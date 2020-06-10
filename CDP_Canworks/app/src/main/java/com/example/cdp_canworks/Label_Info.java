package com.example.cdp_canworks;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Label_Info extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String number;
    String storeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.label_info);

        Intent intent = getIntent();
        number = intent.getExtras().getString("storeNumber");
        final DBHelper helper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar5);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView;
        LabelViewAdapter adapter;
        ArrayList<LabelViewItem> items = new ArrayList<LabelViewItem>();

        // 리스트뷰에 라벨 정보 출력
        adapter = new LabelViewAdapter();
        listView = (ListView)findViewById(R.id.listview2);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        Cursor storeCursor = helper.getStoreInfo(number);
        while(storeCursor.moveToNext()) {
            storeId = storeCursor.getString(storeCursor.getColumnIndex("storeId"));
        }
        Cursor labelCursor = helper.getLabelInfo(storeId);

        while(labelCursor.moveToNext()) {
            adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.knulogo), labelCursor.getString(labelCursor.getColumnIndex("labelNum")), storeId);
        }

        Button addLabel = (Button)findViewById(R.id.addLabel);
        addLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //라벨 추가 페이지로 이동
                Intent i = new Intent(Label_Info.this, Add_Label.class);
                i.putExtra("storeNumber", number);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //라벨 이미지 클릭
        Intent intent = new Intent(Label_Info.this, Modify_Label.class);
        intent.putExtra("storeNumber", number);
        startActivity(intent); //라벨 수정 페이지로 이동
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent backIntent = new Intent(Label_Info.this, Main.class);
                startActivity(backIntent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
