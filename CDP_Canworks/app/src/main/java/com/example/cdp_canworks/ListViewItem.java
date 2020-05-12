package com.example.cdp_canworks;

import android.widget.Button;

public class ListViewItem {
    private Button storeButton;
    private Button labelButton;
    private String store;
    private String storeName;
    private String num;
    private String storeNumber;

    public void setStoreButton(Button store) {
        storeButton = store;
    }

    public void setLabelButton(Button label) {
        labelButton = label;
    }

    public void setStore(String str) {
        store = str;
    }

    public void setStoreName(String name) {
        storeName = name;
    }

    public void setNum(String id) {
        num = id;
    }

    public void setStoreNumber(String number) {
        storeNumber = number;
    }

    public Button getStoreButton() {
        return this.storeButton;
    }

    public Button getLabelButton() {
        return this.labelButton;
    }

    public String getStore() {
        return this.store;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public String getNum() {
        return this.num;
    }

    public String getStoreNumber() {
        return this.storeNumber;
    }

}
