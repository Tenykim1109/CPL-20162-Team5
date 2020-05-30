package com.example.cdp_canworks;

public class ListViewItem {
    private String store;
    private String storeName;
    private String num;
    private String storeNumber;


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
