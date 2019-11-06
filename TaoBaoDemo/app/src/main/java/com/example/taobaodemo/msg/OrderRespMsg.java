package com.example.taobaodemo.msg;

import com.example.taobaodemo.bean.cart.Charge;

public class OrderRespMsg {

    private String orderNum;

    private Charge charge;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }
}
