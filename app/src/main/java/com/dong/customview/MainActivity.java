package com.dong.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dong.customview.flowlayout.FlowLayoutDemoActivity;
import com.dong.customview.shader.RadarActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 雷达
     * @param view
     */
    public void radar(View view){
        Intent intent=new Intent(this, RadarActivity.class);
        startActivity(intent);
    }

    /**
     * 流式布局
     * @param view
     */
    public void flow_layouy(View view) {
        Intent intent=new Intent(this, FlowLayoutDemoActivity.class);
        startActivity(intent);
    }
}
