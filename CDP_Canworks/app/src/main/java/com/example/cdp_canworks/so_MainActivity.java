package com.example.cdp_canworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class so_MainActivity extends AppCompatActivity {//소비자 페이지 메인 페이지_카테고리화

    //DBHelper 관련 코드
    //DBHelper dbHelper = new DBHelper(getApplicationContext(),"canworks.db",null,1);//dbhelper 객체 생성


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.so_main);//so_main layout 참조
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

                IntentIntegrator qr_scan = new IntentIntegrator(so_MainActivity.this);
                qr_scan.setPrompt("QR코드를 인식해주세요.");//qr코드 텍스트
                qr_scan.setBeepEnabled(false);//qr코드 인식 시 소리 끔
                qr_scan.setCaptureActivity(qr_scanner.class);//qr코드 테마 액티비티 불러오기
                qr_scan.setOrientationLocked(false);//화면에 따른 세로 또는 가로 모드

                qr_scan.initiateScan();
            }

        });

        //main_button
        /*Button cafe = (Button) findViewById(R.id.cafe_b);//cafe button
        Button beer = (Button) findViewById(R.id.beer_b);//beer button
        Button tea = (Button) findViewById(R.id.tea_b);//tea button
        Button mak = (Button) findViewById(R.id.mak_b);//makgeolli button*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);//QR코드 값 저장 변수

        if(result != null) {//qr코드를 읽은 경우
            if (result.getContents() == null) {//qr코드 값이 없는 경우
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {//qr코드 값이 있는 경우
                Intent intent = new Intent(so_MainActivity.this, so_customlabel.class);
                String storeId = result.getContents();//QR코드에 담긴 storeId 값 저장
                intent.putExtra("storeId",storeId);//해당 storeId 넘기기_맞는 라벨디자인 찾기 위해
                startActivity(intent);
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void b_Click(View v){//스토어 카테고리 누른 후 액티비티(storelist 액티비티) 이동
        Intent intent = new Intent(so_MainActivity.this, so_storelist.class);
        //intent.putExtra("category",);//category 넘겨주기
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//툴바 옵션 아이콘 컨트롤
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings: {
                return true;
            }
            case android.R.id.home:{
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
