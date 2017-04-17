package com.zyn.catchcrazycat.activity;

import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyn.catchcrazycat.Config.Contast;
import com.zyn.catchcrazycat.R;
import com.zyn.catchcrazycat.appwidget.Playground;
import com.zyn.catchcrazycat.base.BaseActivity;
import com.zyn.catchcrazycat.bean.Dot;
import com.zyn.catchcrazycat.util.DensityUtil;
import com.zyn.catchcrazycat.util.SharedPreferencesUtils;

public class MainActivity extends BaseActivity {

    private RelativeLayout rl_content;
    private RelativeLayout rl_main_content;
    private TextView tv_diff_change;

    public static MainActivity instance;

    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    private Playground mPlayground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        tv_diff_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initData() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        mScreenWidth = point.x;//屏幕的宽度
        mScreenHeight = point.y;//屏幕的高度

        int rl_Length = mScreenWidth - DensityUtil.dip2px(mContext, 16) * 2;//LinearLayout的宽度和高度

        ViewGroup.LayoutParams layoutParams = rl_content.getLayoutParams();
        layoutParams.width = rl_Length;
        layoutParams.height = rl_Length;//此处高度应该比宽度小，但是由于整型的原因，如果减小会小很多，这次忽略
        rl_content.setLayoutParams(layoutParams);

        mPlayground = new Playground(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rl_content.addView(mPlayground, params);

        String title = SharedPreferencesUtils.getString(mContext, "diff_type", "欢乐模式");
        tv_diff_change.setText(title);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        rl_content = (RelativeLayout) findViewById(R.id.rl_content);
        rl_main_content = (RelativeLayout) findViewById(R.id.rl_main_content);
        tv_diff_change = (TextView) findViewById(R.id.tv_diff_change);
    }

    public static MainActivity getInstance(){
        return instance;
    }

    private static ImageView catBg;

    //添加猫的动图
    public void addCatBg(Dot cat){
        int x = cat.getX();
        int y = cat.getY();
        catBg = new ImageView(mContext);
        catBg.setScaleType(ImageView.ScaleType.FIT_CENTER);
        catBg.setBackgroundResource(R.drawable.cat_bg);
        AnimationDrawable drawable = (AnimationDrawable) catBg.getBackground();
        drawable.start();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 150);
        if(y % 2 == 0){
            layoutParams.leftMargin = (int) (x * Playground.WIDTH + Playground.WIDTH / 2 + ((mScreenWidth - rl_content.getLayoutParams().width)/2));
        }else{
            layoutParams.leftMargin = (int) (x * Playground.WIDTH + ((mScreenWidth - rl_content.getLayoutParams().width)/2));
        }
        layoutParams.topMargin = (int)((y * Playground.WIDTH) - 150 + Playground.WIDTH / 2) + (mScreenHeight - rl_content.getLayoutParams().height - DensityUtil.dip2px(mContext, 60));
        rl_main_content.addView(catBg, layoutParams);
    }

    //移动猫的动图
    public void moveCatBg(Dot cat){
        if(catBg == null){
            addCatBg(cat);
        }else{
            //移动
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) catBg.getLayoutParams();
            int x = cat.getX();
            int y = cat.getY();
            if(y % 2 == 0){
                layoutParams.leftMargin = (int) (x * Playground.WIDTH + Playground.WIDTH / 2  + ((mScreenWidth - rl_content.getLayoutParams().width)/2));
            }else{
                layoutParams.leftMargin = (int) (x * Playground.WIDTH  + ((mScreenWidth - rl_content.getLayoutParams().width)/2) );
            }
            layoutParams.topMargin = (int)((y * Playground.WIDTH) - 150 + Playground.WIDTH / 2) + (mScreenHeight - rl_content.getLayoutParams().height - DensityUtil.dip2px(mContext, 60));
            catBg.setLayoutParams(layoutParams);
        }
    }

    //修改猫的动图
    public void changeCatBg(){
        if(catBg != null){
            catBg.setBackgroundResource(R.drawable.cat_crash_bg);
            AnimationDrawable drawable = (AnimationDrawable) catBg.getBackground();
            drawable.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        catBg = null;
    }

    public void startNewGame(){
        catBg.setBackgroundResource(R.drawable.cat_bg);
        AnimationDrawable drawable = (AnimationDrawable) catBg.getBackground();
        drawable.start();
        mPlayground.initGame();
        mPlayground.redraw();
    }
}
