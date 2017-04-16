package com.zyn.catchcrazycat.activity;

import android.os.Bundle;
import android.os.SystemClock;

import com.zyn.catchcrazycat.R;
import com.zyn.catchcrazycat.base.BaseActivity;
import com.zyn.catchcrazycat.base.BaseApplication;

/**
 * Desc:
 * CreateDate: 2017/4/16 16:45
 * Author: Created by ZengYinan
 * Email: 498338021@qq.com
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        BaseApplication.getInstance().execRunnable(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                jump(SplashActivity.this, StartGameActivity.class, true);
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
    }
}
