package com.example.cdp_canworks;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Store_Imformation extends AppCompatActivity {

    TextView storeNum;
    TextView storeName;
    TextView storeId;
    TextView storePassword;
    TextView address1;
    TextView address2;
    TextView phoneNum;
    ImageView emblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_imformation);

        Intent intent = getIntent();
        String number = intent.getExtras().getString("storeNumber");

        storeNum = (TextView)findViewById(R.id.storeNum);
        storeName = (TextView)findViewById(R.id.storeName);
        storeId = (TextView)findViewById(R.id.storeId);
        storePassword = (TextView)findViewById(R.id.storePassword);
        address1 = (TextView)findViewById(R.id.address1);
        address2 = (TextView)findViewById(R.id.address2);
        phoneNum = (TextView)findViewById(R.id.phoneNumber);
        emblem = (ImageView)findViewById(R.id.emblem);

        final DBHelper helper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);
        Cursor cursor = helper.getStoreInfo(number);

        while(cursor.moveToNext()) {
            storeNum.setText(cursor.getString(1));
            storeName.setText(cursor.getString(2));
            storeId.setText(cursor.getString(3));
            storePassword.setText(cursor.getString(4));
            address1.setText(cursor.getString(5));
            address2.setText(cursor.getString(6));
            phoneNum.setText(cursor.getString(7));
            emblem.setImageURI(Uri.parse("file://"+cursor.getString(8)));
        }

        //상단 툴바 부분
        Toolbar tb = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button imageUnable = (Button)findViewById(R.id.store_image_unable);
        imageUnable.setEnabled(false);
        Button modify = (Button)findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //매장정보 수정 페이지로 이동
                Intent modifyIntent = new Intent(Store_Imformation.this, Modify_Information.class);
                modifyIntent.putExtra("storeNum", storeNum.getText().toString());
                startActivity(modifyIntent);
//                finish();
            }
        });

        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //매장정보 삭제
                Intent deleteIntent = new Intent(Store_Imformation.this, Main.class);
                helper.deleteInfo(storeId.getText().toString());
                Toast.makeText(getApplicationContext(), "정상적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                startActivity(deleteIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent backIntent = new Intent(Store_Imformation.this, Main.class);
                startActivity(backIntent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
