package com.example.leedongjin.toto2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by LeeDongJin on 2016-08-12.
 */
public class Story2Activity extends Activity implements View.OnClickListener {

    TextView story1, story2;
    RelativeLayout relativeLayout;

    Handler mHandler;

    int img_number = 1;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story2);
        story1 = (TextView) findViewById(R.id.story2_item1);
        story2 = (TextView) findViewById(R.id.story2_item2);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout2);

        story1.setOnClickListener(this);
        story2.setOnClickListener(this);

        saveImagenumber();
    }

    private void saveImagenumber() {
        sharedPreferences = getSharedPreferences("image", 0);
        editor = sharedPreferences.edit();
        editor.putInt("image_key", img_number);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.story2_item1:
                story1.animate().setDuration(1500);
                story1.animate().alpha(0);
                mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.removeView(story1);
                    }
                }, 1500);
                break;
            case R.id.story2_item2:
                story2.animate().setDuration(1500);
                story2.animate().alpha(0);
                mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.removeView(story2);
                        Intent intent = new Intent(Story2Activity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1500);
                break;
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
