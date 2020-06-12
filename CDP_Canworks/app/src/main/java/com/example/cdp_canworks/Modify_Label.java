package com.example.cdp_canworks;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

public class Modify_Label extends AppCompatActivity {

    TextView storeURL;
    TextView storeId;
    TextView labelNum;
    TextView labelURL;
    ImageView QRcode;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_label);

        Intent intent = getIntent();
        number = intent.getExtras().getString("storeNumber");
        String id = "";
        String labelNumber = "";
        Bitmap code=null;

        final DBHelper helper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);
        Cursor cursor = helper.getStoreInfo(number);
        while(cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndex("storeId"));
            byte[] codeArray = cursor.getBlob(cursor.getColumnIndex("QRcode"));
            code = BitmapFactory.decodeByteArray(codeArray, 0, codeArray.length);
        }
        Toolbar tb = (Toolbar)findViewById(R.id.toolbar7);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storeURL = (TextView)findViewById(R.id.storeURL);
        storeId = (TextView)findViewById(R.id.storeId);
        labelNum = (TextView) findViewById(R.id.labelNum);
        labelURL = (TextView)findViewById(R.id.labelURL);
        QRcode = (ImageView)findViewById(R.id.QRcode);
        cursor = helper.getLabelInfo(id);
        while(cursor.moveToNext()) {
            labelNumber = cursor.getString(cursor.getColumnIndex("labelNum"));

        }
        storeURL.setText("http://canworks.com/shop/"+id);
        storeId.setText(id);
        labelNum.setText(labelNumber);
        labelURL.setText("http://canworks.com/shop/"+id);
        QRcode.setImageBitmap(code);

        Button share = (Button)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StoragePath =
                        Environment.getExternalStorageDirectory().getAbsolutePath();
                String savePath = StoragePath + "/canworks"+"/";
                String fileName = storeId.getText().toString()+"QR.jpg";
                BitmapDrawable d = (BitmapDrawable)QRcode.getDrawable();
                Bitmap bitmap = d.getBitmap();

                try {
                    File file = new File(savePath, fileName);
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID, file));
                    intent.setType("image/*");
                    startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final Button finish = (Button)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Modify_Label.this, Label_Info.class);
                i.putExtra("storeNumber", number);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent backIntent = new Intent(Modify_Label.this, Label_Info.class);
                backIntent.putExtra("storeNumber", number);
                startActivity(backIntent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
