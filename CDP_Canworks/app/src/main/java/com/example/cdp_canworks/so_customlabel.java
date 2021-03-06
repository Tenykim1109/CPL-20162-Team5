package com.example.cdp_canworks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class so_customlabel extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_customlabel);//so_main layout 참조
        Toolbar toolbar = findViewById(R.id.toolbar);//툴바 등록
        setSupportActionBar(toolbar);//툴바 액션바로 등록

        //intent를 통해 해당 매장의 storeNum 받아오기_라벨디자인 호출을 위해
        Intent Intent = getIntent();
        String storeId = Intent.getStringExtra("storeId");//해당 가게의 id intent로 받아오기

        //db helper를 통한 자료 받아오기
        //String sql = "SELECT * FROM store_info WHERE storeName=" + storeName;//해당 stoerNum이용한 쿼리
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);//dbhelper 객체 생성

        String emblemPath = dbHelper.getStoreImage(storeId);
        ImageView view = (ImageView)findViewById(R.id.labelView);
        view.setImageURI(Uri.parse("file://"+emblemPath));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//툴바 내 뒤로가기 버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false);//툴바 타이틀 없애기

        //Intent storeNum = getIntent();//storeNum 정보 가져오기
        //storeNum에 맞는 라벨디자인 불러오기 DB이용
    }

    public void b_Click(View v){//스토어 카테고리 누른 후 액티비티(storelist 액티비티) 이동
        Intent intent = new Intent(so_customlabel.this, com.example.cdp_canworks.so_personaldata.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//툴바 옵션 아이콘 컨트롤
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings: {
                return true;
            }
            case android.R.id.home: {
                finish();
                return true;
            }
        }


        /*if (id == R.id.action_settings) {
            return true;
            원래 툴바 설정 코드
        }*/


        return super.onOptionsItemSelected(item);
    }
}
