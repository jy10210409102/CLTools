/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chenli.dao;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class MsgBuffer implements Serializable{
    private int type;       //类型 如 客户端类型  时间是 5分钟  普通消息 20毫秒
    private long lastTime;  //最后一次时间记录
    private MessageDao msg; //普通消息内容
    private String clientIp;//客户端ip

    public MsgBuffer() {
    }

    public MsgBuffer(int type, long lastTime, MessageDao msg) {
        this.type = type;
        this.lastTime = lastTime;
        this.msg = msg;
    }

    public MsgBuffer(int type, long lastTime, String clientIp) {
        this.type = type;
        this.lastTime = lastTime;
        this.clientIp = clientIp;
    }

    public MsgBuffer(int type, long lastTime, MessageDao msg, String clientIp) {
        this.type = type;
        this.lastTime = lastTime;
        this.msg = msg;
        this.clientIp = clientIp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public MessageDao getMsg() {
        return msg;
    }

    public void setMsg(MessageDao msg) {
        this.msg = msg;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
    
    
    
}
