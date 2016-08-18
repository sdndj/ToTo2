package com.example.leedongjin.toto2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Game_Story1_Activity extends AppCompatActivity implements View.OnClickListener {


    TextView textView, select_text, result_text;
    double st = 10000; // 돈
    Button downbtn; // 초기화 버튼
    Button bt1, bt2; // 홀, 짝 버튼
    Button bt1000, bt5000, bt10000, bt_all;
    String str[] = {"홀", "짝"};
    int num = 3, money1 = 0; //홀짝 선택 여부 변수, 배팅금액 변수

    private android.os.Handler mHandler;
    private ProgressDialog mProgressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        load();

        textView = (TextView) findViewById(R.id.money);
        textView.setText(String.format("%.0f",st));
        select_text = (TextView) findViewById(R.id.select);
        result_text = (TextView) findViewById(R.id.result);
        //초기화 버튼
        downbtn = (Button) findViewById(R.id.down_button);
        downbtn.setOnClickListener(this);
        //홀,짝 선택버튼
        bt1 = (Button) findViewById(R.id.button);
        bt1.setOnClickListener(this);
        bt2 = (Button) findViewById(R.id.button2);
        bt2.setOnClickListener(this);
        //배팅 선택버튼
        bt1000 = (Button) findViewById(R.id.button_1000);
        bt1000.setOnClickListener(this);
        bt5000 = (Button) findViewById(R.id.button_5000);
        bt5000.setOnClickListener(this);
        bt10000 = (Button) findViewById(R.id.button_10000);
        bt10000.setOnClickListener(this);
        //배팅 올인버튼
        bt_all = (Button) findViewById(R.id.all_btn);
        bt_all.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.down_button: //초기화 버튼
                Toast.makeText(Game_Story1_Activity.this, "현재 이 기능은 이용하실 수 없습니다.!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button: //홀 버튼
                num = 0;
                select_text.setText(str[num]);
                break;
            case R.id.button2: //짝 버튼
                num = 1;
                select_text.setText(str[num]);
                break;
            case R.id.button_1000: //배팅 천원

                money1 = 1000;
                startbatting();

                break;
            case R.id.button_5000: //배팅 오천원

                money1 = 5000;
                startbatting();

                break;
            case R.id.button_10000: //배팅 만원

                money1 = 10000;
                startbatting();

                break;
            case R.id.all_btn: //올인
                Toast.makeText(Game_Story1_Activity.this, "현재 이 기능은 이용하실 수 없습니다.!!", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void startbatting() { //홀짝 선택 후 결과 나타냄
        if (num == 3) {
            Toast.makeText(getApplicationContext(), "홀, 짝을 선택하세요.", Toast.LENGTH_SHORT).show();
        } else {
            if (st >= money1) {
                mHandler = new android.os.Handler();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog = ProgressDialog.show(Game_Story1_Activity.this, "", "3초뒤에 결과가 나옵니다.", true);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                        mProgressDialog.dismiss();
                                        //////////////////////////////////
                                        randomSelect();
                                        ///////////////////////////////////
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 3000); // 딜레이 3초
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "돈이 부족합니다. !!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void randomSelect() {
//        int i = random.nextInt(2);
        result_text.setText(str[num]);
        st -= money1; //도전비용 지출
        if (str[num].equals(str[num])) {
            double b = 0;
            b = money1 * 1.9;// 홀짝 비교 후 금액의 1.9배 지급
            st = st + b;
            Toast.makeText(getApplicationContext(), "축하합니다! 당첨 되셨습니다.", Toast.LENGTH_SHORT).show();
            increase();
        } else {
            Toast.makeText(getApplicationContext(), "아~ 아쉽네요 한번 더하면 될지도?", Toast.LENGTH_SHORT).show();
            increase();
        }
    }

    private void increase() { //돈을 저장해주는 메소드
        sharedPreferences = getSharedPreferences("money", 0);
        editor = sharedPreferences.edit();
        editor.putInt("inputmoney", (int) st);
        editor.commit();
        textView.setText(String.format("%.0f",st));
        nextStory();
    }

    private void load() { //저장된 돈을 불러오는 메소드
        sharedPreferences = getSharedPreferences("money", 0);
        st = sharedPreferences.getInt("inputmoney", (int) st);
        nextStory();
    }

    private void nextStory() {
        if(st>=50000){ //돈이 5만원 이상이면 다음 스토리 진행
            Intent i = new Intent(Game_Story1_Activity.this, Story2Activity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onBackPressed() { // 취소버튼 눌렀을 때 프로필을 불러오고 게임은 종료
        super.onBackPressed();
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
        finish();
    }
}
