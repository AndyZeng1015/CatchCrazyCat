package com.zyn.catchcrazycat.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zyn.catchcrazycat.R;
import com.zyn.catchcrazycat.base.BaseActivity;
import com.zyn.catchcrazycat.base.BaseApplication;
import com.zyn.catchcrazycat.util.ToastUtil;

/**
 * Desc:
 * CreateDate: 2017/4/16 16:42
 * Author: Created by ZengYinan
 * Email: 498338021@qq.com
 */

public class StartGameActivity extends BaseActivity {

    private Button btn_start_game;
    private Button btn_diff_select;
//    private Button btn_record_rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initListener() {
        //开始游戏
        btn_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(StartGameActivity.this, MainActivity.class, false);
            }
        });

        //难度选择
        btn_diff_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //记录排名
//        btn_record_rank.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void initView() {
        setContentView(R.layout.activity_start_game);
        btn_start_game = (Button) findViewById(R.id.btn_start_game);
        btn_diff_select = (Button) findViewById(R.id.btn_diff_select);
//        btn_record_rank = (Button) findViewById(R.id.btn_record_rank);
    }

    private static long lastPressTime = 0;

    @Override
    public void onBackPressed() {
        long pressTime = System.currentTimeMillis();
        if(pressTime - lastPressTime > 2000){
            lastPressTime = pressTime;
            ToastUtil.showToast(mContext, "再按一次退出！");
        }else{
            BaseApplication.getInstance().exit();
            super.onBackPressed();
        }
    }
}
