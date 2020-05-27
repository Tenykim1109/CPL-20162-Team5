package com.example.cdp_canworks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class LabelViewAdapter extends BaseAdapter {
    private ArrayList<LabelViewItem> labelViewItemList = new ArrayList<LabelViewItem>();

    public LabelViewAdapter() {

    }

    @Override
    public int getCount() {
        return labelViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.label_custom_listview, parent, false);
        }

        ImageView labelImage = (ImageView)convertView.findViewById(R.id.label_image);
        TextView labelName = (TextView)convertView.findViewById(R.id.label_name);
        TextView storeId = (TextView)convertView.findViewById(R.id.store_id);

        LabelViewItem labelViewItem = labelViewItemList.get(position);

        labelImage.setImageDrawable(labelViewItem.getLabelImage());
        labelName.setText(labelViewItem.getLabelName());
        storeId.setText(labelViewItem.getStoreId());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public Object getItem(int position) {
        return labelViewItemList.get(position) ;
    }


    public void addItem(Drawable icon, String name, String id) {
        LabelViewItem item = new LabelViewItem();

        item.setLabelImage(icon);
        item.setLabelName(name);
        item.setStoreId(id);

        labelViewItemList.add(item);
    }
}
