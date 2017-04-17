package com.zyn.catchcrazycat.appwidget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.zyn.catchcrazycat.R;
import com.zyn.catchcrazycat.activity.MainActivity;
import com.zyn.catchcrazycat.util.DensityUtil;

/**
 * 自定义游戏失败的dialog
 * CreateDate: 2017/4/17 11:01
 * Author: Created by ZengYinan
 * Email: 498338021@qq.com
 */

public class FailGameDialog extends AppCompatDialogFragment implements DialogInterface.OnKeyListener{

    private View mView;
    private Button btn_alert_diff;
    private Button btn_once_again;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mView = inflater.inflate(R.layout.dialog_game_fail,  container);
        initView();
        initListener();
        return mView;
    }

    private void initListener() {
        //修改难度
        btn_alert_diff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DifferentChangeDialog differentChangeDialog = new DifferentChangeDialog();
                differentChangeDialog.show(getFragmentManager(), "differentChangeDialog");
            }
        });

        btn_once_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                Playground.initStepCount();
                ((MainActivity)getActivity()).startNewGame();
            }
        });

        getDialog().setOnKeyListener(this);
    }

    private void initView() {
        btn_alert_diff = (Button) mView.findViewById(R.id.btn_alert_diff);
        btn_once_again = (Button) mView.findViewById(R.id.btn_once_again);
    }

    @Override
    public void onResume() {
        super.onResume();
        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        getDialog().getWindow().setLayout(point.x - DensityUtil.dip2px(getContext(), 16) * 2, point.y - DensityUtil.dip2px(getContext(), 16) * 2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        //不响应返回按钮点击
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return false;
    }
}
