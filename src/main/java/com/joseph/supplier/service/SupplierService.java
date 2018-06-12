package com.joseph.supplier.service;

import com.joseph.supplier.dao.*;
import com.joseph.supplier.model.*;
import com.joseph.supplier.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private SizeDao sizeDao;

    @Autowired
    private StyleDao styleDao;

    @Autowired
    private ColorDao colorDao;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private PreOrderDao preOrderDao;

    @Autowired
    private PreOrderDetailDao preOrderDetailDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ImageDao imageDao;

    //supplier
    public void saveSupplier(Supplier supplier) {
        supplierDao.save(supplier);
    }

    public List<Supplier> listSuppliers() {
        return supplierDao.findAll();
    }

    //order
    @Transactional
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailDao.save(orderDetail);
        orderDao.checkFinishCount(orderDetail.getOrderId());
    }

    public Supplier getSupplierById(Integer supplierId) {
        return supplierDao.findOne(supplierId);
    }

    public void deleteOrderDetail(Integer id) {
        OrderDetail orderDetail = orderDetailDao.findOne(id);
        orderDetailDao.delete(id);
        orderDao.checkFinishCount(orderDetail.getOrderId());
    }

    public void deleteRecord(Integer id) {
        recordDao.delete(id);
    }

    public void editOrderDetail(OrderDetail orderDetail) {
        orderDetailDao.save(orderDetail);
    }

    public void deleteOrder(Integer id) {
        orderDao.delete(id);
    }

    public void updateOrder(Order order) {
        orderDao.save(order);
    }

    @Transactional
    public void saveOrder(Order order) {
        String sizeStr = order.getSize();
        String[] sizes = sizeStr.split("-");
        String[] sizesName = {"S", "M", "L", "XL", "XXL", "XXXL"};
        String batchId = UUID.randomUUID().toString().replaceAll("-", "");
        Date ctime = new Date();
        for (int i = 0; i < sizes.length; i++) {
            if (!"0".equals(sizes[i])) {
                Order o = new Order();
                BeanUtils.copyProperties(order, o);
                o.setBatchId(batchId);
                o.setCtime(ctime);
                o.setSize(sizesName[i]);
                o.setOutCount(Integer.valueOf(sizes[i]));
                o.setFinishCount(0);
                o.setUnfinishCount(Integer.valueOf(sizes[i]));
                orderDao.save(o);
            }
        }
    }

    public Order getOrderById(Integer id) {
        return orderDao.getOne(id);
    }

    public List<Order> getSupplierCountList(int sid, String sdate, String edate) {
        String sql = "select o.order_date, o.style as style, o.color as color, sum(o.out_count) as out_count, sum(o.finish_count) as finish_count " +
                "from t_order o " +
        "where o.supplier_id = " + sid +"  @sdate@  @edate@ group by o.order_date, o.style, " +
                "o.color order by o.order_date desc";
        if (StringUtils.isNotBlank(sdate)) {
            sql = sql.replace("@sdate@", "and o.order_date >= '" + sdate + "'");
        } else {
            sql = sql.replace("@sdate@", "");
        }
        if (StringUtils.isNotBlank(edate)) {
            sql = sql.replace("@edate@", "and o.order_date <= '" + edate + "'");
        } else {
            sql = sql.replace("@edate@", "");
        }
        return jdbcTemplate.query(sql, new OrderCountRowMapper());
    }


    public List<Order> listOrders() {
        return orderDao.findAll();
    }

    public List<OrderDetail> listOrderDetails() {
        return orderDetailDao.findAll();
    }

    public List<OrderDetail> listOrderDetailsByOrderId(Integer orderId) {
        return orderDetailDao.findByOrderId(orderId);
    }

    public OrderDetail getOrderDetailById(Integer id) {
        return orderDetailDao.getOne(id);
    }

    public List<Order> listOrdersBySupplierId(Integer supplierId) {
        String sql = "select " +
                "batch_id as batchId," +
                "order_date as orderDate, " +
                "sum(finish_count) as finishCount, " +
                "style, " +
                "color," +
                "brand," +
                "sum(out_count) as outCount, " +
                "group_concat(size) as size, " +
                "delivery_date as deliveryDate, " +
                "price from " +
                "t_order where supplier_id = " + supplierId + " group by batch_id, style, ctime, color, order_date, " +
                "delivery_date, brand, price order by ctime desc";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    public List<Order> searchOrdersBySupplierId(Integer supplierId, String search) {
        String sql = "select " +
                "batch_id as batchId," +
                "order_date as orderDate, " +
                "sum(finish_count) as finishCount, " +
                "style, " +
                "color," +
                "brand," +
                "sum(out_count) as outCount, " +
                "group_concat(size) as size, " +
                "delivery_date as deliveryDate, " +
                "price from t_order where supplier_id = " + supplierId + " and style = '" + search +
                "' group by batch_id, style,ctime,color,order_date,delivery_date, brand, price order by ctime desc";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    public List<Record> listRecordsToday() {
        String sql = "select supplier_name,style,content,ctime from t_record where to_days(ctime) = to_days(now()) " +
                "group by supplier_name,style,content,ctime";
        return jdbcTemplate.query(sql, new RecordRowMapper());
    }

    //size
    public void saveSize(Size size) {
        sizeDao.save(size);
    }

    public List<Size> listSizes() {
        return sizeDao.findAll();
    }

    //style
    public void saveStyle(Style style) {
        styleDao.save(style);
    }

    public List<Style> listStyles() {
        return styleDao.findAll();
    }

    //color
    public void saveColor(Color color) {
        colorDao.save(color);
    }

    public List<Color> listColors() {
        return colorDao.findAll();
    }

    public void saveRecord(Record record) {
        record.setCtime(new Date());
        recordDao.save(record);
    }

    public PreOrder getPreOrder(int id) {
        return preOrderDao.findOne(id);
    }

    public void savePreOrder(PreOrder preOrder) {
        preOrderDao.save(preOrder);
    }

    public void savePreOrderDetail(PreOrderDetail preOrderDetail) {
        preOrderDetailDao.save(preOrderDetail);
    }

    @Transactional
    public void updtateFinishTime(int id, Date recentFinishTime) {
        preOrderDao.updtateFinishTime(id, recentFinishTime);
    }

    public List<PreOrder> listPreOrderByFinishAndStyle(int finish, String style) {
        return preOrderDao.listOrdersByFinishAndStyle(finish, style);
    }

    public List<PreOrder> listPreOrderByFinish(int finish) {
        return preOrderDao.listOrdersByFinish(finish);
    }

    @Transactional
    public void updatePreOrderFinish(int id) {
        preOrderDao.setFinish(id, new Date());
    }

    public List<PreOrderDetail> listPreOrderDetail(int preOrderId) {
        return preOrderDetailDao.listByPreOrderId(preOrderId);
    }

    @Transactional
    public void saveImage(Image image) {
        imageDao.save(image);
        ImageUtil.setIms(image.getStyle(), image.getImgs());
    }

    public List<Record> listRecordsByOrderId(String batchId) {
        return recordDao.findByBatchId(batchId);
    }

    @Transactional
    public void deletePreOrder(int id) {
        preOrderDao.delete(id);
    }

    public List<Order> listByBatchId(String batchId) {
        return orderDao.listOrdersByBatchId(batchId);
    }

    public void deleteOrderByBatchId(String batchId) {
        orderDao.deleteOrderByBatchId(batchId);
    }

    public List<Order> listByStyle(String style) {
        return orderDao.listByStyle(style);
    }

    public List<Order> listByStyleAndSize(String style, String size) {
        return orderDao.listByStyleAndSize(style, size);
    }

    public List<Order> listByStyleAndBrand(String style, String brand) {
        return orderDao.listByStyleAndBrand(style, brand);
    }

    public List<Order> listByStyleAndSizeAndBrand(String style, String size, String brand) {
        return orderDao.listByStyleAndSizeAndBrand(style, size, brand);
    }


    public String hasRecordToday(String batchId) {
        return recordDao.hasRecordToday(batchId, getStartTime(), getEndTime()) > 0 ? "是" : "否";
    }

    public Order getByBatchIdAndSize(String batchId, String size) {
        return orderDao.getByBatchIdAndSize(size, batchId);
    }

    private static Date getStartTime() {
        return DateUtils.truncate(new Date(), Calendar.DATE);
    }

    private static Date getEndTime() {
        return DateUtils.ceiling(new Date(), Calendar.DATE);
    }
}
