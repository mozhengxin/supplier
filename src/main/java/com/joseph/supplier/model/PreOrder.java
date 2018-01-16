package com.joseph.supplier.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_pre_order")
public class PreOrder implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String style;

    private String color;

    private String imgs;

    private int num;

    private String ctime;

    private String brand;

    private int isFinish;

    private Date finishTime;

    private Date recentFinishTime;

    @Transient
    private int isToday;

    @Transient
    private List<PreOrderDetail> details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public List<PreOrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PreOrderDetail> details) {
        this.details = details;
    }

    public int getIsToday() {
        return isToday;
    }

    public void setIsToday(int isToday) {
        this.isToday = isToday;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getRecentFinishTime() {
        return recentFinishTime;
    }

    public void setRecentFinishTime(Date recentFinishTime) {
        this.recentFinishTime = recentFinishTime;
    }
}
