package com.zyn.catchcrazycat.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zyn.catchcrazycat.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.mContext = this;
		addActivityInList();
	}
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivityInList();
    }
    
    /**
     * 将当前Activity加入到集合中
     */
    private void addActivityInList() {
    	BaseApplication.getInstance().addActivity(this);
    }

    /**
     * 将当前Activity从集合中移除
     */
    private void removeActivityInList(){
    	BaseApplication.getInstance().removeActivity(this);
    }

    public void jump(Activity activity, Class to_class, boolean isClose) {
        Intent intent = new Intent(activity, to_class);
        startActivity(intent);
        if(isClose == true){
            activity.finish();//关闭activity
        }
        overridePendingTransition(R.anim.enter, R.anim.out);//动画
    }

    public void jump(Activity activity, Class to_class, String key, Bundle bundle, boolean isClose) {
        Intent intent = new Intent(activity, to_class);
        intent.putExtra(key, bundle);
        startActivity(intent);
        if(isClose == true){
            activity.finish();//关闭activity
        }
        overridePendingTransition(R.anim.enter, R.anim.out);//动画
    }
}
