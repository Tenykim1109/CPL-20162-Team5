package com.example.cdp_canworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class so_storelist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_storelist);//so_main layout 참조
        Toolbar toolbar = findViewById(R.id.toolbar);//툴바 등록
        setSupportActionBar(toolbar);//툴바 액션바로 등록

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//툴바 내 뒤로가기 버튼
        getSupportActionBar().setDisplayShowTitleEnabled(false);//툴바 타이틀 없애기

        FloatingActionButton fab = findViewById(R.id.fab);//floating button_QR코드 인식 기능
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//QR코드 버튼 클릭 시
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/ //스낵바 코드

                IntentIntegrator qr_scan = new IntentIntegrator(so_storelist.this);
                qr_scan.setPrompt("QR코드를 인식해주세요.");//qr코드 텍스트
                qr_scan.setBeepEnabled(false);//qr코드 인식 시 소리 끔
                qr_scan.setCaptureActivity(qr_scanner.class);//qr코드 테마 액티비티 불러오기
                qr_scan.setOrientationLocked(false);//화면에 따른 세로 또는 가로 모드

                qr_scan.initiateScan();

                //EditText에 대한 이벤트 리스너 개발
                //Listview에 대한 리스트와 어댑터 개발
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);//QR코드 값 저장 변수

        if(result != null) {//qr코드를 읽은 경우
            if (result.getContents() == null) {//qr코드 값이 없는 경우
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {//qr코드 값이 있는 경우
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }
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
