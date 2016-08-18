package com.example.leedongjin.toto2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

/**
 * Created by LeeDongJin on 2016-08-12.
 */
public class Main extends Activity implements View.OnClickListener {

    int a = 0;

    MediaPlayer mediaPlayer;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button start, free;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        startActivity(new Intent(this, Splash.class)); //스플래시 화면
        start = (Button) findViewById(R.id.story_start_btn);
        free = (Button) findViewById(R.id.free_play_btn);
        free.setOnClickListener(this);
        start.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.main_music); //배경음악
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.story_start_btn: //스토리 시작
                moveAni(start);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() { //a가 0이면 스토리 1부터 시작하고 a가 1이면 스토리1을 건너뛰고 시작한다. (처음 스토리 확인여부)
                        if (a == 0) {
                            startActivity(new Intent(Main.this, Story1Activity.class));
                            a = 1;
                            save_number();
                            finish();
                        } else if (a == 1) {
                            startActivity(new Intent(Main.this, ProfileActivity.class));
                            finish();
                        }
                    }
                }, 1000);
                break;
            case R.id.free_play_btn: //프리플레이 시작
                moveAni(free);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Main.this, Free_play_Activity.class));
                        finish();
                    }
                }, 1000);
                break;
        }
    }

    private void moveAni(View view) {
        TranslateAnimation ani = new TranslateAnimation( //버튼 이동 애니메이션
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -0.5f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        ani.setDuration(1000); //진행시간
        ani.setFillAfter(false);
        view.startAnimation(ani);
        view.animate().alpha(0); //투명도
        view.animate().setDuration(1000);
    }

    private void save_number() {
        sharedPreferences = getSharedPreferences("number", 0);
        editor = sharedPreferences.edit();
        editor.putInt("a", a);
        editor.commit();
    }

    private void load() {
        sharedPreferences = getSharedPreferences("number", 0);
        a = sharedPreferences.getInt("a", a);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
