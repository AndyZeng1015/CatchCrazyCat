package com.zyn.catchcrazycat.bean;

import static com.zyn.catchcrazycat.Config.Contast.STATUS_OFF;

/**
 * Desc: 每一个圆圈的实体
 * CreateDate: 2017/4/15 17:31
 * Author: Created by ZengYinan
 * Email: 498338021@qq.com
 */

public class Dot {
    private int x;
    private int y;
    private int status;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
        status = STATUS_OFF;//默认为可点击的状态
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

}
