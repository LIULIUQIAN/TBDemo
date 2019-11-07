package com.example.taobaodemo.bean;

import java.io.Serializable;

public class Address implements Serializable {

    private Long id;

    private String consignee;
    private String phone;
    private String addr;
    private String detailed;
    private Boolean isDefault;

    public Address() {
    }

    public Address(String consignee, String phone, String addr, String detailed) {
        this.consignee = consignee;
        this.phone = phone;
        this.addr = addr;
        this.detailed = detailed;
        this.isDefault = false;
        this.id = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }


    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }
}
