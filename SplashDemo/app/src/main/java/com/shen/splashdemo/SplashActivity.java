package com.shen.splashdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    //延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 3000;
    private static final String SHARE_APP_TAG = "shen.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences(SHARE_APP_TAG, 0);
        Boolean user_first = settings.getBoolean("FIRST", true);
        if (true == user_first)
        {
            settings.edit().putBoolean("FIRST", false).commit();
            Toast.makeText(SplashActivity.this, "第一次", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(SplashActivity.this, "不是第一次", Toast.LENGTH_LONG).show();
        }
        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goMainActivity();
            }
        }, SPLASH_DELAY_MILLIS);
    }

    private void goMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }
}
