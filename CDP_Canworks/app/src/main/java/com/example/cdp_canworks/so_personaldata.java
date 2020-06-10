package com.example.cdp_canworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



public class so_personaldata extends AppCompatActivity {
    String age;//스피너 연령대 정보 받을 변수_b_Click()함수에서 사용을 위해 전역 선언
    Boolean Sharing = false;//check box 버튼 기본 상태값
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
                //Toast.makeText(adapterView.getContext(), Sadapter.getItem(i).toString(),Toast.LENGTH_LONG).show();
                age = Sadapter.getItem(i).toString();//연령대 정보 받기
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void b_Click(View v){//스토어 카테고리 누른 후 액티비티(storelist 액티비티) 이동 & 개인정보 DB에 저장
        CheckBox agree = (CheckBox) findViewById(R.id.agree);//체크박스 참조
        if(agree.isChecked()){//personal data sharing agree
            Sharing = true;
        }
        else{
            Sharing = false;
        }
        if(Sharing == true) {
            Intent intent = new Intent(so_personaldata.this, so_MainActivity.class);

            //db helper를 통한 자료 받아오기
            DBHelper dbHelper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);//dbhelper 객체 생성
            //SQLiteDatabase db = dbHelper.getWritableDatabase();//dbhelper를 이용하여 canworks db 열기

            EditText NameInput = (EditText) findViewById(R.id.NameInput);//이름 editText 참조
            EditText PhoneInput = (EditText) findViewById(R.id.PhoneInput);//연락처 editText 참조
            EditText MailInput = (EditText) findViewById(R.id.MailInput);//메일 editText 참조
            RadioGroup genderButton = (RadioGroup) findViewById(R.id.radioGroup);//성별 버튼 참조
            int gender = 0;

            String Name = NameInput.getText().toString();//editText로부터 이름 입력받기
            String Phone = PhoneInput.getText().toString();//editText로부터 연락처 입력받기
            String Mail = MailInput.getText().toString();//editText로부터 메일 입력받기

            if (genderButton.getCheckedRadioButtonId() == R.id.FemaleRadio)//여자인 경우
                gender = 1;
            else if (genderButton.getCheckedRadioButtonId() == R.id.MaleRadio)//남자인 경우
                gender = 0;

            dbHelper.updateLabelInfo(Name, Phone, Mail, gender, age);//개인정보 DB 내 label_info에 추가

            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please Fill the blank & Check the agreement", Toast.LENGTH_LONG).show();
        }

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