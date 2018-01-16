package com.joseph.supplier.dao;

import com.joseph.supplier.model.PreOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreOrderDetailDao extends JpaRepository<PreOrderDetail, Integer> {

    @Query(value = "select o from PreOrderDetail o where o.preOrderId = :preOrderId")
    List<PreOrderDetail> listByPreOrderId(@Param("preOrderId") int preOrderId);
}
