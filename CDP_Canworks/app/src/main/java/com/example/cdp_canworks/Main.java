package com.example.cdp_canworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ListView listView;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        adapter.addItem("캔웍스", "012345");
        adapter.addItem("카페 봉봉", "003235");
        adapter.addItem("커피왕", "082745");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListViewItem item = (ListViewItem)adapterView.getItemAtPosition(position);

                String str = item.getStore();
                String storeName = item.getStoreName();
                String num = item.getNum();
                String storeNum = item.getStoreNumber();
                Button store = item.getStoreButton();
                Button label = item.getLabelButton();

                store.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Main.this, Store_Imformation.class);
                        startActivity(intent);
                    }
                });
            }
        });

        ImageButton btn = (ImageButton)findViewById(R.id.logout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Login.class);
                Toast.makeText(Main.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT);
                startActivity(intent);
                finish();
            }
        });

    }
}
