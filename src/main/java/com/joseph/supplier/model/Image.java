package com.joseph.supplier.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_image")
public class Image implements Serializable {

    @Id
    private String style;

    private String imgs;

    public Image() {
    }

    public Image(String style, String imgs) {
        this.style = style;
        this.imgs = imgs;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
