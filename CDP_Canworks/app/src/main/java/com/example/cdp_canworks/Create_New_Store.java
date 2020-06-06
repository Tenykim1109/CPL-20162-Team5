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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;


public class Create_New_Store extends AppCompatActivity {

    ImageView imageView;
    String storeNum;
    String storeName;
    String storeId;
    String storePassword;
    String address1;
    String address2;
    String phoneNumber;
    String emblem;
    Uri uri;

    private File tempFile;
    final int REQ_CODE_SELECT_IMAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_store);

        final DBHelper helper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);

        //툴바 설정
        Toolbar tb = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView)findViewById(R.id.new_image);
        Button createImage = (Button)findViewById(R.id.create_image);
        createImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //갤러리에서 이미지 선택
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setAction(Intent.ACTION_GET_CONTENT); //갤러리에서 이미지 선택
                startActivityForResult(intent, 1);
            }
        });
        final EditText input_name = (EditText)findViewById(R.id.storeName);
        final EditText input_id = (EditText)findViewById(R.id.storeId);
        final EditText input_pwd = (EditText)findViewById(R.id.storePassword);
        final EditText input_add1 = (EditText)findViewById(R.id.address1);
        final EditText input_add2 = (EditText)findViewById(R.id.address2);
        final EditText input_phoneNum = (EditText)findViewById(R.id.phoneNumber);


        Button finish = (Button)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeNum = numberGen(6, 2); //6자리 임의 숫자 생성
                storeName = input_name.getText().toString();
                storeId = input_id.getText().toString();
                storePassword = input_pwd.getText().toString();
                address1 = input_add1.getText().toString();
                address2 = input_add2.getText().toString();
                phoneNumber = input_phoneNum.getText().toString();
                emblem = getRealPathFromURI(uri);
                try { //QR코드 생성부
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    BitMatrix bitMatrix = multiFormatWriter.encode(storeId, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] QRcode = stream.toByteArray();
                    String QRcodeLength = Integer.toString(QRcode.length);
                    Log.i("Length of QRcode", QRcodeLength);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                helper.createStore(storeNum, storeName, storeId, storePassword, address1, address2, phoneNumber, emblem);

                Intent intent = new Intent(Create_New_Store.this, Main.class);
                Toast.makeText(getApplicationContext(), "정상적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            // Make sure the request was successful
            Cursor cursor = null;
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    uri = data.getData();
                    String name_str = getImageNameToUri(uri);
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
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else { //이미지 선택하지 않았을 경우
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        //Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }
            return;
        }

    }

    public String getImageNameToUri(Uri data) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent backIntent = new Intent(Create_New_Store.this, Main.class);
                startActivity(backIntent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 전달된 파라미터에 맞게 난수를 생성한다
     * @param len : 생성할 난수의 길이
     * @param dupCd : 중복 허용 여부 (1: 중복허용, 2:중복제거)
     *
     * Created by 닢향
     * http://niphyang.tistory.com
     */
    public static String numberGen(int len, int dupCd) {

        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++) {

            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));

            if(dupCd==1) {
                //중복 허용시 numStr에 append
                numStr += ran;
            }else if(dupCd==2) {
                //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if(!numStr.contains(ran)) {
                    //중복된 값이 없으면 numStr에 append
                    numStr += ran;
                }else {
                    //생성된 난수가 중복되면 루틴을 다시 실행한다
                    i-=1;
                }
            }
        }
        return numStr;
    }


    /*public Byte[] createQR (String text) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Bitmap bitmap;
        Byte[] QRcode;
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress()
    }*/
}
