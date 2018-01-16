package com.joseph.supplier.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static String getFileName() {
        return UUID.randomUUID().toString();
    }

    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String getNewFileName(String file) {
        return getFileName() + "." + getFileSuffix(file);
    }
}
