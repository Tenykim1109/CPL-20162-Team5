package com.example.cdp_canworks;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureActivity;

public class qr_scanner extends CaptureActivity {//qr코드 배경

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(//Linear Layout
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );



        TextView title = new TextView(this);
        title.setLayoutParams(new LinearLayout.LayoutParams
                (WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)); //put title textview on layout
        title.setTextColor(Color.parseColor("#FFFFFF")); //set title textview color
        title.setText("매장 QR 코드 입력");
        title.setTextSize(30);
        title.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addContentView(title,params);


    }
}
