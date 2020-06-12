package com.example.cdp_canworks;

//listview data definition
import android.graphics.drawable.Drawable;

public class so_listview_data {
    private Drawable storeIcon;
    private String storeName;
    private String storeAddress;
    private String storePhone;

    public void setStoreIcon(Drawable icon){
        storeIcon = icon;
    }

    public void setStoreName(String Name){
        storeName = Name;
    }

    public void setStoreAddress(String address){
        storeAddress = address;
    }

    public void setStorePhone(String phone){
        storePhone = phone;
    }

    public Drawable getStoreIcon(){ return this.storeIcon; }

    public String getStoreName(){
        return this.storeName;
    }

    public String getStoreAddress(){
        return this.storeAddress;
    }

    public String getStorePhone(){
        return this.storePhone;
    }


}