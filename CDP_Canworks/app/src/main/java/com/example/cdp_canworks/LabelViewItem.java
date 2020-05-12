package com.example.cdp_canworks;

import android.graphics.drawable.Drawable;


public class LabelViewItem {
    private Drawable labelImage;
    private String labelName;
    private String str2;
    private String storeId;

    public void setLabelImage(Drawable icon) {
        labelImage = icon;
    }

    public void setLabelName(String name) {
        labelName = name;
    }

    public void setStr2(String str) {
        str2 = str;
    }

    public void setStoreId(String id) {
        storeId = id;
    }

    public Drawable getLabelImage() {
        return this.labelImage;
    }

    public String getLabelName() {
        return this.labelName;
    }

    public String getStr2() {
        return this.str2;
    }

    public String getStoreId() {
        return this.storeId;
    }
}
