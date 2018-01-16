package com.joseph.supplier.dao;

import com.joseph.supplier.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecordDao extends JpaRepository<Record,Integer>  {

    @Query(value = "select o from Record o where o.batchId = :batchId order by o.ctime desc")
    List<Record> findByBatchId(@Param("batchId") String batchId);

    @Query(value = "select count(o) from Record o where o.batchId = :batchId and o.ctime < :endTime and o.ctime > :startTime")
    int hasRecordToday(@Param("batchId") String batchId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
