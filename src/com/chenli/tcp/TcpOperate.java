/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chenli.tcp;

import com.chenli.dao.MessageDao;
import com.chenli.dao.MsgType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import tool.Tool;

/**
 *
 * @author Administrator
 */
public class TcpOperate extends Thread {

    String ip = null;//对方ip
    Socket s = null;
    ObjectInputStream is = null;// new BufferedInputStream(s.getInputStream()); // 读进
    ObjectOutputStream os = null;//new BufferedOutputStream(new FileOutputStream(file));// 写出

    public TcpOperate(Socket s) {
        this.s = s;
        try {
            System.out.println(" public TcpOperate(Socket s) {");
            os = new ObjectOutputStream(s.getOutputStream());// 写出
            ip = s.getInetAddress().toString();
            System.out.println("TcpOperate 构造完成 ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            is = new ObjectInputStream(s.getInputStream());// 读进
            //接受消息并分类处理
            msgClassfiy();
        } catch (Exception ex) {
            Logger.getLogger(TcpOperate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //读取消息获得msg
    public MessageDao readMsg() {
        MessageDao msg = null;
        try {
            msg = (MessageDao) is.readObject();
        } catch (Exception e) {
            //如果本机有网 表示 客户端断开       //连接断开 则个数减1
            if (Tool.internetConState) {
                Tool.clientBuffer.remove(ip);
            } else {    //如果本机无网 表示 服务器断开
                Tool.valnConState = false;//伪网络断开
            }




            e.printStackTrace();
        }
        return msg;
    }

    //消息处理
    public void msgClassfiy() throws Exception {
        while (true) {
            MessageDao msg = readMsg();//读取消息
            switch (msg.getMsgType()) {
                case MsgType.REQUESTTRANSLATE: // 对方请求在线翻译
                    System.out.println("对方请求在线翻译");
                    //翻译并回复
                    rplyTrans(msg);
                    break;
                case MsgType.REPLYTRANSLATE: // 对方回复翻译结果                   
                    System.out.println("对方回复翻译结果，消息mark：" + msg.getMsgmMark() + "msgID：" + msg.getPacketID() + "接收人ip：" + msg.getReceiveUser() + "消息body：" + ((String[]) msg.getBody())[2] + "!!");
                    String[] strs = (String[]) msg.getBody();
                    Tool.tansBuffer.put(strs[0] + "-" + strs[1], strs[2]);//存到 “今天-en”，“taday”
                    break;
            }
        }
    }

    /*
     * 基本消息定义
     */
    //发送消息
    public void sendMsg(MessageDao msg) throws IOException, Exception {
        msg.setSendUserIp(Tool.getLocalHostIp());//设置发送人ip
        os.writeObject(msg);
        os.flush();
    }

    //发送请求翻译消息
    public void requestTrans(String[] strs) throws Exception {
        // 组装msg
        MessageDao msg = new MessageDao(Tool.getTimel() + "", Tool.getName(),
                MsgType.REQUESTTRANSLATE, Tool.bastServceIp);
        msg.setBody(strs);//设置翻译内容
        sendMsg(msg);// 发送广播通知下线
    }

    //回复翻译结果
    public void replyGoogleTranRsult(String ip, String[] strs) throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        long currentTime = Tool.getTimel();
        MessageDao msg = new MessageDao(currentTime + "", currentTime, Tool.getName(),
                MsgType.REPLYTRANSLATE, ip);
        msg.setBody(strs);//设置翻译内容
        sendMsg(msg);// 发送给服务端
    }

    /*
     * 逻辑处理
     */
    //回复结果
    public boolean rplyTrans(MessageDao msg) throws Exception {
        String result = transAndRply(msg);
        //发送翻译结果
        String[] strs = new String[]{((String[]) msg.getBody())[0], ((String[]) msg.getBody())[1], result};//原文+翻译结果
        replyGoogleTranRsult(msg.getSendUserIp(), strs);
        return false;
    }

    //获得翻译结果
    public String transAndRply(MessageDao msg) throws Exception {
        String[] strs = ((String[]) msg.getBody());
        String result = Tool.Tool().Translate(null, strs[1], strs[0]);
        System.out.println(strs[0]+"翻译成!!!!!!!!!!"+strs[1]+"!!!!!!!!!!!!!!翻译结果：" + result);
        return result;

    }
}
