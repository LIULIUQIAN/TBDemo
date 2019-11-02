package com.example.taobaodemo.bean.category;

import com.example.taobaodemo.bean.BaseBean;

public class Category extends BaseBean {

    private String name;
    private String sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
