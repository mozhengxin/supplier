package com.joseph.supplier.dao;

import com.joseph.supplier.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update t_order set finish_count = (select IFNULL(sum(finish_count),0)  from t_order_detail where order_id = :orderId)" +
            " where id = :orderId",nativeQuery = true)
    void checkFinishCount(@Param("orderId") Integer orderId);

    @Query(value = "select o from Order o where o.batchId = :batchId")
    List<Order> listOrdersByBatchId(@Param("batchId") String batchId);

    @Transactional
    @Modifying
    @Query(value = "delete from Order o where o.batchId = :batchId")
    void deleteOrderByBatchId(@Param("batchId") String batchId);

    @Transactional
    @Modifying
    @Query(value = "update Order set imgs = :imgs where batchId = :batchId")
    void saveImgs(@Param("batchId") String batchId, @Param("imgs") String imgs);

    @Transactional
    @Modifying
    @Query(value = "update Order set imgs = :imgs where style = :style")
    void saveImgsWithStyle(@Param("imgs") String imgs, @Param("style") String style);

    @Query(value = "select o from Order o where o.style = :style order by o.ctime desc")
    List<Order> listByStyle(@Param("style") String style);

    @Query(value = "select o from Order o where o.style = :style and o.size = :size order by o.ctime desc")
    List<Order> listByStyleAndSize(@Param("style") String style, @Param("size") String size);

    @Query(value = "select o from Order o where o.style = :style and o.brand = :brand order by o.ctime desc")
    List<Order> listByStyleAndBrand(@Param("style") String style, @Param("brand") String brand);


    @Query(value = "select o from Order o where o.style = :style and o.size = :size and o.brand = :brand order by o.ctime desc")
    List<Order> listByStyleAndSizeAndBrand(@Param("style") String style,@Param("size") String size,@Param("brand") String brand);


    @Query(value = "select o from Order o where o.brand = :brand order by o.ctime desc")
    List<Order> listByBrand(@Param("brand") String brand);

    @Query(value = "select o from Order o where o.size = :size order by o.ctime desc")
    List<Order> listBySize(@Param("size") String size);

}
