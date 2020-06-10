package com.example.cdp_canworks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter implements Filterable {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    Context ctx;

    public ListViewAdapter(Activity act) {
        ctx = act;
    }

    //Filter관련 코드_filter된 StoreList 배열
    private ArrayList<ListViewItem> filteredStoreList = listViewItemList;
    Filter listFilter;

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return filteredStoreList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {//position에 위한 데이터를 화면에 출력하는데 사용될 View 리턴
        final int pos = position;
        final Context context = viewGroup.getContext();

        if(view == null){//so_listview_item 레이아웃을 inflate -> view 참조 획득
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listview, viewGroup, false);
        }

        //화면에 표시될 view로부터 위젯에 대한 참조 획득
        TextView storeName = (TextView) view.findViewById(R.id.storeName) ;
        TextView storeNum = (TextView) view.findViewById(R.id.storeNum) ;

        //storelist에서 position에 위치한 데이터 참조 획득
        final ListViewItem listViewItem = filteredStoreList.get(position);

        Button storeButton = (Button)view.findViewById(R.id.store);
        Button labelButton = (Button)view.findViewById(R.id.label);


        storeName.setText(listViewItem.getStoreName());
        storeNum.setText(listViewItem.getStoreNumber());

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //매장 버튼 클릭시 페이지 이동
                movePage(ctx, Store_Imformation.class, listViewItem.getStoreNumber());
            }
        });

        labelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movePage(ctx, Label_Info.class, listViewItem.getStoreNumber());
            }
        });

        return view;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return filteredStoreList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, String number) {
        ListViewItem item = new ListViewItem();

        item.setStoreName(name);
        item.setStoreNumber(number);

        listViewItemList.add(item);
    }

    public void movePage(Context context, Class<?> cls, String number) {
        Intent i = new Intent(context, cls);
        i.putExtra("storeNumber", number);
        context.startActivity(i);
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null)
            listFilter = new ListViewAdapter.ListFilter();//커스텀 필터 객체 생성

        return listFilter;
    }


    //커스텀 필터 클래스 - 검색
    private class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();//필터링 결과

            if(constraint == null || constraint.length() == 0){
                results.values = listViewItemList;
                results.count = listViewItemList.size();
            }
            else{
                ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();//필터링 결과 저장한 커스텀 배열

                for(ListViewItem item : listViewItemList){//storeList 내 각 객체들을 storeName으로 검색 후 커스텀 배열 itemList에 따로 저장
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
            filteredStoreList = (ArrayList<ListViewItem>) filterResults.values;//필터링된 결과 새로운 커스텀 배열에 저장

            if(filterResults.count > 0){//필터링 결과가 있다면
                notifyDataSetChanged();
            }
            else{//필터링 결과가 없다면
                notifyDataSetInvalidated();
            }
        }
    }

}
