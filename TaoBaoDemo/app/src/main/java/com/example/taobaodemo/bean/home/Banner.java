package com.example.taobaodemo.bean.home;

import com.example.taobaodemo.bean.BaseBean;

public class Banner extends BaseBean {

    private String name;
    private String imgUrl;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
