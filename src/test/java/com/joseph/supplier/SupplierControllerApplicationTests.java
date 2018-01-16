package com.joseph.supplier;

import com.joseph.supplier.dao.ColorDao;
import com.joseph.supplier.model.Color;
import com.joseph.supplier.model.Image;
import com.joseph.supplier.service.SupplierService;
import com.joseph.supplier.util.ImageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
public class SupplierControllerApplicationTests {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ColorDao colorDao;



    @Test
    public void contextLoads() throws InterruptedException {
        System.out.println("###########");
        Color c = new Color();
        c.setName("color");
        supplierService.saveColor(c);
        System.out.println("@@@@@@@@@@@@");
    }


    @Test
    public void contextLoads1() throws InterruptedException {
        Image image = new Image();
        System.out.println(ImageUtil.getIms("2"));
    }
}
