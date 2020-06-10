package com.example.cdp_canworks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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

        //db helper를 통한 자료 받아오기
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);//dbhelper 객체 생성
        SQLiteDatabase db = dbHelper.getReadableDatabase();//dbhelper를 이용하여 canworks db 열기
        Cursor cursor = db.rawQuery("SELECT * FROM store_info",null);//select 쿼리 던지기



        //listview 관련 코드
        final so_listview_adapter adapter = new so_listview_adapter();//so_listview_adapater로 커스텀한 어댑터 객체 생성
        final ListView listview = (ListView)findViewById(R.id.storelistView);//store listview 객체 생성

        listview.setAdapter(adapter);//listview와 adapter 연결

        //listview 필터 관련 코드
        EditText storeSearch = (EditText)findViewById(R.id.storeSearch);
        storeSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable edit) {//검색창이 변화가 있다면
                String filterText = edit.toString();
                ((so_listview_adapter)listview.getAdapter()).getFilter().filter(filterText);

                /*if(filterText.length() > 0){//검색어가 있다면
                    listview.setFilterText(filterText);//lisetview의 setFilterText함수를 이용하여 필터링 수행
                }
                else {
                    listview.clearTextFilter();
                }*/
            }
        });

        if(cursor != null){//커서를 이용하여 DB에서 select된 레코드 집합 읽어오기
            if(cursor.moveToFirst()){//Cursor를 제일 첫번째 행으로 이동
                do{//해당 레코드 집합 중 가게 정보 부분 읽어서 리스트뷰 어댑터에 추가
                    String storeName = cursor.getString(cursor.getColumnIndex("storeName"));
                    String storeAddress = cursor.getString(cursor.getColumnIndex("address1"));
                    String storePhone = cursor.getString(cursor.getColumnIndex("phone"));

                    adapter.addItem(ContextCompat.getDrawable(this, R.drawable.knu),storeName,storeAddress,storePhone);
                } while(cursor.moveToNext());
            }
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {//listview 객체 클릭 시, 해당 객체의 storeId와 함께 intent
            @Override
            public void onItemClick(AdapterView listview, View view, int position, long id) {//listview 아이템 정보 참조 가능
                String storeId = "0000";//storeId 입력받을 변수
                so_listview_data object = (so_listview_data) listview.getItemAtPosition(position);//클릭된 리스트뷰 내의 가게 넘버 참조
                String sql = "SELECT storeId FROM store_info WHERE storeName=" + "\'" + object.getStoreName() + "\'";//해당 stoerNum이용한 쿼리
                SQLiteDatabase db = dbHelper.getReadableDatabase();//dbhelper를 이용하여 canworks db 열기
                Cursor cursor2 = db.rawQuery(sql,null);//select 쿼리 던지기
                if(cursor2 != null){
                    if(cursor2.moveToFirst()){
                        do{
                            storeId = cursor2.getString(cursor2.getColumnIndex("storeId"));//커서로부터 storeId 값 읽어오기
                        } while(cursor2.moveToNext());
                    }
                }

                Intent intent = new Intent(so_storelist.this, so_customlabel.class);
                intent.putExtra("storeId",storeId);//intent로 storeId 넘겨주기_해당 라벨디자인 열기 위해
                startActivity(intent);
            }
        });



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
        String storeId = "0000";//storeId 입력받을 변수

        if(result != null) {//qr코드를 읽은 경우
            if (result.getContents() == null) {//qr코드 값이 없는 경우
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {//qr코드 값이 있는 경우
                Intent intent = new Intent(so_storelist.this,so_customlabel.class);
                storeId = result.getContents();//QR코드에 담긴 storeId 값 저장
                intent.putExtra("storeId",storeId);//해당 storeId 넘기기_맞는 라벨디자인 찾기 위해
                startActivity(intent);
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
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
