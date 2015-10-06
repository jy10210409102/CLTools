package com.chenli.dao;

public class MsgType implements java.io.Serializable {
    /*聊天传文件相关*/
    public static final int ONLINE = 6291457;                    //上线
    public static final int SELFONLINE = 6291458;                //在线
    public static final int DOWNLINE = 6291456;                  //下线
    public static final int Msg = 6291459;                       //文字消息
    public static final int FileTishi = 6291460;                 //文件接收提示
    public static final int FILEJIE = 6291461;                   // 对方确定接收文件
    public static final int FILEJU = 6291462;                    // 对方拒绝接收文件
    public static final int FILEOVER = 6291463;                  //文件接收完成
    public static final int ACCEPTEOK = 6291464;                  //非广播消息 接受成功 
    /*翻译相关*/
    public static final int INTSERVER = 4000;                  //可以提供网络服务
    public static final int REQUESTSERVER = 4001;              //请求网络服务
    public static final int REQUESTTRANSLATE = 4002;           //请求翻译
    public static final int REPLYTRANSLATE = 4003;              //翻译回复
    /*msgBuffer type*/
    public static final int CLIENTTYPE = 3000;              //客户端
    public static final int PUBLICMSG = 3001;              //普通文字消息
}
