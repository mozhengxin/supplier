package com.joseph.supplier.dao;

import com.joseph.supplier.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeDao extends JpaRepository<Size,Integer>  {

}
