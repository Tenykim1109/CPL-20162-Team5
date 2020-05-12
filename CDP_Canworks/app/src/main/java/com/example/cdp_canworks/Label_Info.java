package com.example.cdp_canworks;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Label_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.label_info);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar5);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ListView listView;
        LabelViewAdapter adapter = new LabelViewAdapter();

        listView = (ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.knulogo), "CW01_500", "KNU Brewing");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.knulogo), "CW01_350", "KNU Brewing");
    }
}
