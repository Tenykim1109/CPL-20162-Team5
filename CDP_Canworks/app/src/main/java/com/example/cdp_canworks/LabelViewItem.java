package com.example.cdp_canworks;

import android.graphics.drawable.Drawable;


public class LabelViewItem {
    private Drawable labelImage;
    private String labelName;
    private String storeId;

    public void setLabelImage(Drawable icon) {
        labelImage = icon;
    }

    public void setLabelName(String name) {
        labelName = name;
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

    public String getStoreId() {
        return this.storeId;
    }
}
