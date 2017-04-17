package com.zyn.catchcrazycat.appwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.zyn.catchcrazycat.Config.Contast;
import com.zyn.catchcrazycat.activity.MainActivity;
import com.zyn.catchcrazycat.bean.Dot;
import com.zyn.catchcrazycat.util.SharedPreferencesUtils;

import java.util.Random;
import java.util.Vector;

import static com.zyn.catchcrazycat.Config.Contast.COL;
import static com.zyn.catchcrazycat.Config.Contast.GAME_CONDUCT;
import static com.zyn.catchcrazycat.Config.Contast.GAME_FAIL;
import static com.zyn.catchcrazycat.Config.Contast.GAME_SUCCESS;
import static com.zyn.catchcrazycat.Config.Contast.LEFT;
import static com.zyn.catchcrazycat.Config.Contast.LEFT_DOWN;
import static com.zyn.catchcrazycat.Config.Contast.LEFT_UP;
import static com.zyn.catchcrazycat.Config.Contast.RIGHT;
import static com.zyn.catchcrazycat.Config.Contast.RIGHT_DOWN;
import static com.zyn.catchcrazycat.Config.Contast.RIGHT_UP;
import static com.zyn.catchcrazycat.Config.Contast.ROW;
import static com.zyn.catchcrazycat.Config.Contast.STATUS_IN;
import static com.zyn.catchcrazycat.Config.Contast.STATUS_OFF;
import static com.zyn.catchcrazycat.Config.Contast.STATUS_ON;

/**
 * Desc: 游戏界面
 * CreateDate: 2017/4/15 17:28
 * Author: Created by ZengYinan
 * Email: 498338021@qq.com
 */

public class Playground extends SurfaceView implements View.OnTouchListener {

    public static float WIDTH = 40; //每个圆圈的宽度， 默认为40
    private Dot matrix[][];//用于存放屏幕上每个圆圈
    private Dot cat;//猫

    private Random mRandom;
    private Context mContext;

    private static int stepCount = 1;//用于记录使用了多少步

    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            WIDTH = (float) (width / (COL + 0.5));
            redraw();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    public Playground(Context context) {
        super(context);
        mContext = context;
        mRandom = new Random();
        matrix = new Dot[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                matrix[i][j] = new Dot(i, j);
            }
        }
        getHolder().addCallback(mCallback);
        initGame();
        setOnTouchListener(this);
    }

    //重绘
    public void redraw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.parseColor("#666666"));

        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        for (int y = 0; y < ROW; y++) {
            for (int x = 0; x < COL; x++) {
                if (matrix[x][y].getStatus() == STATUS_OFF) {
                    paint.setColor(0xFFB5B5B5);
                } else if (matrix[x][y].getStatus() == STATUS_ON) {
                    paint.setColor(0xFFFE845D);
                } else if (matrix[x][y].getStatus() == STATUS_IN) {
                    //paint.setColor(0xFFFF0000);
                    paint.setColor(0xFFB5B5B5);
                    MainActivity.getInstance().moveCatBg(matrix[x][y]);
                }

                if (y % 2 == 0) {
                    canvas.drawOval(new RectF(x * WIDTH + WIDTH / 2, y * WIDTH, (x + 1) * WIDTH + WIDTH / 2, (y + 1) * WIDTH), paint);
                } else {
                    canvas.drawOval(new RectF(x * WIDTH, y * WIDTH, (x + 1) * WIDTH, (y + 1) * WIDTH), paint);
                }

            }
        }

        getHolder().unlockCanvasAndPost(canvas);
    }

    //初始化游戏界面
    public void initGame() {
        //让所有圆圈都呈现可点击状态
        for (int x = 0; x < ROW; x++) {
            for (int y = 0; y < COL; y++) {
                matrix[x][y].setStatus(STATUS_OFF);
            }
        }
        //设置猫的起始位置在中心点
        cat = matrix[4][4];
        cat.setStatus(STATUS_IN);

        //随机设置路障点的位置
        int blockCount = 0;
        while (blockCount < 10) {
            int x = mRandom.nextInt(10);
            int y = mRandom.nextInt(10);
            if (matrix[x][y].getStatus() == STATUS_OFF) {
                matrix[x][y].setStatus(STATUS_ON);
                blockCount++;
            }
        }

    }

    //猫的移动
    private void moveCat() {
        String title = SharedPreferencesUtils.getString(mContext, "diff_type", "欢乐模式");
        if (title.equals("欢乐模式")) {//固定移动方向，无选择
            //获取到猫可以移动的方向
            Vector<Dot> dots = new Vector<Dot>();
            for (int i = 0; i < 6; i++) {
                if (getNearbyDot(cat, i).getStatus() == STATUS_OFF) {
                    dots.add(getNearbyDot(cat, i));
                }
            }
            cat.setStatus(STATUS_OFF);
            cat = dots.get(0);
            cat.setStatus(STATUS_IN);
        } else if (title.equals("普通模式")) {//最短路径算法
            //先计算每个可移动点的路径值，选择最短的走
            calculateShortestPath();
            Vector<Dot> dots = new Vector<Dot>();
            for (int i = 0; i < 6; i++) {
                if (getNearbyDot(cat, i).getStatus() == STATUS_OFF) {
                    dots.add(getNearbyDot(cat, i));
                }
            }

            int minValue = 110;//记录最小的值
            int index = 0;//记录最小值是第几个
            for (int i = 0; i < dots.size(); i++) {
                if (minValue > dots.get(i).getValue()) {
                    minValue = dots.get(i).getValue();
                    index = i;
                }
            }

            if(minValue == -110){
                //表示周围都被堵死了
                MainActivity.getInstance().changeCatBg();
            }

            cat.setStatus(STATUS_OFF);
            cat = dots.get(index);
            cat.setStatus(STATUS_IN);

        } else if (title.equals("烧脑模式")) {

        }
    }

    //计算图中每个点的值
    private void calculateShortestPath() {

        //清空之前的值
        for (int x = 0; x < ROW; x++) {
            for (int y = 0; y < COL; y++) {
                matrix[x][y].setValue(-110);
            }
        }

        //左上角
        for (int i = 0; i < Contast.ROW; i++) {
            for (int j = 0; j < Contast.COL; j++) {
                calculateOneOverl(matrix[j][i]);//计算第一行->最后一行
                calculateOneOverl(matrix[i][j]);//计算第一列->最后一列
            }
        }

        //右上角
        for (int i = 0; i < Contast.ROW; i++) {
            for (int j = 0; j < Contast.COL; j++) {
                calculateOneOverl(matrix[(Contast.COL - 1) - j][i]);//计算第一行->最后一行
                calculateOneOverl(matrix[i][j]);//计算第一列->最后一列
            }
        }

        //右下角
        for (int i = Contast.ROW - 1; i >= 0; i--) {
            for (int j = Contast.COL - 1; j >= 0 ; j--) {
                calculateOneOverl(matrix[j][i]);
                calculateOneOverl(matrix[i][j]);
            }
        }

        //左下角
        for (int i = 0; i < Contast.ROW; i++) {
            for (int j = 0; j < Contast.COL; j++) {
                calculateOneOverl(matrix[j][i]);
                calculateOneOverl(matrix[i][(Contast.COL - 1) - j]);
            }
        }
    }

    //为指定点设置值
    private void calculateOneOverl(Dot dot) {
        if (dot.getStatus() == STATUS_ON) {
            //表示为障碍物
            dot.setValue(110);
            return;
        }

        if (dot.getStatus() == STATUS_IN) {
            //表示为猫的位置
            dot.setValue(888);
            return;
        }

        if (dot.getX() * dot.getY() == 0 || dot.getX() == Contast.ROW - 1 || dot.getY() == Contast.COL - 1) {
            //表示为边界
            dot.setValue(0);
            return;
        }

        //到这里表示为中间的非猫非占有的点
        Vector<Dot> dots = new Vector<Dot>();
        for (int i = 0; i < 6; i++) {
            Dot nearByDot = getNearbyDot(dot, i);
            if (nearByDot.getStatus() == STATUS_OFF) {
                dots.add(nearByDot);
            }
        }

        if (dots.size() <= 0) {
            //表示周围没有可用的点
            dot.setValue(-110);
            return;
        } else {
            int minValue = 110;
            for (int i = 0; i < dots.size(); i++) {
                if (dots.get(i).getValue() != -110 && dots.get(i).getValue() < minValue) {
                    minValue = dots.get(i).getValue();
                }
            }
            if(minValue == 110){
                //说明该点周围都是没赋值的点，可能从外圈被围住了
                dot.setValue(-110);
                return;
            }else{
                dot.setValue(minValue + 1);
                return;
            }
        }
    }

    //判断游戏的状态
    private int getGameStatus() {
        if (cat.getX() * cat.getY() == 0 || cat.getX() == ROW - 1 || cat.getY() == COL - 1) {
            //表示猫的位置在边沿位置
            return GAME_FAIL;
        }

        for (int i = 0; i < 6; i++) {
            Dot nearByDot = getNearbyDot(cat, i);
            if (nearByDot.getStatus() == STATUS_OFF) {
                //如果有一个点是可点击的状态表示游戏未结束
                return GAME_CONDUCT;
            }
        }
        return GAME_SUCCESS;
    }

    //获取到指定点指定方向的临近点
    private Dot getNearbyDot(Dot dot, int direction) {
        Dot nearByDot = null;
        int x = dot.getX();
        int y = dot.getY();
        switch (direction) {
            case LEFT:
                if (x != 0) {
                    nearByDot = matrix[x - 1][y];
                }
                break;
            case LEFT_UP:
                if (y != 0) {
                    if (y % 2 == 0) {
                        //基数行
                        nearByDot = matrix[x][y - 1];
                    } else {
                        //偶数行
                        if (x != 0) {
                            nearByDot = matrix[x - 1][y - 1];
                        }
                    }
                }
                break;
            case RIGHT_UP:
                if (y != 0) {
                    if (y % 2 == 0) {
                        //基数行
                        if (x != ROW - 1) {
                            nearByDot = matrix[x + 1][y - 1];
                        }
                    } else {
                        //偶数行
                        nearByDot = matrix[x][y - 1];
                    }
                }
                break;
            case RIGHT:
                if (x != ROW - 1) {
                    nearByDot = matrix[x + 1][y];
                }
                break;
            case RIGHT_DOWN:
                if (y != COL - 1) {
                    if (y % 2 == 0) {
                        //基数行
                        if (x != ROW - 1) {
                            nearByDot = matrix[x + 1][y + 1];
                        }
                    } else {
                        //偶数行
                        nearByDot = matrix[x][y + 1];
                    }
                }
                break;
            case LEFT_DOWN:
                if (y != COL - 1) {
                    if (y % 2 == 0) {
                        //基数行
                        nearByDot = matrix[x][y + 1];
                    } else {
                        //偶数行
                        if (x != 0) {
                            nearByDot = matrix[x - 1][y + 1];
                        }
                    }
                }
                break;
            default:
                break;
        }
        return nearByDot;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x, y;
            y = (int) (event.getY() / WIDTH);
            if (y % 2 == 0) {
                x = (int) ((event.getX() - WIDTH / 2) / WIDTH);
            } else {
                x = (int) (event.getX() / WIDTH);
            }

            //如果点击的位置在圆圈点内
            if (x < ROW && y < COL) {
                //如果该点是可点击状态的
                if (matrix[x][y].getStatus() == STATUS_OFF) {
                    matrix[x][y].setStatus(STATUS_ON);
                    //点击完成修改后，先判断游戏状态
                    if (getGameStatus() == GAME_CONDUCT) {
                        stepCount++;
                        moveCat();
                    } else if (getGameStatus() == GAME_FAIL) {
                        //Toast.makeText(mContext, "游戏失败", Toast.LENGTH_SHORT).show();
                        FailGameDialog failGameDialog = new FailGameDialog();
                        failGameDialog.show(((MainActivity) mContext).getSupportFragmentManager(), "failGameDialog");
                    } else if (getGameStatus() == GAME_SUCCESS) {
                        //Toast.makeText(mContext, "游戏成功", Toast.LENGTH_SHORT).show();
                        SuccessGameDialog successGameDialog = new SuccessGameDialog();
                        successGameDialog.show(((MainActivity) mContext).getSupportFragmentManager(), "successGameDialog");
                    }
                    redraw();
                }
            }
        }
        return true;
    }

    public static int getStepCount() {
        return stepCount;
    }

    public static void initStepCount() {
        stepCount = 1;
    }
}
