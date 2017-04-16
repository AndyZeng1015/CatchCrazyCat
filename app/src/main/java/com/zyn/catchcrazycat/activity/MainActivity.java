package com.zyn.catchcrazycat.activity;

import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zyn.catchcrazycat.Config.Contast;
import com.zyn.catchcrazycat.R;
import com.zyn.catchcrazycat.appwidget.Playground;
import com.zyn.catchcrazycat.base.BaseActivity;
import com.zyn.catchcrazycat.bean.Dot;
import com.zyn.catchcrazycat.util.DensityUtil;

public class MainActivity extends BaseActivity {

    private RelativeLayout rl_content;

    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        initView();
        initData();
    }

    private void initData() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        int screenWidth = point.x;//屏幕的宽度

        int ll_Length = screenWidth - DensityUtil.dip2px(mContext, 16) * 2;//LinearLayout的宽度和高度

        ViewGroup.LayoutParams layoutParams = rl_content.getLayoutParams();
        layoutParams.width = ll_Length;
        layoutParams.height = ll_Length;//此处高度应该比宽度小，但是由于整型的原因，如果减小会小很多，这次忽略
        rl_content.setLayoutParams(layoutParams);

        Playground playground = new Playground(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rl_content.addView(playground, params);

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        rl_content = (RelativeLayout) findViewById(R.id.rl_content);
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
        catBg.setBackgroundResource(R.drawable.cat_bg);
        AnimationDrawable drawable = (AnimationDrawable) catBg.getBackground();
        drawable.start();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,170);
        if(y % 2 == 0){
            layoutParams.leftMargin = (int) (x * Playground.WIDTH + Playground.WIDTH / 2);
        }else{
            layoutParams.leftMargin = (int) (x * Playground.WIDTH);
        }
        layoutParams.topMargin = (int)((y * Playground.WIDTH) - 170 + Playground.WIDTH / 2);
        rl_content.addView(catBg, layoutParams);
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
                layoutParams.leftMargin = (int) (x * Playground.WIDTH + Playground.WIDTH / 2);
            }else{
                layoutParams.leftMargin = (int) (x * Playground.WIDTH);
            }
            layoutParams.topMargin = (int)((y * Playground.WIDTH) - 170 + Playground.WIDTH / 2);
            catBg.setLayoutParams(layoutParams);
        }
    }

    //修改猫的动图
    public void changeCatBg(){

    }
}
