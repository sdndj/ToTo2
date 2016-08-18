package com.example.leedongjin.toto2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by LeeDongJin on 2016-06-30.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3000);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
