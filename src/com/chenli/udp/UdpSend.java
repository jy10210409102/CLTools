package com.chenli.udp;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.chenli.dao.MessageDao;
import com.chenli.dao.MsgType;
import com.chenli.dao.User;
import tool.Tool;

public class UdpSend {
//	Activity mainA = null;
//
//	public UdpSend(Activity mainA) {
//		this.mainA = mainA;
//	}

    private static UdpSend udpSend = new UdpSend(); //后来加的一个伪单例

    public static UdpSend udpSend() {    //后来加的一个伪单例
        return udpSend;
    }

    public UdpSend() {
    }

    public void sendMsg(MessageDao msg) {// 启动线程发送
        Send send = new Send(msg);
        send.start();
    }

    class Send extends Thread {

        MessageDao msg = null;

        Send(MessageDao msg) {
            this.msg = msg;
        }

        public void run() {
            SendMsg(msg);
            return;
        }
    }

    // 发消息
    private void SendMsg(MessageDao msg) {
        try {
            msg.setSendUserIp(Tool.getLocalHostIp());//赋值本机ip
            // byte[] data = this.messageLoad(msg);
            byte[] data = Tool.toByteArray(msg);
            // DatagramSocket ds = new DatagramSocket(2426);
            DatagramSocket ds = new DatagramSocket();
            DatagramPacket packet1 = new DatagramPacket(data, data.length,
                    InetAddress.getByName(msg.getReceiveUser()), Tool.udpPort);
            packet1.setData(data);
            //更新消息缓冲区   如果消息未回复则重复发送   并不是所有消息都需要验证是否发送成功        
            Tool.Tool().updataBuffer(msg);

            System.out.println("发送消息：type:" + msg.getMsgType());
            ds.send(packet1);
            ds.close();

            //如果不是广播消息  在发送后  打开计时器 等待确认对方接收消息  未接收到确认信息则继续隔50毫秒发一次 最多发十次
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 上线
    public void upline() throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        // 组装上线msg
        MessageDao msg = new MessageDao(Tool.getTimel() + "", Tool.getName(),
                MsgType.ONLINE, Tool.getBroadCastIP());
        sendMsg(msg);// 发送广播通知上线
    }

    // 下线
    public void downline() throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体

        // 组装上线msg
        MessageDao msg = new MessageDao(Tool.getTimel() + "", Tool.getName(),
                MsgType.DOWNLINE, Tool.getBroadCastIP());
        sendMsg(msg);// 发送广播通知下线
    }

    // 询问是否接收文件 **
    public void fileXunWen(User user, String path) throws Exception {
        // 组装上线msg
        MessageDao msg = new MessageDao(Tool.getTimel() + "", Tool.getName(),
                MsgType.FileTishi, user.getIp(),
                (new File(path)).getName() + Tool.sign + (new File(path)).length());
        sendMsg(msg);//
    }

    // 回复 接收
    public void fileJie(String ip) throws Exception {
        // 组装上线msg
        MessageDao msg = new MessageDao(Tool.getTimel() + "", Tool.getName(),
                MsgType.FILEJIE, ip);
        sendMsg(msg);// 发送广播通知下线
    }

    // 回复 拒绝
    public void fileJu(String ip) throws Exception {
        // 组装上线msg
        MessageDao msg = new MessageDao(Tool.getTimel() + "", Tool.getName(),
                MsgType.FILEJU, ip);
        sendMsg(msg);// 发送广播通知下线
    }

    //下线
    public void fileOver(String ip) throws Exception {
        // 组装上线msg
        MessageDao msg = new MessageDao(Tool.getTimel() + "", Tool.getName(),
                MsgType.FILEOVER, ip);
        sendMsg(msg);// 发送广播通知下线
    }

    //开启软件时广播可提供网络服务
    public void canInterServce() throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        // 组装上线msg
        MessageDao msg = new MessageDao(Tool.getTimel() + "", Tool.getName(),
                MsgType.INTSERVER, Tool.getBroadCastIP());
        msg.setBody(Tool.Tool().getTranslateClinetCount());
        sendMsg(msg);// 发送广播通知上线
    }

    //其他客户端请求  回复可提供网络服务
    public void clientRequestCanInterServce(String ip) throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        // 组装上线msg
        long currentTime = Tool.getTimel();
        MessageDao msg = new MessageDao(currentTime + "", currentTime, Tool.getName(),
                MsgType.INTSERVER, ip);
        msg.setBody(Tool.Tool().getTranslateClinetCount());//设置消息主题为客户端个数
        sendMsg(msg);// 发送广播通知上线
    }

    //请求提供网络服务   并打开计时开关选择最优服务
    public void requestInterServce() throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        // 组装上线msg
        MessageDao msg = new MessageDao(Tool.getTimel() + "", Tool.getName(),
                MsgType.REQUESTSERVER, Tool.getBroadCastIP());
        sendMsg(msg);// 发送广播通知上线
        Tool.Tool().openScanServce();//开始搜索服务 打开开关 和记录初始搜索时间
    }

    // 回复消息接收成功
    public void acceptOK(String ip, long msgMark) throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        MessageDao msg = new MessageDao(Tool.getTimel() + "", msgMark, Tool.getName(),
                MsgType.ACCEPTEOK, ip);
        sendMsg(msg);
    }

    // 重发消息
    public void againMsg(MessageDao msg) throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        sendMsg(msg);// 发送广播通知上线
    }

    // 发送普通消息消息
    public void sendPublicMsg(MessageDao msg) throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        sendMsg(msg);// 发送广播通知上线
    }

    // 发送在线翻译请求
    public void requestGoogleTran(String ip, String[] strs) throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        System.out.println("发送在线翻译请求发送在线翻译请求发送在线翻译请求发送在线翻译请求发送在线翻译请求\r\n");
        long currentTime = Tool.getTimel();
        MessageDao msg = new MessageDao(currentTime + "", currentTime, Tool.getName(),
                MsgType.REQUESTTRANSLATE, ip);
        msg.setBody(strs);//设置翻译内容
        sendMsg(msg);// 发送给服务端
    }
    
    //回复翻译结果
     public void replyGoogleTranRsult(String ip, String[] strs) throws Exception {// 版本号：数据包id：发送人昵称：消息类型：消息体
        long currentTime = Tool.getTimel();
        MessageDao msg = new MessageDao(currentTime + "", currentTime, Tool.getName(),
                MsgType.REPLYTRANSLATE, ip);
        msg.setBody(strs);//设置翻译内容
        sendMsg(msg);// 发送给服务端
    }
}
