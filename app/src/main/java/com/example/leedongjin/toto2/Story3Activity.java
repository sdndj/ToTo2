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
 * Created by LeeDongJin on 2016-08-15.
 */
public class Story3Activity extends Activity implements View.OnClickListener {

    TextView story1,story2;
    RelativeLayout relativeLayout;

    int img_number = 2;

    Handler handler;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story3);
        story1 = (TextView)findViewById(R.id.story3_item1);
        story2 = (TextView)findViewById(R.id.story3_item2);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout3);
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
        switch (v.getId()){
            case R.id.story3_item1:
                story1.animate().setDuration(1500); //스토리 진행시간 1.5초
                story1.animate().alpha(0); //투명도 완전투명으로 바꿈
                handler = new Handler(); //애니메이션 진행시간 1.5초와 텍스트 뷰를 터치하여 사라지는 효과를 위해서 1.5초 뒤에 삭제되게 해놓았다.
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.removeView(story1);
                    }
                }, 1500);
                break;
            case R.id.story3_item2:
                story2.animate().setDuration(1500); //스토리 진행시간 1.5초
                story2.animate().alpha(0); //투명도 완전투명으로 바꿈
                handler = new Handler(); //애니메이션 진행시간 1.5초와 텍스트 뷰를 터치하여 사라지는 효과를 위해서 1.5초 뒤에 삭제되게 해놓았다.
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.removeView(story2);
                        Intent intent = new Intent(Story3Activity.this, ProfileActivity.class);
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
