package com.example.cdp_canworks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Add_Label extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_label);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar6);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
