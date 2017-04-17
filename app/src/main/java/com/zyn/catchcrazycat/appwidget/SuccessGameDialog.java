package com.zyn.catchcrazycat.appwidget;

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
import android.widget.TextView;

import com.zyn.catchcrazycat.R;
import com.zyn.catchcrazycat.activity.MainActivity;
import com.zyn.catchcrazycat.util.DensityUtil;

/**
 * 自定义游戏失败的dialog
 * CreateDate: 2017/4/17 11:01
 * Author: Created by ZengYinan
 * Email: 498338021@qq.com
 */

public class SuccessGameDialog extends AppCompatDialogFragment implements DialogInterface.OnKeyListener {

    private View mView;
    private TextView tv_with_step;
//    private TextView tv_with_ranking;
//    private TextView tv_with_per;
    private TextView tv_with_title;
    private Button btn_alert_diff;
    private Button btn_once_again;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mView = inflater.inflate(R.layout.dialog_game_success,  container);
        initView();
        initData();
        initListener();
        return mView;
    }

    private void initListener() {
        //修改难度
        btn_alert_diff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //重新开始
        btn_once_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                ((MainActivity)getActivity()).startNewGame();
            }
        });

        getDialog().setOnKeyListener(this);
    }

    private void initData() {
        //在这里加载数据
        int stepCount = Playground.getStepCount();
        tv_with_step.setText(String.format(getResources().getString(R.string.with_step), stepCount+""));
        String title = "";
        if(stepCount < 3){
            title = "精神病博士";
        }else if(stepCount < 6){
            title = "神经大神";
        }else if(stepCount < 10){
            title = "精神病院长";
        }else if(stepCount < 13){
            title = "院长派来的救兵";
        }else if(stepCount < 17){
            title = "扫地僧";
        }else{
            title = "隔壁王伯伯";
        }
        tv_with_title.setText(String.format(getResources().getString(R.string.with_title), title));
    }

    private void initView() {
        tv_with_step = (TextView) mView.findViewById(R.id.tv_with_step);
//        tv_with_ranking = (TextView) mView.findViewById(R.id.tv_with_ranking);
//        tv_with_per = (TextView) mView.findViewById(R.id.tv_with_per);
        tv_with_title = (TextView) mView.findViewById(R.id.tv_with_title);
        btn_alert_diff = (Button) mView.findViewById(R.id.btn_alert_diff);
        btn_once_again = (Button) mView.findViewById(R.id.btn_once_again);
    }

    @Override
    public void onResume() {
        super.onResume();
        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        getDialog().getWindow().setLayout(point.x - DensityUtil.dip2px(getContext(), 16) * 2, point.y  - DensityUtil.dip2px(getContext(), 16) * 2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return false;
    }
}
