package com.chenli.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import com.chenli.dao.MessageDao;
import tool.Tool;
import com.chenli.udp.UdpSend;

public class TcpClient {

    // String path = null;
    public static TcpOperate clientTrans = null; //翻译客户端唯一对象

    //启动客户端
    public void start() {
        Client c = new Client();
        c.start();
    }

    class Client extends Thread {

        public void run() {
            try {
                creatClient();
                // Tools.sendProgress=-1;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void creatClient() throws Exception {
        //Socket s = new Socket(Tool.bastServceIp, Tool.tcpTransPort);
        System.out.println("TCP客户端准备连接");
        Socket s = new Socket("192.168.0.149", Tool.tcpTransPort);//test
        System.out.println("TCP客户端连接建立成功！！！！！！！！");
        TcpClient.clientTrans = new TcpOperate(s); //翻译客户端       
        TcpClient.clientTrans.start();
        //伪网络连接成功
        Tool.valnConState=true;

    }

    public void filePass() {
        // 读文件
//        File file = new File(path);
//        BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
//        BufferedOutputStream os = new BufferedOutputStream(s.getOutputStream());
//        // 读文件
//        double n = 1;
////		long part = file.length() / Tools.byteSize;// 分成几段�
////		long surplus = file.length() % Tools.byteSize;// �最后剩余多少段
//        byte[] data = new byte[Tool.byteSize];// 每次读取的字节数�ֽ���
//        int len = -1;
//        while ((len = is.read(data)) != -1) {
//            os.write(data, 0, len);
//            Tool.sendProgress += len;//进度�
//        }
//        Tool.sendProgress = -1;
//        is.close();
//        os.flush();
//        os.close();
//        //发送消息告诉几经上传完�߼����ϴ���
//        UdpSend us = new UdpSend();
//        us.fileOver(msg.getSendUserIp());
    }
}
