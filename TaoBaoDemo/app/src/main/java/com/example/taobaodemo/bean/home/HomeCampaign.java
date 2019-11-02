package com.example.taobaodemo.bean.home;

import java.io.Serializable;

public class HomeCampaign implements Serializable {

    private String id;
    private String title;
    private String campaignOne;
    private String campaignTwo;
    private String campaignThree;
    private Campaign cpOne;
    private Campaign cpTwo;
    private Campaign cpThree;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCampaignOne() {
        return campaignOne;
    }

    public void setCampaignOne(String campaignOne) {
        this.campaignOne = campaignOne;
    }

    public String getCampaignTwo() {
        return campaignTwo;
    }

    public void setCampaignTwo(String campaignTwo) {
        this.campaignTwo = campaignTwo;
    }

    public String getCampaignThree() {
        return campaignThree;
    }

    public void setCampaignThree(String campaignThree) {
        this.campaignThree = campaignThree;
    }

    public Campaign getCpOne() {
        return cpOne;
    }

    public void setCpOne(Campaign cpOne) {
        this.cpOne = cpOne;
    }

    public Campaign getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(Campaign cpTwo) {
        this.cpTwo = cpTwo;
    }

    public Campaign getCpThree() {
        return cpThree;
    }

    public void setCpThree(Campaign cpThree) {
        this.cpThree = cpThree;
    }
}
