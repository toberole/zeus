package com.cat.zeus.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cat.anno.ZeusAPTBindView;
import com.cat.zeus.R;


public class MainActivity extends AppCompatActivity {
    @ZeusAPTBindView(R.id.sample_text)
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv.setText("hello");
    }
}
