package com.joseph.supplier.dao;

import com.joseph.supplier.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorDao extends JpaRepository<Color,Integer>  {

}
