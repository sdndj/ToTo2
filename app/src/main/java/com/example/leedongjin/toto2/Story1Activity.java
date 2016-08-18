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
public class Story1Activity extends Activity implements View.OnClickListener {

    TextView story1,story2,story3,story4; //스토리 텍스트뷰
    RelativeLayout relativeLayout;

    Handler mHandler;

    int img_number = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story1);
        story1 = (TextView)findViewById(R.id.story1_item1);
        story2 = (TextView)findViewById(R.id.story1_item2);
        story3 = (TextView)findViewById(R.id.story1_item3);
        story4 = (TextView)findViewById(R.id.story1_item4);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout1);
        story1.setOnClickListener(this);
        story2.setOnClickListener(this);
        story3.setOnClickListener(this);
        story4.setOnClickListener(this);

        saveImagenumber(); //프로필 이미지 변환을 위한 변수 저장
    }

    private void saveImagenumber() {
        sharedPreferences = getSharedPreferences("image",0);
        editor = sharedPreferences.edit();
        editor.putInt("image_key",img_number);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.story1_item1:
                story1.animate().setDuration(1500); //스토리 진행시간 1.5초
                story1.animate().alpha(0); //투명도 완전투명으로 바꿈
                mHandler = new Handler(); //애니메이션 진행시간 1.5초와 텍스트 뷰를 터치하여 사라지는 효과를 위해서 1.5초 뒤에 삭제되게 해놓았다.
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.removeView(story1);
                    }
                }, 1500);
                break;
            case R.id.story1_item2:
                story2.animate().setDuration(1500);
                story2.animate().alpha(0);
                mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.removeView(story2);
                    }
                }, 1500);
                break;
            case R.id.story1_item3:
                story3.animate().setDuration(1500);
                story3.animate().alpha(0);
                mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.removeView(story3);
                    }
                }, 1500);
                break;
            case R.id.story1_item4:
                story4.animate().setDuration(1500);
                story4.animate().alpha(0);
                mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() { //마지막 스토리가 끝나면 프로필 화면으로 넘어간다.
                        Intent i = new Intent(Story1Activity.this, ProfileActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 1500);
                break;
        }
    }

    //취소버튼 비활성
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
