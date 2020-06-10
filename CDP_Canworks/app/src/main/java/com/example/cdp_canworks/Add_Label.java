package com.example.cdp_canworks;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Add_Label extends AppCompatActivity {

    TextView storeURL;
    TextView storeId;
    EditText labelNum;
    TextView labelURL;
    ImageView QRcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_label);

        Intent intent = getIntent();
        final String number = intent.getExtras().getString("storeNumber");
        String id = "";

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar6);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DBHelper helper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);
        Cursor cursor = helper.getStoreInfo(number);
        while(cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex("storeId"));
        }

        Button useQR = (Button)findViewById(R.id.useQR);
        useQR.setEnabled(false);

        storeURL = (TextView)findViewById(R.id.storeURL);
        storeId = (TextView)findViewById(R.id.storeId);
        labelNum = (EditText)findViewById(R.id.labelNum);
        labelURL = (TextView)findViewById(R.id.labelURL);
        QRcode = (ImageView)findViewById(R.id.QRcode);

        storeURL.setText("http://canworks.com/shop/"+id);
        storeId.setText(id);
        labelURL.setText("http://canworks.com/shop/"+id);
        createQR(id); //매장 ID로 QR코드 생성

        final Bitmap bitmap = ((BitmapDrawable)QRcode.getDrawable()).getBitmap();

        Button download = (Button)findViewById(R.id.downloadQR);
        download.setOnClickListener(new View.OnClickListener() { //이미지를 갤러리에 저장
            @Override
            public void onClick(View view) {
                String StoragePath =
                        Environment.getExternalStorageDirectory().getAbsolutePath();
                String savePath = StoragePath + "/canworks";
                File f = new File(savePath);
                if (!f.isDirectory()) f.mkdirs();
                FileOutputStream out = null;
                try{
                    out=new FileOutputStream(StoragePath+"/"+storeId.getText().toString()+"QR.jpg");
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    Toast.makeText(getApplicationContext(), "갤러리에 정상적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        final Button finish = (Button)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.setLabelNum(labelNum.getText().toString(), storeId.getText().toString());
                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); //이미지뷰의 QR코드 추출하여
                    byte[] code = stream.toByteArray(); //바이트 배열로 변환
                    helper.updateQRcode(code, storeId.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(Add_Label.this, Label_Info.class);
                    i.putExtra("storeNumber", number);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent backIntent = new Intent(Add_Label.this, Label_Info.class);
                startActivity(backIntent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void createQR(String text) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        byte[] code;
        try { //QR코드 생성부
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            QRcode.setImageBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            code = stream.toByteArray();
            String QRcodeLength = Integer.toString(code.length);
            Log.i("Length of QRcode", QRcodeLength);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
