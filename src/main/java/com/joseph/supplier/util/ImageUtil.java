package com.joseph.supplier.util;

import com.joseph.supplier.dao.ImageDao;
import com.joseph.supplier.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ImageUtil {

    public static HashMap<String, String> images = new HashMap<>();

    public static String filePath;

    @Value("${filePath}")
    public void setFilePath(String path) {
        filePath = path;
    }

    public static String getFilePath() {
        return filePath;
    }

    @Autowired
    public ImageUtil(ImageDao imageDao) {
        List<Image> list = imageDao.findAll();
        for (Image image : list) {
            images.put(image.getStyle(), image.getImgs());
        }
        System.out.println(images);
    }

    public static String getIms(String style) {
        return images.get(style) == null ? filePath + "default.png" : filePath + images.get(style);
    }

    public static void setIms(String style, String imgs) {
        images.put(style, imgs);
    }

}
