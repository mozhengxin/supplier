package com.joseph.supplier.model;

import java.io.ObjectStreamClass;
import java.io.Serializable;

public class Log implements Serializable {

    private Integer id;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Log() {
        ObjectStreamClass sdc =  ObjectStreamClass.lookup(Log.class);
        System.out.println(sdc.getSerialVersionUID());
    }

    public static void main(String[] args) {
        new Log();
    }
}
