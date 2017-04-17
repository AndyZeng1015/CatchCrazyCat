package com.zyn.catchcrazycat.Config;

/**
 * Desc:
 * CreateDate: 2017/4/16 14:41
 * Author: Created by ZengYinan
 * Email: 498338021@qq.com
 */

public class Contast {

    public static final String SP_NAME = "catchCrazyCat";

    public static final int STATUS_ON = 1; //圆圈已点击
    public static final int STATUS_OFF = 0; //圆圈为可点击状态
    public static final int STATUS_IN = 9; //猫在本圆圈上

    public static final int ROW = 10; //行数
    public static final int COL = 10; //列数
    public static final int BLOCKS = 10;//默认的路障个数

    //游戏状态
    public static final int GAME_FAIL = 1;//游戏失败
    public static final int GAME_SUCCESS = 2;//游戏成功
    public static final int GAME_CONDUCT = 3;//游戏进行中

    //方向
    public static final int LEFT = 0;//左
    public static final int LEFT_UP = 1;//左上
    public static final int RIGHT_UP = 2;//右上
    public static final int RIGHT = 3;//右
    public static final int RIGHT_DOWN = 4;//右下
    public static final int LEFT_DOWN = 5;//左下
}
