package com.dong.customview.shader;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dong.customview.R;

/**
 * Created dong on 2017/11/3.
 * @author dc
 */

public class RadarActivity extends AppCompatActivity {

    private RadarView mRadarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);

        mRadarView = (RadarView) findViewById(R.id.radarview);
    }
    public void start(View view){
        mRadarView.startScan();
    }

    public void stop(View view){
        mRadarView.stopScan();
    }
}
