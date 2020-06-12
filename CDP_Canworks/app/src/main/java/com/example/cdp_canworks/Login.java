package com.example.cdp_canworks;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private long time;
    String input_id;
    String input_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final DBHelper helper = new DBHelper(getApplicationContext(), "canworks.db", null, 1); //DB 관리자 생성
        final EditText id = (EditText)findViewById(R.id.id);
        final EditText pwd = (EditText)findViewById(R.id.pwd);

        //로그인
        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_id = id.getText().toString();
                input_pwd = pwd.getText().toString();
                Cursor cursor = helper.Login(input_id, input_pwd); //내부 db에 저장된 값 불러오기 - member_info 테이블의 name 항목, 없으면 "\0" 반환
                String nickname="\0";
                int grade = 100;

                while(cursor.moveToNext()) {
                    grade = cursor.getInt(cursor.getColumnIndex("grade")); //계정 등급 - 0: 운영자, 1: 매장관리자, 2: 고객
                    nickname = cursor.getString(cursor.getColumnIndex("name")); //계정 닉네임
                }
              
                if (grade == 0) { //운영자 페이지 이동
                    Intent intent = new Intent(Login.this, Main.class);
                    Toast.makeText(getApplicationContext(), nickname+"님 환영합니다!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else if(grade==1) { //매장관리자 페이지 이동 - 구현예정
                    Toast.makeText(getApplicationContext(), nickname+"님 환영합니다!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button customer = (Button)findViewById(R.id.login2);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //소비자 페이지 이동
                Intent intent = new Intent(Login.this, so_MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //뒤로가기 버튼 두번 클릭시 종료
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로가기 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            super.onBackPressed();
            //앱 자체를 종료시키는 구문
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
