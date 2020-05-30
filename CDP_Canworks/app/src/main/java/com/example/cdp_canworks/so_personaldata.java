package com.example.cdp_canworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class so_personaldata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_personaldata);////so_personaldata layout 참조
        Toolbar toolbar = findViewById(R.id.toolbar);//툴바 등록
        setSupportActionBar(toolbar);//툴바 액션바로 등록

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//툴바 내 뒤로가기 버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false);//툴바 타이틀 없애기

        final Spinner ageSpinner = (Spinner)findViewById(R.id.ageSpinner);//ageSpinner xml에서 불러오기
        final ArrayAdapter Sadapter = ArrayAdapter.createFromResource(this, R.array.age,android.R.layout.simple_spinner_dropdown_item);//배열 어댑터에 스피너 배열 연결
        ageSpinner.setAdapter(Sadapter);//어댑터와 스피너 연결
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //스피너에 리스너 장착
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {//스피너에서 선택시 동작
                //ageSpinner.getSelectedItem() : 선택된 아이템 얻기
                Toast.makeText(adapterView.getContext(), Sadapter.getItem(i).toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void b_Click(View v){//스토어 카테고리 누른 후 액티비티(storelist 액티비티) 이동
        Intent intent = new Intent(so_personaldata.this, so_MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//툴바 옵션 아이콘 컨트롤
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings: {
                return true;
            }
            case android.R.id.home:{
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
