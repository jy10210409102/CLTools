package com.chenli.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import tool.Tool;

public class TcpServer {

    private static TcpServer tcp = new TcpServer();

    private TcpServer() {
    }

    public static TcpServer Tcp() {
        return tcp;
    }
    //启动tcp服务

    public void start() {
        Server server = new Server();
        server.start();
    }

    class Server extends Thread {

        public void run() {
            try {
                creatServer();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void creatServer() throws Exception {
        ServerSocket ss = new ServerSocket(Tool.tcpTransPort);
        while (true) {
            System.out.println("等待客户端连接");
            Socket s = ss.accept();
            System.out.println("有客户端连接");
            Tool.clientBuffer.add(s.getInetAddress());//添加客户端
            TcpOperate to = new TcpOperate(s);
            to.start();
        }


    }

    //文件传递
    public void fileDliver() throws Exception {
        // 服务器只是短时间用来传送文件 只用一次 用来接收文件
//        File file = new File(Tool.savePath + "/" + Tool.fileName);
//        if (!file.exists()) {
//            file.getParentFile().mkdirs();
//            file.createNewFile();
//        }
//        BufferedInputStream is = new BufferedInputStream(s.getInputStream()); // 读进
//        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));// 写出
//        Thread.sleep(1000);
//        int n = 1;
//        long part = Tool.fileSize / Tool.byteSize;// 分成几段
//        long surplus = Tool.fileSize % Tool.byteSize;// 最后剩余多少段
//        byte[] data = new byte[Tool.byteSize];// 每次读取的字节数
//        int len = -1;
//        while ((len = is.read(data)) != -1) {
//            os.write(data, 0, len);
//            Tool.sendProgress += len;//进度
//        }
//        Tool.sendProgress = -1;
//        is.close();
//        os.flush();
//        os.close();
//        s.close();
    }
}
