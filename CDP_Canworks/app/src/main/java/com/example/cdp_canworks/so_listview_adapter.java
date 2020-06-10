package com.example.cdp_canworks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class so_listview_adapter extends BaseAdapter implements Filterable {

    //원본 storeList 배열
    private ArrayList<so_listview_data> storeList = new ArrayList<so_listview_data>();//새로 정의한 리스트뷰 데이터 형식으로 배열 생성

    //Filter관련 코드_filter된 StoreList 배열
    private ArrayList<so_listview_data> filteredStoreList = storeList;
    Filter listFilter;

    public so_listview_adapter(){//so_listview_adapter 생성자
    }


    @Override
    public int getCount() {//Adapter에 사용되는 데이터 개수 리턴
        return filteredStoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredStoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {//position에 위한 데이터를 화면에 출력하는데 사용될 View 리턴
        final int pos = position;
        final Context context = viewGroup.getContext();

        if(view == null){//so_listview_item 레이아웃을 inflate -> view 참조 획득
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.so_listview_item,viewGroup,false);
        }

        //화면에 표시될 view로부터 위젯에 대한 참조 획득
        ImageView storeIcon = (ImageView) view.findViewById(R.id.storeIcon);
        TextView storeName = (TextView) view.findViewById(R.id.storeName);
        TextView storeAddress = (TextView) view.findViewById(R.id.storeAddress);
        TextView storePhone = (TextView) view.findViewById(R.id.storePhone);

        //storelist에서 position에 위치한 데이터 참조 획득
        so_listview_data listview_data = filteredStoreList.get(position);

        storeIcon.setImageDrawable(listview_data.getStoreIcon());
        storeName.setText(listview_data.getStoreName());
        storeAddress.setText(listview_data.getStoreAddress());
        storePhone.setText(listview_data.getStorePhone());

        return view;
    }

    public void addItem(Drawable icon, String name, String address, String phone){
        so_listview_data item = new so_listview_data();//커스텀한 데이터 형식(클래스)에 맞춘 객체 생성

        item.setStoreIcon(icon);
        item.setStoreName(name);
        item.setStoreAddress(address);
        item.setStorePhone(phone);

        storeList.add(item);//storelist에 item(가게 정보) 등록
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null)
            listFilter = new ListFilter();//커스텀 필터 객체 생성

        return listFilter;
    }


    //커스텀 필터 클래스
    private class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();//필터링 결과

            if(constraint == null || constraint.length() == 0){
                results.values = storeList;
                results.count = storeList.size();
            }
            else{
                ArrayList<so_listview_data> itemList = new ArrayList<so_listview_data>();//필터링 결과 저장한 커스텀 배열

                for(so_listview_data item : storeList){//storeList 내 각 객체들을 storeName으로 검색 후 커스텀 배열 itemList에 따로 저장
                    if(item.getStoreName().toUpperCase().contains(constraint.toString().toUpperCase())){
                        itemList.add(item);
                    }
                }

                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredStoreList = (ArrayList<so_listview_data>) filterResults.values;//필터링된 결과 새로운 커스텀 배열에 저장

            if(filterResults.count > 0){//필터링 결과가 있다면
                notifyDataSetChanged();
            }
            else{//필터링 결과가 없다면
                notifyDataSetInvalidated();
            }
        }
    }
}