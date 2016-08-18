package com.example.leedongjin.toto2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by LeeDongJin on 2016-08-13.
 */
public class ProfileActivity extends Activity implements View.OnClickListener {

    Button replay, start, option;
    ImageView profile;
    int img_number=0;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        replay = (Button) findViewById(R.id.story_replay_btn);
        start = (Button) findViewById(R.id.game_start_btn);
        option = (Button) findViewById(R.id.option_btn);
        profile = (ImageView) findViewById(R.id.profile_img);
        replay.setOnClickListener(this);
        start.setOnClickListener(this);
        option.setOnClickListener(this);
        load();
        ImageChange(img_number);
    }

    private void load() {
        sharedPreferences = getSharedPreferences("image", 0);
        img_number = sharedPreferences.getInt("image_key", img_number);
    }

    private void ImageChange(int a) {
        switch (a){
            case 0:
                profile.setBackgroundResource(R.mipmap.character_happy);
                break;
            case 1:
                profile.setBackgroundResource(R.mipmap.character_unhappy);
                break;
            case 2:
                profile.setBackgroundResource(R.mipmap.character_angry);
                break;
            case 3:
                profile.setBackgroundResource(R.mipmap.gameover);
                break;
        }
    }

    @Override //뒤로가기 키 눌러서 팝업으로 종료하는 부분
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(this.getString(R.string.exit)) // 제목부분 텍스트
                    .setMessage(this.getString(R.string.exit2)) // 내용부분 텍스트
                    .setPositiveButton(this.getString(android.R.string.yes), //승인버튼을 눌렀을때
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    moveTaskToBack(true);
                                    finish(); //종료
                                }
                            }
                    ).setNegativeButton(this.getString(android.R.string.no), null).show(); //취소버튼을 눌렀을때
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.story_replay_btn: //스토리 다시보기
                switch (img_number){ //각 프로필 사진마다 게임 규칙과 스토리가 바뀌기 때문에 그에 맞게 버튼을 클릭 할 수 있어야함.
                    case 0: //스토리 1
                        startActivity(new Intent(this, Story1Activity.class));
                        finish();
                        break;
                    case 1: //스토리 2
                        startActivity(new Intent(this, Story2Activity.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(this, Story3Activity.class));
                        finish();
                        break;
                    case 3:
                        startActivity(new Intent(this, Story4Activity.class));
                        finish();
                        break;
                }
                break;
            case R.id.game_start_btn:
                switch (img_number){
                    case 0: //스토리 1
                        startActivity(new Intent(this, Game_Story1_Activity.class));
                        finish();
                        break;
                    case 1: //스토리 2
                        startActivity(new Intent(this, Game_Story2_Activity.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(this, Game_Story3_Activity.class));
                        finish();
                        break;
                    case 3:
                        DialogView();
                        break;
                }
                break;
            case R.id.option_btn:
                Dialog_option();
                break;
        }
    }

    //게임이 끝났다는걸 알리는 다이얼로그
    private void DialogView() {
        AlertDialog.Builder ab = new AlertDialog.Builder(ProfileActivity.this);
        ab.setMessage("Game Over");
        ab.setPositiveButton("확인",null);
        ab.show();
    }
    //옵션 다이얼로그
    private void Dialog_option(){
        AlertDialog.Builder ab = new AlertDialog.Builder(ProfileActivity.this);
        ab.setMessage("아직 준비중이에요~");
        ab.setPositiveButton("확인",null);
        ab.show();
    }
}