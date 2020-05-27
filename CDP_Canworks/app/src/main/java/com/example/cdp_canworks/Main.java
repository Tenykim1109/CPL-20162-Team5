package com.example.cdp_canworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Main extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ListView listView;
        ListViewAdapter adapter;
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();

        adapter = new ListViewAdapter(this) ;
        listView = (ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        adapter.addItem("캔웍스", "012345");
        adapter.addItem("카페 봉봉", "003235");
        adapter.addItem("커피왕", "082745");
        
        ImageButton logout = (ImageButton)findViewById(R.id.logout);
        ImageButton create = (ImageButton)findViewById(R.id.create);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Login.class);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Create_New_Store.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로가기 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            super.onBackPressed();
        }
    }
}
