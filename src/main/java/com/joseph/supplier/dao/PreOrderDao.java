package com.joseph.supplier.dao;

import com.joseph.supplier.model.PreOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface PreOrderDao extends JpaRepository<PreOrder, Integer> {

    @Query(value = "select o from PreOrder o where o.isFinish = :isFinish order by o.finishTime desc")
    List<PreOrder> listOrdersByFinish(@Param("isFinish") int isFinish);


    @Query(value = "select o from PreOrder o where o.isFinish = :isFinish and o.style = :style order by o.finishTime desc")
    List<PreOrder> listOrdersByFinishAndStyle(@Param("isFinish") int isFinish, @Param("style") String style);


    @Query(value = "select o from PreOrder o where o.style = :style")
    List<PreOrder> listOrdersByStyle(@Param("style") String style);

    @Modifying
    @Query(value = "update PreOrder o set o.isFinish = 1 , o.finishTime = :finishTime where o.id = :id")
    void setFinish(@Param("id") int id,@Param("finishTime") Date finishTime);

    @Modifying
    @Query(value = "update PreOrder o set o.recentFinishTime = :recentFinishTime where o.id = :id")
    void updtateFinishTime(@Param("id") int id,@Param("recentFinishTime") Date recentFinishTime);
}
