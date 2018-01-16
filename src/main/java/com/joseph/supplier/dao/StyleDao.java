package com.joseph.supplier.dao;

import com.joseph.supplier.model.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleDao extends JpaRepository<Style,Integer> {

}
