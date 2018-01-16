package com.joseph.supplier.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String batchId;

    private Integer supplierId;

    private String style;

    private String color;

    private String size;

    private Integer outCount = 0;

    private String deliveryDate;

    private String orderDate;

    private String brand;

    private String imgs;

    private String price;

    private Date ctime;

    private Date mtime;

    private Integer unfinishCount = 0;

    private Integer finishCount = 0;

    @Transient
    private String hasRecord;

    @Transient
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

//    public String getTrialDate() {
//        return trialDate;
//    }
//
//    public void setTrialDate(String trialDate) {
//        this.trialDate = trialDate;
//    }

    public Integer getUnfinishCount() {
        return unfinishCount;
    }

    public void setUnfinishCount(Integer unfinishCount) {
        this.unfinishCount = unfinishCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getHasRecord() {
        return hasRecord;
    }

    public void setHasRecord(String hasRecord) {
        this.hasRecord = hasRecord;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
