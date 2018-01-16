package com.joseph.supplier.dao;

import com.joseph.supplier.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail,Integer>  {

    @Query(value="select o from OrderDetail o where o.orderId = :orderId")
    List <OrderDetail> findByOrderId(@Param("orderId") Integer orderId);

    @Query(value="select sum(o.finishCount) from OrderDetail o where o.orderId = :orderId")
    Integer getCount(@Param("orderId") Integer orderId);

    @Query(value="select o from OrderDetail o where o.orderId = :orderId and o.finishCount = :finishCount and o.deliveryDate = :deliveryDate")
    List <OrderDetail> checkRepeat(@Param("orderId") int orderId,@Param("finishCount") int finishCount,
                                   @Param("deliveryDate") String deliveryDate);


}
