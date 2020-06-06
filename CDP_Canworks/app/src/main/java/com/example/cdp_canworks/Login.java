package com.example.cdp_canworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private long time;
    String admin_id; // 운영자 id
    String admin_pwd; // 운영자 비밀번호
    String store_id; // 매장관리자 id
    String store_pwd; // 매장관리자 비밀번호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //로그인
        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Main.class);
                startActivity(intent);
                finish();

                String input_id = id.getText().toString();
                String input_pwd = pwd.getText().toString();
                String nickname = helper.Login(input_id, input_pwd); //내부 db에 저장된 값 불러오기 - member_info 테이블의 name 항목, 없으면 "\0" 반환
                if (nickname !="\0") {
                    Intent intent = new Intent(Login.this, Main.class);
                    Toast.makeText(getApplicationContext(), nickname+"님 환영합니다!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button customer = (Button)findViewById(R.id.login2);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        }
    }
}
