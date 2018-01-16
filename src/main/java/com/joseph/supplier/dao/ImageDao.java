package com.joseph.supplier.dao;

import com.joseph.supplier.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDao extends JpaRepository<Image,String>  {

}
