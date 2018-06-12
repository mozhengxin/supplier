package com.joseph.supplier.controller;

import com.joseph.supplier.model.*;
import com.joseph.supplier.service.SupplierService;
import com.joseph.supplier.util.DateUtil;
import com.joseph.supplier.util.FileUtil;
import com.joseph.supplier.util.ImageUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Value("${fileDir}")
    private String fileDir;

    @RequestMapping("/addOrder")
    public String saveOrder(Order order, SizeParam size, BindingResult bindingResult) {
        order.setStyle(order.getStyle().trim());
        order.setSize(size.toString());
        supplierService.saveOrder(order);
        return "redirect:/view/supplierOrder?supplierId=" + order.getSupplierId();
    }

    @RequestMapping("/addRecord")
    public String saveRecord(Record record, String name, Integer supplierId) throws UnsupportedEncodingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        record.setSupplierName(supplierService.getSupplierById(supplierId).getName());
        if (StringUtils.isBlank(record.getDeliveryDate())) {
            record.setDeliveryDate(sdf.format(new Date()));
        } else {
            record.setDeliveryDate(record.getDeliveryDate().replace("T", " ").substring(0, 13));
        }
        supplierService.saveRecord(record);
        name = java.net.URLEncoder.encode(name, "UTF-8");
        return "redirect:/view/orderList?batchId=" + record.getBatchId() + "&name=" + name + "&supplierId=" + supplierId;
    }

    @RequestMapping("/editOrder")
    public String editOrder(Order order) {
        Order dbDate = supplierService.getOrderById(order.getId());
        dbDate.setOutCount(order.getOutCount());
        supplierService.updateOrder(dbDate);
        return "redirect:/view/supplierOrder?supplierId=" + order.getSupplierId();
    }

    @RequestMapping("/deleteOrderBatch")
    @ResponseBody
    public String editOrder(String batchId) {
        supplierService.deleteOrderByBatchId(batchId);
        return "";
    }

    @RequestMapping("/deleteOrderDetail")
    @ResponseBody
    public String deleteOrderDetail(Integer id) {
        supplierService.deleteOrderDetail(id);
        return "";
    }

    @RequestMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(Integer orderId) {
        supplierService.deleteOrder(orderId);
        return "";
    }

    @RequestMapping("/deleteRecord")
    @ResponseBody
    public String deleteRecord(Integer id) {
        supplierService.deleteRecord(id);
        return "";
    }

    @RequestMapping("/addSize")
    public String saveSize(Size size) {
        supplierService.saveSize(size);
        return "redirect:/view/size";
    }

    @RequestMapping("/addStyle")
    public String saveStyle(Style style) {
        supplierService.saveStyle(style);
        return "redirect:/view/style";
    }

    @RequestMapping("/addColor")
    public String saveColor(Color color) {
        supplierService.saveColor(color);
        return "redirect:/view/color";
    }

    @RequestMapping("/addSupplier")
    public String saveColor(Supplier supplier) {
        supplierService.saveSupplier(supplier);
        return "redirect:/view/supplier";
    }

    @RequestMapping("/addOrderDetail")
    public String saveOrderDetail(ModelMap modelMap, OrderDetail orderDetail) throws UnsupportedEncodingException {
        supplierService.saveOrderDetail(orderDetail);
        Order order = supplierService.getOrderById(orderDetail.getOrderId());
        Supplier supplier = supplierService.getSupplierById(orderDetail.getSupplierId());
        String name = java.net.URLEncoder.encode(supplier.getName(), "UTF-8");
        return "redirect:/view/orderList?batchId=" + order.getBatchId() + "&name=" + name + "&supplierId=" + orderDetail.getSupplierId();
    }

    @RequestMapping("/view/supplier")
    public String index(ModelMap modelMap, String name, Supplier supplier) {
        List<Supplier> list = supplierService.listSuppliers();
        modelMap.addAttribute("list", list);
        return "/supplier-list";
    }

    @RequestMapping("/view/size")
    public String viewSize(ModelMap modelMap) {
        List<Size> list = supplierService.listSizes();
        modelMap.addAttribute("list", list);
        return "/size-list";
    }

    @RequestMapping("/view/color")
    public String viewColor(ModelMap modelMap) {
        List<Color> list = supplierService.listColors();
        modelMap.addAttribute("list", list);
        return "/color-list";
    }

    @RequestMapping("/view/style")
    public String viewStyle(ModelMap modelMap) {
        List<Style> list = supplierService.listStyles();
        modelMap.addAttribute("list", list);
        return "/style-list";
    }

    @RequestMapping("/view/addSupplier")
    public String viewAddSupplier() {
        return "/supplier-add";
    }

    @RequestMapping("/view/addColor")
    public String viewAddColor() {
        return "/color-add";
    }

    @RequestMapping("/view/addStyle")
    public String viewAddStyle() {
        return "/style-add";
    }

    @RequestMapping("/view/addSize")
    public String viewAddSize() {
        return "/size-add";
    }

    @RequestMapping("/view/img")
    public String img() {
        return "/img-config";
    }

    @RequestMapping("/view/addOrder")
    public String viewAddOrder(ModelMap modelMap, String name, Integer supplierId) {
        List<Size> sizes = supplierService.listSizes();
        List<Style> styles = supplierService.listStyles();
        List<Color> colors = supplierService.listColors();
        modelMap.addAttribute("sizes", sizes);
        modelMap.addAttribute("styles", styles);
        modelMap.addAttribute("colors", colors);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("supplierId", supplierId);
        return "/order-add";
    }

    @RequestMapping("/view/addBatchOrder")
    public String viewAddBatchOrder(ModelMap modelMap, String name, Integer supplierId,String style, String color, Integer orderId, String batchId) {
        List<Size> sizes = supplierService.listSizes();
        modelMap.addAttribute("sizes", sizes);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("supplierId", supplierId);
        modelMap.addAttribute("style", style);
        modelMap.addAttribute("color", color);
        modelMap.addAttribute("orderId", orderId);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("batchId", batchId);
        return "/order-batch-add";
    }


    @RequestMapping("/addBatchOrder")
    public String addBatchOrder(ModelMap modelMap, Integer supplierId, String deliveryDate,
                                String style, String batchId, String name, SizeParam size, BindingResult bindingResult) {
        Order order;
        if (size.getS() != 0) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDeliveryDate(deliveryDate);
            order = supplierService.getByBatchIdAndSize(batchId, "S");
            orderDetail.setOrderId(order.getId());
            orderDetail.setSupplierId(supplierId);
            orderDetail.setFinishCount(size.getS());
            supplierService.saveOrderDetail(orderDetail);
        }
        if (size.getM() != 0) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDeliveryDate(deliveryDate);
            order = supplierService.getByBatchIdAndSize(batchId, "M");
            orderDetail.setOrderId(order.getId());
            orderDetail.setSupplierId(supplierId);
            orderDetail.setFinishCount(size.getM());
            supplierService.saveOrderDetail(orderDetail);
        }
        if (size.getL() != 0) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDeliveryDate(deliveryDate);
            order = supplierService.getByBatchIdAndSize(batchId, "L");
            orderDetail.setOrderId(order.getId());
            orderDetail.setSupplierId(supplierId);
            orderDetail.setFinishCount(size.getL());
            supplierService.saveOrderDetail(orderDetail);
        }
        if (size.getXL() != 0) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDeliveryDate(deliveryDate);
            order = supplierService.getByBatchIdAndSize(batchId, "XL");
            orderDetail.setOrderId(order.getId());
            orderDetail.setSupplierId(supplierId);
            orderDetail.setFinishCount(size.getXL());
            supplierService.saveOrderDetail(orderDetail);
        }
        if (size.getXXL() != 0) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDeliveryDate(deliveryDate);
            order = supplierService.getByBatchIdAndSize(batchId, "XXL");
            orderDetail.setOrderId(order.getId());
            orderDetail.setSupplierId(supplierId);
            orderDetail.setFinishCount(size.getXXL());
            supplierService.saveOrderDetail(orderDetail);
        }
        if (size.getXXXL() != 0) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDeliveryDate(deliveryDate);
            order = supplierService.getByBatchIdAndSize(batchId, "XXXL");
            orderDetail.setOrderId(order.getId());
            orderDetail.setSupplierId(supplierId);
            orderDetail.setFinishCount(size.getXXXL());
            supplierService.saveOrderDetail(orderDetail);
        }
        return "redirect:/view/orderList?supplierId=" + supplierId +"&style="
                + style + "&batchId=" + batchId + "&name" + name;
    }

    @RequestMapping("/view/orderList")
    public String viewOrderList(ModelMap modelMap, String batchId, String name, Integer supplierId, Integer orderId) {
        List<Order> list = supplierService.listByBatchId(batchId);
        for (Order o : list) {
            o.setImgs(ImageUtil.getIms(o.getStyle()));
            o.setOrderDate(o.getOrderDate().substring(4).replaceAll("-", ""));
            o.setUnfinishCount(o.getOutCount() - o.getFinishCount());
        }
        List<Record> recList = supplierService.listRecordsByOrderId(batchId);
        modelMap.addAttribute("batchId", batchId);
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("reclist", recList);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("supplierId", supplierId);
        modelMap.addAttribute("orderId", orderId);
        if (list.size() > 0) {
            modelMap.addAttribute("style", list.get(0).getStyle());
        }
        return "/supplier-order-list";
    }

    @RequestMapping("/view/config")
    public String config() {
        return "/config";
    }


    @RequestMapping("/view/imgList")
    public String imgList(ModelMap modelMap,String style) {
        List<Image> list = new ArrayList<>();
        if (StringUtils.isBlank(style)) {
            HashMap <String,String> images = ImageUtil.images;
            Set<Map.Entry<String,String>> set =  images.entrySet();
            Iterator<Map.Entry<String,String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String,String> e = iterator.next();
                list.add(new Image(e.getKey(), ImageUtil.filePath + e.getValue()));
            }
        } else {
            if (StringUtils.isNotBlank(ImageUtil.getIms(style))) {
                list.add(new Image(style, ImageUtil.getIms(style)));
            }
            modelMap.addAttribute("style", style);
        }
        modelMap.addAttribute("list", list);
        return "/img-list";
    }

    @RequestMapping("/view/preOrder")
    public String preOrder(ModelMap modelMap, String search) {
        List<PreOrder> list;
        List<PreOrder> list1;
        if (StringUtils.isNotBlank(search)) {
            list = supplierService.listPreOrderByFinishAndStyle(0, search);
            list1 = supplierService.listPreOrderByFinishAndStyle(1, search);
        } else {
            list = supplierService.listPreOrderByFinish(0);
            list1 = supplierService.listPreOrderByFinish(1);
        }
        Collections.sort(list, new Comparator<PreOrder>() {
            @Override
            public int compare(PreOrder o1, PreOrder o2) {
                return (int) (o1.getRecentFinishTime().getTime() - o2.getRecentFinishTime().getTime());
            }
        });
        for (PreOrder p : list) {
            p.setImgs(ImageUtil.getIms(p.getStyle()));
            if (p.getFinishTime() != null &&
                    DateUtil.getTime("yyyy-MM-dd", p.getFinishTime()).equals(DateUtil.getTime("yyyy-MM-dd"))) {
                p.setIsToday(1);
            }
            List<PreOrderDetail> detailList = supplierService.listPreOrderDetail(p.getId());
            for (PreOrderDetail detail : detailList) {
                if (detail.getBackDate().equals(DateUtil.getTime("yyyy-MM-dd"))){
                    p.setIsToday(1);
                }
            }
            p.setDetails(detailList);
        }
        for (PreOrder p : list1) {
            p.setImgs(ImageUtil.getIms(p.getStyle()));
            List<PreOrderDetail> detailList = supplierService.listPreOrderDetail(p.getId());
            if (p.getFinishTime() != null &&
                    DateUtil.getTime("yyyy-MM-dd", p.getFinishTime()).equals(DateUtil.getTime("yyyy-MM-dd"))) {
                p.setIsToday(1);
            }
            for (PreOrderDetail detail : detailList) {
                if (detail.getBackDate().equals(DateUtil.getTime("yyyy-MM-dd"))){
                    p.setIsToday(1);
                }
            }
            p.setDetails(detailList);
        }
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("list1", list1);
        modelMap.addAttribute("search", search);
        return "/pre-order-list";
    }

    @RequestMapping("/view/preOrder1")
    public String preOrder1(ModelMap modelMap, String search) {
        List<PreOrder> list;
        List<PreOrder> list1;
        if (StringUtils.isNotBlank(search)) {
            list = supplierService.listPreOrderByFinishAndStyle(0, search);
            list1 = supplierService.listPreOrderByFinishAndStyle(1, search);
        } else {
            list = supplierService.listPreOrderByFinish(0);
            list1 = supplierService.listPreOrderByFinish(1);
        }
        Collections.sort(list, new Comparator<PreOrder>() {
            @Override
            public int compare(PreOrder o1, PreOrder o2) {
                return (int) (o1.getRecentFinishTime().getTime() - o2.getRecentFinishTime().getTime());
            }
        });
        for (PreOrder p : list) {
            p.setImgs(ImageUtil.getIms(p.getStyle()));
            List<PreOrderDetail> detailList = supplierService.listPreOrderDetail(p.getId());
            if (p.getFinishTime() != null &&
                    DateUtil.getTime("yyyy-MM-dd", p.getFinishTime()).equals(DateUtil.getTime("yyyy-MM-dd"))) {
                p.setIsToday(1);
            }
            for (PreOrderDetail detail : detailList) {
                if (detail.getBackDate().equals(DateUtil.getTime("yyyy-MM-dd"))){
                    p.setIsToday(1);
                }
            }
            p.setDetails(detailList);
        }
        for (PreOrder p : list1) {
            p.setImgs(ImageUtil.getIms(p.getStyle()));
            List<PreOrderDetail> detailList = supplierService.listPreOrderDetail(p.getId());
            if (p.getFinishTime() != null &&
                    DateUtil.getTime("yyyy-MM-dd", p.getFinishTime()).equals(DateUtil.getTime("yyyy-MM-dd"))) {
                p.setIsToday(1);
            }
            for (PreOrderDetail detail : detailList) {
                if (detail.getBackDate().equals(DateUtil.getTime("yyyy-MM-dd"))){
                    p.setIsToday(1);
                }
            }
            p.setDetails(detailList);
        }
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("list1", list1);
        modelMap.addAttribute("search", search);
        return "/pre-order-list1";
    }

    @RequestMapping("/view/orderCountList")
    public String orderCountList(ModelMap modelMap, String sdate, String edate, int supplierId) {
        List<Order> list = supplierService.getSupplierCountList(supplierId, sdate, edate);
        Order sumOrder = new Order();
        int sumCount = 0;
        int sumFinishCount = 0;
        for (Order o : list) {
            sumCount += o.getOutCount();
            sumFinishCount += o.getFinishCount();
        }
        sumOrder.setOutCount(sumCount);
        sumOrder.setFinishCount(sumFinishCount);
        sumOrder.setOrderDate("合计：");
        sumOrder.setStyle("");
        List <Order> list1 = new ArrayList<>();
        list1.add(sumOrder);
        list1.addAll(list);
        modelMap.addAttribute("list", list1);
        modelMap.addAttribute("supplierId", supplierId);
        modelMap.addAttribute("sdate", sdate);
        modelMap.addAttribute("edate", edate);
        return "/supplier-order-count-list";
    }

    @RequestMapping("/view/addPreOrder")
    public String addPreOrder(ModelMap modelMap) {
        List<Color> colors = supplierService.listColors();
        modelMap.addAttribute("colors", colors);
        modelMap.addAttribute("ctime", DateUtil.getTime("yyyy-MM-dd HH:mm"));
        return "/pre-order-add";
    }

    @RequestMapping("/view/editPreOrder")
    public String editPreOrder(ModelMap modelMap,Integer preOrderId) {
        List<Color> colors = supplierService.listColors();
        PreOrder preOrder = supplierService.getPreOrder(preOrderId);
        modelMap.addAttribute("colors", colors);
        modelMap.addAttribute("preOrder", preOrder);
        return "/pre-order-edit";
    }

    @RequestMapping("/view/addPreOrderDetail")
    public String addOrderDetail(ModelMap modelMap, int preOrderId) {
        modelMap.addAttribute("preOrderId", preOrderId);
        return "/pre-order-detail-add";
    }

    @RequestMapping("/addPreOrder")
    public String addPreOrder(PreOrder preOrder, PreOrderDetail preOrderDetail, BindingResult bindingResult) {
        String backDate = preOrderDetail.getBackDate();
        preOrder.setRecentFinishTime(DateUtil.getDate(StringUtils.isBlank(backDate) ? "2020-01-01" : backDate));
        supplierService.savePreOrder(preOrder);
        preOrderDetail.setPreOrderId(preOrder.getId());
        if (preOrderDetail.getBackNum() > 0 && StringUtils.isNotBlank(preOrderDetail.getBackDate())) {
            supplierService.savePreOrderDetail(preOrderDetail);
        }
        return "redirect:/view/preOrder";
    }

    @RequestMapping("/addPreOrderDetail")
    public String addPreOrderDetail(ModelMap modelMap, PreOrderDetail preOrderDetail, BindingResult bindingResult) {
        supplierService.updtateFinishTime(preOrderDetail.getPreOrderId(), DateUtil.getDate(preOrderDetail.getBackDate()));
        supplierService.savePreOrderDetail(preOrderDetail);
        return "redirect:/view/preOrder";
    }

    @RequestMapping("/view/preOrderToOrder")
    public String preOrderToOrder(ModelMap modelMap, Integer preOrderId) {
        List<Supplier> suppliers = supplierService.listSuppliers();
        modelMap.addAttribute("suppliers", suppliers);
        PreOrder preOrder = supplierService.getPreOrder(preOrderId);
        modelMap.addAttribute("preOrder", preOrder);
        List<Size> sizes = supplierService.listSizes();
        modelMap.addAttribute("sizes", sizes);
        return "/pre-order-to-order";
    }

    @RequestMapping("/view/orderList1")
    public String viewOrderList1(ModelMap modelMap, String batchId, String name, Integer supplierId) {
        List<Order> list = supplierService.listByBatchId(batchId);
        for (Order o : list) {
            o.setImgs(ImageUtil.getIms(o.getStyle()));
            o.setOrderDate(o.getOrderDate().substring(4).replaceAll("-", ""));
            o.setUnfinishCount(o.getOutCount() - o.getFinishCount());
        }
        List<Record> recList = supplierService.listRecordsByOrderId(batchId);
        modelMap.addAttribute("batchId", batchId);
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("reclist", recList);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("supplierId", supplierId);
        if (list.size() > 0) {
            modelMap.addAttribute("style", list.get(0).getStyle());
        }
        return "/supplier-order-list1";
    }

    @RequestMapping("/view/addOrderDetail")
    public String viewAddOrderDetail(ModelMap modelMap, Integer supplierId, Integer orderId) {
        modelMap.addAttribute("orderId", orderId);
        modelMap.addAttribute("supplierId", supplierId);
        return "/order-detail-add";
    }

    @RequestMapping("/view/addRecord")
    public String viewAddOrderRecord(ModelMap modelMap, Integer orderId, String batchId, Integer supplierId, String name, String style) {
        modelMap.addAttribute("orderId", orderId);
        modelMap.addAttribute("batchId", batchId);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("supplierId", supplierId);
        modelMap.addAttribute("style", style);
        return "/record-add";
    }

    @RequestMapping("/view/editOrderDetail")
    public String viewEditOrderDetail(ModelMap modelMap, Integer supplierId, Integer orderId, Integer id) {
        OrderDetail od = supplierService.getOrderDetailById(id);
        modelMap.addAttribute("deliveryData", od.getDeliveryDate());
        modelMap.addAttribute("finishCount", od.getFinishCount());
        modelMap.addAttribute("id", id);
        modelMap.addAttribute("orderId", orderId);
        modelMap.addAttribute("supplierId", supplierId);
        return "/order-detail-edit";
    }

    @RequestMapping("/view/orderDetail")
    public String orderDetail(Integer orderId, ModelMap modelMap) {
        List<OrderDetail> list = supplierService.listOrderDetailsByOrderId(orderId);
        Order order = supplierService.getOrderById(orderId);
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("orderId", orderId);
        modelMap.addAttribute("supplierId", order.getSupplierId());
        modelMap.addAttribute("color", order.getColor());
        modelMap.addAttribute("size", order.getSize());
        modelMap.addAttribute("style", order.getStyle());
        return "/order-detail-list";
    }

    @RequestMapping("/view/editOrder")
    public String viewEditOrder(Integer orderId, ModelMap modelMap) {
        Order order = supplierService.getOrderById(orderId);
        modelMap.addAttribute("order", order);
        modelMap.addAttribute("supplierId", order.getSupplierId());
        modelMap.addAttribute("batchId", order.getBatchId());
        return "/order-edit";
    }

    @RequestMapping("/view/record")
    public String listRecordsToday(ModelMap modelMap) {
        List<Record> list = supplierService.listRecordsToday();
        String name = StringUtils.EMPTY;
        for (Record r : list) {
            if (StringUtils.isBlank(name)) {
                r.setShow(1);
                name = r.getSupplierName();
                continue;
            }
            if (name.equals(r.getSupplierName())) {
                r.setShow(0);
            } else {
                r.setShow(1);
                name = r.getSupplierName();
            }
        }
        modelMap.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        modelMap.addAttribute("list", list);
        return "/record-list";
    }

    @RequestMapping("/view/supplierOrder")
    public String viewSupplierOrder(String name, Integer supplierId, ModelMap modelMap, String search) {
        List<Order> list;
        List<Order> listFinish = new ArrayList<>();
        if (StringUtils.isBlank(search)) {
            list = supplierService.listOrdersBySupplierId(supplierId);
        } else {
            list = supplierService.searchOrdersBySupplierId(supplierId, search.trim());
        }
        Iterator<Order> iterator = list.iterator();
        while (iterator.hasNext()) {
            Order o = iterator.next();
            o.setImgs(ImageUtil.getIms(o.getStyle()));
            o.setUnfinishCount(o.getOutCount() - o.getFinishCount());
            o.setHasRecord(supplierService.hasRecordToday(o.getBatchId()));
            if (o.getFinishCount() >= o.getOutCount()) {
                listFinish.add(o);
                iterator.remove();
            }
        }
        Supplier supplier = supplierService.getSupplierById(supplierId);
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("list1", listFinish);
        modelMap.addAttribute("name", supplier.getName());
        modelMap.addAttribute("supplierId", supplierId);
        modelMap.addAttribute("search", search);
        return "/supplier-order";
    }

    @RequestMapping("/view/supplierOrderFinish")
    public String viewSupplierOrder1(String name, Integer supplierId, ModelMap modelMap, String search) {
        List<Order> list;
        List<Order> listFinish = new ArrayList<>();
        if (StringUtils.isBlank(search)) {
            list = supplierService.listOrdersBySupplierId(supplierId);
        } else {
            list = supplierService.searchOrdersBySupplierId(supplierId, search.trim());
        }
        Iterator<Order> iterator = list.iterator();
        while (iterator.hasNext()) {
            Order o = iterator.next();
            o.setImgs(ImageUtil.getIms(o.getStyle()));
            o.setUnfinishCount(o.getOutCount() - o.getFinishCount());
            o.setHasRecord(supplierService.hasRecordToday(o.getBatchId()));
            if (o.getFinishCount() >= o.getOutCount()) {
                listFinish.add(o);
                iterator.remove();
            }
        }
        Supplier supplier = supplierService.getSupplierById(supplierId);
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("list1", listFinish);
        modelMap.addAttribute("name", supplier.getName());
        modelMap.addAttribute("supplierId", supplierId);
        modelMap.addAttribute("search", search);
        return "/supplier-order1";
    }

    @RequestMapping("/view/searchOrder")
    public String searchOrder(ModelMap modelMap, String search, String size, String brand) {
        List<Order> list;
        List<Order> listFinish = new ArrayList<>();
        if (StringUtils.isNotBlank(size) && StringUtils.isNotBlank(brand)) {
            list = supplierService.listByStyleAndSizeAndBrand(search, size, brand);
        } else if (StringUtils.isNotBlank(size) && StringUtils.isBlank(brand)) {
            list = supplierService.listByStyleAndSize(search, size);
        } else if (StringUtils.isBlank(size) && StringUtils.isNotBlank(brand)) {
            list = supplierService.listByStyleAndBrand(search, brand);
        } else {
            list = supplierService.listByStyle(search);
        }
        Iterator<Order> iterator = list.iterator();
        int finishCount = 0;
        int unfinishCount = 0;
        while (iterator.hasNext()) {
            Order o = iterator.next();
            o.setImgs(ImageUtil.getIms(o.getStyle()));
            o.setUnfinishCount(o.getOutCount() - o.getFinishCount());
            o.setHasRecord(supplierService.hasRecordToday(o.getBatchId()));
            o.setName(supplierService.getSupplierById(o.getSupplierId()).getName());
            if (o.getFinishCount() >= o.getOutCount()) {
                listFinish.add(o);
                iterator.remove();
            } else {
                finishCount += o.getFinishCount();
                unfinishCount += o.getUnfinishCount();
            }
        }
        List<Size> sizes = supplierService.listSizes();
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("list1", listFinish);
        modelMap.addAttribute("finishCount", finishCount);
        modelMap.addAttribute("unfinishCount", unfinishCount);
        modelMap.addAttribute("search", search);
        modelMap.addAttribute("sizes", sizes);
        modelMap.addAttribute("size", size);
        modelMap.addAttribute("brand", brand);
        return "/search-list";
    }

    @RequestMapping("/view/searchOrderFinish")
    public String searchOrderFinish(ModelMap modelMap, String search, String size, String brand) {
        List<Order> list;
        List<Order> listFinish = new ArrayList<>();
        if (StringUtils.isNotBlank(size) && StringUtils.isNotBlank(brand)) {
            list = supplierService.listByStyleAndSizeAndBrand(search, size, brand);
        } else if (StringUtils.isNotBlank(size) && StringUtils.isBlank(brand)) {
            list = supplierService.listByStyleAndSize(search, size);
        } else if (StringUtils.isBlank(size) && StringUtils.isNotBlank(brand)) {
            list = supplierService.listByStyleAndBrand(search, brand);
        } else {
            list = supplierService.listByStyle(search);
        }
        Iterator<Order> iterator = list.iterator();
        int finishCount = 0;
        int unfinishCount = 0;
        while (iterator.hasNext()) {
            Order o = iterator.next();
            o.setImgs(ImageUtil.getIms(o.getStyle()));
            o.setUnfinishCount(o.getOutCount() - o.getFinishCount());
            o.setHasRecord(supplierService.hasRecordToday(o.getBatchId()));
            o.setName(supplierService.getSupplierById(o.getSupplierId()).getName());
            if (o.getFinishCount() >= o.getOutCount()) {
                listFinish.add(o);
                iterator.remove();
            } else {
                finishCount += o.getFinishCount();
                unfinishCount += o.getUnfinishCount();
            }
        }
        List<Size> sizes = supplierService.listSizes();
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("list1", listFinish);
        modelMap.addAttribute("finishCount", finishCount);
        modelMap.addAttribute("unfinishCount", unfinishCount);
        modelMap.addAttribute("search", search);
        modelMap.addAttribute("sizes", sizes);
        modelMap.addAttribute("size", size);
        modelMap.addAttribute("brand", brand);
        return "/search-list1";
    }

    @RequestMapping("/view/search")
    public String viewSearch(ModelMap modelMap) {
        List<Size> sizes = supplierService.listSizes();
        modelMap.addAttribute("sizes", sizes);
        modelMap.addAttribute("finishCount", 0);
        modelMap.addAttribute("unfinishCount", 0);
        return "/search-list";
    }

    //处理文件上传
    @RequestMapping(value = "/img", method = RequestMethod.POST)
    public String uploadImgConfig(@RequestParam("file") MultipartFile file, String style)  {
        String fileName = file.getOriginalFilename();
        String newFileName = new Date().getTime() + "." + FileUtil.getFileSuffix(fileName);
        try {
            File imgFile = new File(fileDir + newFileName);
            imgFile.createNewFile();
            Thumbnails.of(file.getInputStream()).scale(0.25f).outputQuality(0.25f).toFile(imgFile);
            BufferedImage bufferedImage = Thumbnails.of(imgFile).scale(1f).asBufferedImage();
            if (bufferedImage.getHeight() < bufferedImage.getWidth()) {
                Thumbnails.of(imgFile).scale(1f).rotate(90).toFile(imgFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image image = new Image(style, newFileName);
        supplierService.saveImage(image);
        return "redirect:/view/imgList";
    }

    @RequestMapping(value = "/setFinish")
    @ResponseBody
    public String setFinish(int id) {
        supplierService.updatePreOrderFinish(id);
        return "";
    }

    @RequestMapping(value = "/deletePreOrder")
    @ResponseBody
    public String deletePreOrder(int id) {
        supplierService.deletePreOrder(id);
        return "";
    }

}
