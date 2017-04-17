package com.zyn.catchcrazycat.appwidget;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zyn.catchcrazycat.R;
import com.zyn.catchcrazycat.activity.MainActivity;
import com.zyn.catchcrazycat.activity.StartGameActivity;
import com.zyn.catchcrazycat.util.DensityUtil;
import com.zyn.catchcrazycat.util.SharedPreferencesUtils;

/**
 * Desc:
 * CreateDate: 2017/4/17 15:33
 * Author: Created by ZengYinan
 * Email: 498338021@qq.com
 */

public class DifferentChangeDialog extends AppCompatDialogFragment {
    private View mView;
    private Button btn_happy_mode;
    private Button btn_normal_mode;
    private Button btn_brain_mode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_diff_change, container);
        initView();
        initListener();
        return mView;
    }

    private void initListener() {
        btn_brain_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.saveString(getContext(), "diff_type", "烧脑模式");
                if(getActivity() instanceof MainActivity){
                    //游戏界面
                    Playground.initStepCount();
                    ((MainActivity)getActivity()).startNewGame();

                    if(getFragmentManager().findFragmentByTag("successGameDialog") != null){
                        ((AppCompatDialogFragment)getFragmentManager().findFragmentByTag("successGameDialog")).getDialog().dismiss();
                    }

                    if(getFragmentManager().findFragmentByTag("failGameDialog") != null){
                        ((AppCompatDialogFragment)getFragmentManager().findFragmentByTag("failGameDialog")).getDialog().dismiss();
                    }
                }else if(getActivity() instanceof StartGameActivity){
                    //开始界面
                    ((StartGameActivity)getActivity()).jump(getActivity(), MainActivity.class, false);
                }
                getDialog().dismiss();
            }
        });
        btn_normal_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.saveString(getContext(), "diff_type", "普通模式");
                if(getActivity() instanceof MainActivity){
                    //游戏界面
                    Playground.initStepCount();
                    ((MainActivity)getActivity()).startNewGame();

                    if(getFragmentManager().findFragmentByTag("successGameDialog") != null){
                        ((AppCompatDialogFragment)getFragmentManager().findFragmentByTag("successGameDialog")).getDialog().dismiss();
                    }

                    if(getFragmentManager().findFragmentByTag("failGameDialog") != null){
                        ((AppCompatDialogFragment)getFragmentManager().findFragmentByTag("failGameDialog")).getDialog().dismiss();
                    }
                }else if(getActivity() instanceof StartGameActivity){
                    //开始界面
                    ((StartGameActivity)getActivity()).jump(getActivity(), MainActivity.class, false);
                }
                getDialog().dismiss();
            }
        });
        btn_happy_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.saveString(getContext(), "diff_type", "欢乐模式");
                if(getActivity() instanceof MainActivity){
                    //游戏界面
                    Playground.initStepCount();
                    ((MainActivity)getActivity()).startNewGame();

                    if(getFragmentManager().findFragmentByTag("successGameDialog") != null){
                        ((AppCompatDialogFragment)getFragmentManager().findFragmentByTag("successGameDialog")).getDialog().dismiss();
                    }

                    if(getFragmentManager().findFragmentByTag("failGameDialog") != null){
                        ((AppCompatDialogFragment)getFragmentManager().findFragmentByTag("failGameDialog")).getDialog().dismiss();
                    }
                }else if(getActivity() instanceof StartGameActivity){
                    //开始界面
                    ((StartGameActivity)getActivity()).jump(getActivity(), MainActivity.class, false);
                }
                getDialog().dismiss();
            }
        });
    }

    private void initView() {
        btn_happy_mode = (Button) mView.findViewById(R.id.btn_happy_mode);
        btn_normal_mode = (Button) mView.findViewById(R.id.btn_normal_mode);
        btn_brain_mode = (Button) mView.findViewById(R.id.btn_brain_mode);
    }

    @Override
    public void onResume() {
        super.onResume();
        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        getDialog().getWindow().setLayout(point.x - DensityUtil.dip2px(getContext(), 16) * 2, point.y - DensityUtil.dip2px(getContext(), 16) * 2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
