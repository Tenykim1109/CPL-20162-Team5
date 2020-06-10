package com.example.cdp_canworks;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Modify_Information extends AppCompatActivity {

    final int REQ_CODE_SELECT_IMAGE=1; //갤러리에서 사진 선택시 발생하는 requestCode
    Uri uri; //갤러리에서 가져온 사진을 uri 형태로 저장할 변수
    String default_storeNum; //기존 매장번호
    String input_name;
    String input_pwd;
    String input_add1;
    String input_add2;
    String input_phoneNum;
    String newImage;
    String number;

    TextView storeNum;
    EditText storeName;
    TextView storeId;
    EditText storePassword;
    EditText address1;
    EditText address2;
    EditText phoneNumber;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_information);

        Intent intent = getIntent();
        number = intent.getExtras().getString("storeNumber");

        final DBHelper helper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar4);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Cursor cursor = helper.getStoreInfo(number);

        Button addImage = (Button)findViewById(R.id.addImage);
        imageView = (ImageView)findViewById(R.id.store_image); //매장 이미지
        storeNum = (TextView)findViewById(R.id.storeNum); //매장번호
        storeName = (EditText)findViewById(R.id.storeName); //매장 이름
        storeId = (TextView)findViewById(R.id.storeId); //매장 ID
        storePassword = (EditText)findViewById(R.id.storePassword); //매장 PIN번호
        address1 = (EditText)findViewById(R.id.address1); //매장 주소
        address2 = (EditText)findViewById(R.id.address2); //매장 상세주소
        phoneNumber = (EditText)findViewById(R.id.phoneNumber); //매장 전화번호

        while(cursor.moveToNext()) {
            storeNum.setText(cursor.getString(1));
            storeName.setText(cursor.getString(2));
            storeId.setText(cursor.getString(3));
            storePassword.setText(cursor.getString(4));
            address1.setText(cursor.getString(5));
            address2.setText(cursor.getString(6));
            phoneNumber.setText(cursor.getString(7));
            imageView.setImageURI(Uri.parse("file://"+cursor.getString(8)));
        }
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //갤러리에서 이미지 선택
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setAction(Intent.ACTION_GET_CONTENT); //갤러리에서 이미지 선택
                startActivityForResult(intent, 1);
            }
        });

        final Button finish = (Button)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //매장정보 수정 적용
                default_storeNum = storeNum.getText().toString();
                input_name = storeName.getText().toString();
                input_pwd = storePassword.getText().toString();
                input_add1 = address1.getText().toString();
                input_add2 = address2.getText().toString();
                input_phoneNum = phoneNumber.getText().toString();
                newImage = getRealPathFromURI(uri);

                Intent intent = new Intent(Modify_Information.this, Main.class);
                helper.updateInfo(default_storeNum, input_name, input_pwd, input_add1, input_add2, input_phoneNum, newImage);
                Toast.makeText(getApplicationContext(), "정상적으로 수정되었습니다", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) { //Uri를 절대경로로 변환
        String[] proj = { MediaStore.Images.Media.DATA };
        String path;
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file path
            Log.d("path", "path is empty.");
            path = contentUri.getPath();
        }
        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path = cursor.getString(idx);
            Log.d("path", "path is not empty.");
            cursor.close();
        }
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    uri = data.getData();
//                    String name_str = getImageNameToUri(uri);
                    Log.i("Uri", uri.toString());
                    Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // 이미지뷰에 불러온 이미지 표시
                    imageView.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent backIntent = new Intent(Modify_Information.this, Store_Imformation.class);
                backIntent.putExtra("storeNumber", number);
                startActivity(backIntent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
