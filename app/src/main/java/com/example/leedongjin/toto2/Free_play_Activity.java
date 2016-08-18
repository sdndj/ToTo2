package com.example.leedongjin.toto2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import java.util.Random;

public class Free_play_Activity extends AppCompatActivity implements View.OnClickListener {

    KakaoLink kakaoLink;
    KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;

    TextView textView, select_text, result_text;
    double st = 500000; // 돈
    Button downbtn, sharedbutton; // 초기화 버튼
    Button bt1, bt2; // 홀, 짝 버튼
    Button bt10000, bt50000, bt100000, bt_all;
    String str[] = {"홀", "짝"};
    Random random = new Random();
    int num = 3, money1 = 0; //홀짝 선택 여부 변수, 배팅금액 변수

    private android.os.Handler mHandler;
    private ProgressDialog mProgressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_free);
        load();

        try {
            kakaoLink = KakaoLink.getKakaoLink(getApplicationContext());
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }

        textView = (TextView) findViewById(R.id.money);
        textView.setText(String.format("%.0f", st));
        select_text = (TextView) findViewById(R.id.select);
        result_text = (TextView) findViewById(R.id.result);
        //초기화 버튼
        downbtn = (Button) findViewById(R.id.free_clear_btn);
        downbtn.setOnClickListener(this);
        //홀,짝 선택버튼
        bt1 = (Button) findViewById(R.id.free_hole);
        bt1.setOnClickListener(this);
        bt2 = (Button) findViewById(R.id.free_jjack);
        bt2.setOnClickListener(this);
        //배팅 선택버튼
        bt10000 = (Button) findViewById(R.id.free_button_10000);
        bt10000.setOnClickListener(this);
        bt50000 = (Button) findViewById(R.id.free_button_50000);
        bt50000.setOnClickListener(this);
        bt100000 = (Button) findViewById(R.id.free_button_100000);
        bt100000.setOnClickListener(this);
        bt_all = (Button) findViewById(R.id.free_all_btn);
        bt_all.setOnClickListener(this);
        //공유 버튼
        sharedbutton = (Button) findViewById(R.id.shared_btn);
        sharedbutton.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shared_btn: //공유 버튼
                kakaoShared();
                break;
            case R.id.free_clear_btn: //초기화 버튼
                mHandler = new android.os.Handler();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog = ProgressDialog.show(Free_play_Activity.this, "", "돈빌리는 중... (소요시간 10초)", true);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                        mProgressDialog.dismiss();
                                        //////////////////////////////////
                                        st = 200000;
                                        textView.setText(String.valueOf(st));
                                        increase();
                                        ///////////////////////////////////
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 10000);
                    }
                });
                break;
            case R.id.free_hole: //홀 버튼
                num = 0;
                select_text.setText(str[num]);
                break;
            case R.id.free_jjack: //짝 버튼
                num = 1;
                select_text.setText(str[num]);
                break;
            case R.id.free_button_10000: //배팅 만원

                money1 = 10000;
                startbatting();

                break;
            case R.id.free_button_50000: //배팅 오만원

                money1 = 50000;
                startbatting();

                break;
            case R.id.free_button_100000: //배팅 10만원

                money1 = 100000;
                startbatting();

                break;
            case R.id.free_all_btn: //올인
                if (num == 3) {
                    Toast.makeText(getApplicationContext(), "홀, 짝을 선택하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (st < 300000) {
                        Toast.makeText(getApplicationContext(), "모두 투자는 최소 30만원 부터 가능합니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        mHandler = new android.os.Handler();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog = ProgressDialog.show(Free_play_Activity.this, "", "3초뒤에 결과가 나옵니다.", true);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                                mProgressDialog.dismiss();
                                                //////////////////////////////////
                                                allSelect();
                                                ///////////////////////////////////
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 3000);
                            }
                        });
                    }
                }
                break;

            default:
                break;
        }
    }

    private void kakaoShared() { //카카오톡으로 공유하는 메소드
        try {
            String ment = "\n(광고)  <홀/짝 게임>\n\n즐겁고 재미있는 신개념\n(행복)순수 운빨 100% 게임!(행복)\n=========================\n현재 홀짝 게임에서 " + String.format("%.0f", st) + "원 보유중 입니다.\n(돈)운빨 끝판왕에 도전해보세요!!\n=========================\n당신은 더 많은 금액을 얻을 수 있습니다!!";
            kakaoTalkLinkMessageBuilder.addImage("http://blogfiles.naver.net/20160630_137/a753431_1467284333546wsb91_PNG/splash.png", 150, 200);
            kakaoTalkLinkMessageBuilder.addText(ment);
            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this);
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
        } catch (KakaoParameterException e) {
            e.printStackTrace();
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
                        mProgressDialog = ProgressDialog.show(Free_play_Activity.this, "", "3초뒤에 결과가 나옵니다.", true);
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
        int i = random.nextInt(2);
        result_text.setText(str[i]);
        st -= money1; //도전비용 지출
        if (str[num].equals(str[i])) {
            double b = 0;
            b = money1 * 1.9;// 홀짝 비교 후 금액의 1.9배 지급
            st = st + b;
            Toast.makeText(getApplicationContext(), "축하합니다! 당첨 되셨습니다.", Toast.LENGTH_LONG).show();
            increase();
        } else {
            Toast.makeText(getApplicationContext(), "아~ 아쉽네요 한번 더하면 될지도?", Toast.LENGTH_LONG).show();
            increase();
        }
    }

    private void allSelect() { //올인용 홀짝 비교
        int i = random.nextInt(2);
        result_text.setText(str[i]);
        double a = st;
        st -= st;
        if (str[num].equals(str[i])) {
            double b = 0;
            b = a * 1.9;
            st = st + b;
            Toast.makeText(getApplicationContext(), "축하합니다! 당첨 되셨습니다.", Toast.LENGTH_LONG).show();
            increase();
        } else {
            Toast.makeText(getApplicationContext(), "아~ 아쉽네요 한번 더하면 될지도?", Toast.LENGTH_LONG).show();
            increase();
        }
    }

    private void increase() { //돈을 저장해주는 메소드
        sharedPreferences = getSharedPreferences("money_free", 0);
        editor = sharedPreferences.edit();
        editor.putInt("inputmoney_free", (int) st);
        editor.commit();
        textView.setText(String.format("%.0f", st));
    }

    private void load() { //저장된 돈을 불러오는 메소드
        sharedPreferences = getSharedPreferences("money_free", 0);
        st = sharedPreferences.getInt("inputmoney_free", (int) st);
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
}
