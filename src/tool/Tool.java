/*
 * To change this template, choose Tool | Templates
 * and open the template in the editor.
 */
package tool;

import GUI.Translate_GUI;
import com.chenli.dao.MessageDao;
import com.chenli.dao.MsgType;
import com.chenli.dao.RowLanguage;
import com.chenli.dao.User;
import com.chenli.tcp.TcpClient;
import com.chenli.tcp.TcpServer;
import com.chenli.udp.UdpReceive;
import com.chenli.udp.UdpSend;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Administrator
 */
public class Tool {

    FileSystemView fsv = FileSystemView.getFileSystemView();
    private static Tool t = new Tool();

    public Tool() {    // 不用单例 防止并发执行错误
        deskPath = fsv.getHomeDirectory().getPath();    //这便是读取桌面路径的方法了
        chooseStartPath = deskPath;
    }

    public static Tool Tool() {
        return t;
    }
    public final String deskPath;
    public static String chooseStartPath = null;
    /*
     * 语言定义
     */
    public static final int ENGLISH = 0; //_T("English"),  en
    public static final int CHS = 1; //_T("中文（简体）"),zh-CN
    public static final int CHR = 2; //_T("中文（繁体）"),zh-TW
    public static final int ARABIC = 3;//_T("阿拉伯语"),   ar
    public static final int HEBREW = 4;//__T("希伯来语"), iw
    public static final int RUSSIAN = 5; //_T("俄罗斯语"),  ru 
    public static final int GREECE = 6; //_T("希腊语")  el
    public static final int SLOVENE = 7; //_T("斯洛文尼亚") sl
    public static final int PORTUGAL = 8; //_T("葡萄牙语"),pt
    public static final int TURKSIH = 9; //_T("土耳其语"), tr
    public static final int FRENCH = 10; //_T("法语"),  fr
    public static final int GERMAN = 11; //_T("德语"),	de
    public static final int ITALY = 12; //_T("意大利语"), it
    public static final int SPAIN = 13; //_T("西班牙语"), es
    public static final int HUNGARY = 14; //_T("匈牙利语"),hu	
    public static final int POLISH = 15; //_T("波兰语"),pl
    public static final int RUMANIA = 16; //_T("罗马尼亚"),ro
    public static final int CZECH = 17; //_T("捷克语"),cs 
    public static final int VIETNAMESE = 18; //_T("越南语"),
    public static final int DENMARK = 19; //_T("丹麦语"),
    public static final int JAPANESE = 20; //_T("日语"),
    public static final int THAI = 21; //_T("泰语"),
    public static final HashMap languageMap = new HashMap();   //协议
    public static final HashMap languageStrMap = new HashMap();   //多国语言表头字符串map
    public static final HashMap languageChsMap = new HashMap();   //语言对应的字符串map
    public static final HashMap BITMap = new HashMap();   //语言对应的字符串map
    public static final int languageCount = 22; //语言个数
    public static final int onLineTranThreadCount = 5; // 在线翻译线程最多开启个数
    public static String charSet = null;//字符
    public static Translate_GUI TranMain = null;//翻译主界面
    public static boolean internetConState = false;//网络连接状态
    public static boolean databaseConState = false;//数据库连接状态
    public static boolean valnConState = false;//伪网络连接状态
    /*通信*/
    static final String CFGPATH = "/mnt/sdcard/wifiFQ/config";
    public static final String savePath = "/mnt/sdcard/wifiFQ/Files";
    public static String fileName = null;// 发送的文件名称
    public static long fileSize = 0;// 文件大小
    public static int byteSize = 1024 * 5;// 每次读写文件的字节数
    public static String sign = ":";
    //public static String startPath = FileChoose.ADDRESS;
    public static Map<String, String> MsgEx = new HashMap<String, String>();
    public static double sendProgress = -1;// 每次读写文件的字节数s
    public static float fontSize = 0;// chat msgShow的字体大小
    public static float chatFirst = 0;// chat msgShow的字体大小
    public static Thread timeListening = null;//计时器线程对象
    // public static Vector<MsgBuffer> msgBufferVector = new Vector<MsgBuffer>();//计时器遍历消息缓冲
    public static Hashtable<String, Long> clientBufferMap = new Hashtable<String, Long>();//客户端记录  udp久的
    public static Vector clientBuffer = new Vector();//服务器集合  tcp 新的
    public static Hashtable<Long, MessageDao> msgBufferMap = new Hashtable<Long, MessageDao>();//消息缓冲记录
    public static boolean scanServceONOFF = false;//搜索服务开关
    public static long scanServceStartTime = -1;//开始搜索的时间
    public static String bastServceIp = null;//最佳服务ip
    public static HashMap<String, Integer> ServceMap = new HashMap<String, Integer>();//服务器集合   
    public static int udpPort = 2425;//udp端口号
    public static int tcpTransPort = 2395;//TCP翻译端口号
    public static int tcpPassFliePort = 2223;//TCP传文件端口号
    public static int testPort = 2299;//程序多开测试端口
    public static DatagramSocket testDs = null;//测试多开
    public static Hashtable<String, String> tansBuffer = new Hashtable();//翻译缓存

    /*
     * 对应修改区 start
     */
    static {                               //对应google翻译协议
        languageMap.put(ENGLISH, "en");
        languageMap.put(CHS, "zh-CN");
        languageMap.put(CHR, "zh-TW");
        languageMap.put(ARABIC, "ar");
        languageMap.put(HEBREW, "iw");
        languageMap.put(RUSSIAN, "ru");
        languageMap.put(GREECE, "el");
        languageMap.put(SLOVENE, "sl");
        languageMap.put(PORTUGAL, "pt");
        languageMap.put(TURKSIH, "tr");
        languageMap.put(FRENCH, "fr");
        languageMap.put(GERMAN, "de");
        languageMap.put(ITALY, "it");
        languageMap.put(SPAIN, "es");
        languageMap.put(HUNGARY, "hu");
        languageMap.put(POLISH, "pl");
        languageMap.put(RUMANIA, "ro");
        languageMap.put(CZECH, "cs");//捷克语
        languageMap.put(VIETNAMESE, "vi");//越南
        languageMap.put(DENMARK, "da");//
        languageMap.put(JAPANESE, "ja");//
        languageMap.put(THAI, "th");//泰语
    }

    static {                                   //对应数据库表头
        languageStrMap.put(ENGLISH, "ENGLISH");
        languageStrMap.put(CHS, "CHS");
        languageStrMap.put(CHR, "CHR");
        languageStrMap.put(ARABIC, "ARABIC");
        languageStrMap.put(HEBREW, "HEBREW");
        languageStrMap.put(RUSSIAN, "RUSSIAN");
        languageStrMap.put(GREECE, "GREECE");
        languageStrMap.put(SLOVENE, "SLOVENE");
        languageStrMap.put(PORTUGAL, "PORTUGAL");
        languageStrMap.put(TURKSIH, "TURKSIH");
        languageStrMap.put(FRENCH, "FRENCH");
        languageStrMap.put(GERMAN, "GERMAN");
        languageStrMap.put(ITALY, "ITALY");
        languageStrMap.put(SPAIN, "SPAIN");
        languageStrMap.put(HUNGARY, "HUNGARY");
        languageStrMap.put(POLISH, "POLISH");
        languageStrMap.put(RUMANIA, "RUMANIA");
        languageStrMap.put(CZECH, "CZECH");//捷克语
        languageStrMap.put(VIETNAMESE, "VIETNAMESE");//越南
        languageStrMap.put(DENMARK, "DENMARK");//
        languageStrMap.put(JAPANESE, "JAPANESE");//
        languageStrMap.put(THAI, "THAI");//泰语
    }

    static {                                   //对应语言的字符串表头
        languageChsMap.put(ENGLISH, "英语");
        languageChsMap.put(CHS, "中文简体");
        languageChsMap.put(CHR, "中文繁体");
        languageChsMap.put(ARABIC, "阿拉伯语");
        languageChsMap.put(HEBREW, "希伯来语");
        languageChsMap.put(RUSSIAN, "俄罗斯语");
        languageChsMap.put(GREECE, "希腊语");
        languageChsMap.put(SLOVENE, "斯洛文尼亚");
        languageChsMap.put(PORTUGAL, "葡萄牙语");
        languageChsMap.put(TURKSIH, "土耳其语");
        languageChsMap.put(FRENCH, "意大利语");
        languageChsMap.put(GERMAN, "德语");
        languageChsMap.put(ITALY, "意大利语");
        languageChsMap.put(SPAIN, "西班牙语");
        languageChsMap.put(HUNGARY, "匈牙利语");
        languageChsMap.put(POLISH, "波兰语");
        languageChsMap.put(RUMANIA, "罗马尼亚");
        languageChsMap.put(CZECH, "捷克语");//捷克语
        languageChsMap.put(VIETNAMESE, "越南语");//越南
        languageChsMap.put(DENMARK, "丹麦语");//
        languageChsMap.put(JAPANESE, "日语");//
        languageChsMap.put(THAI, "泰语");//泰语
    }
    /*日志系统 如
     * <加密>[成功]-时间-[2013-07-07 15:22]-大小[1.141k]-耗时[0.09秒]
     >>文件路径:C:\Users\Administrator\Desktop\纯汉语\翻译后\test.txt
     * 
     */
    /*
     * 对应修改区 end
     */
    //移位 计算器使用
    static final int BIT0 = (0x01 << 0);
    static final int BIT1 = (0x01 << 1);
    static final int BIT2 = (0x01 << 2);
    static final int BIT3 = (0x01 << 3);
    static final int BIT4 = (1 << 4);
    static final int BIT5 = (1 << 5);
    static final int BIT6 = (1 << 6);
    static final int BIT7 = (0x01 << 7);
    static final int BIT8 = (0x01 << 8);
    static final int BIT9 = (0x01 << 9);
    static final int BIT10 = (0x01 << 10);
    static final int BIT11 = (0x01 << 11);
    static final int BIT12 = (0x01 << 12);
    static final int BIT13 = (0x01 << 13);
    static final int BIT14 = (0x01 << 14);
    static final int BIT15 = (0x01 << 15);
    static final int BIT16 = (0x01 << 16);
    static final int BIT17 = (0x01 << 17);
    static final int BIT18 = (0x01 << 18);
    static final int BIT19 = (0x01 << 19);
    static final int BIT20 = (0x01 << 20);
    static final int BIT21 = (0x01 << 21);
    static final int BIT22 = (0x01 << 22);
    static final int BIT23 = (0x01 << 23);
    static final int BIT24 = (0x01 << 24);
    static final int BIT25 = (0x01 << 25);
    static final int BIT26 = (0x01 << 26);
    static final int BIT27 = (0x01 << 27);
    static final int BIT28 = (0x01 << 28);
    static final int BIT29 = (0x01 << 29);
    static final int BIT30 = (0x01 << 30);

    static {
        BITMap.put(0, BIT0);
        BITMap.put(1, BIT1);
        BITMap.put(2, BIT2);
        BITMap.put(3, BIT3);
        BITMap.put(4, BIT4);
        BITMap.put(5, BIT5);
        BITMap.put(6, BIT6);
        BITMap.put(7, BIT7);
        BITMap.put(8, BIT8);
        BITMap.put(9, BIT9);
        BITMap.put(10, BIT10);
        BITMap.put(11, BIT11);
        BITMap.put(12, BIT12);
        BITMap.put(13, BIT13);
        BITMap.put(14, BIT14);
        BITMap.put(15, BIT15);
        BITMap.put(16, BIT16);
        BITMap.put(17, BIT17);
        BITMap.put(18, BIT18);
        BITMap.put(19, BIT19);
        BITMap.put(20, BIT20);
        BITMap.put(21, BIT21);
        BITMap.put(22, BIT22);
        BITMap.put(23, BIT23);
        BITMap.put(24, BIT24);
        BITMap.put(25, BIT25);
        BITMap.put(26, BIT26);
        BITMap.put(27, BIT27);
        BITMap.put(28, BIT28);
        BITMap.put(29, BIT29);
        BITMap.put(30, BIT30);
    }
    public static JTextArea TranTxtOut = null;//翻译工具主界面文本输出框
    public static String filePath = null;//需分割的文件路径
    public static User mySelf = null;//个人信息
    //启动初始化

    public void init() throws Exception {
        //启动接收线程
        UdpReceive receive = new UdpReceive();
        receive.Star();

        //启动计时器
        timeListening = new Thread() {
            public void run() {
                TimerListerning();
            }
        };
        timeListening.start();
        //初始化个人信息       
        mySelf = new User(Tool.getName(), Tool.getLocalHostIp());

        //广播上线信息
        UdpSend udpSend = new UdpSend();
        udpSend.upline();

        //网络判断
        if (isInterntConnectionTest()) //已连接网络
        {

            //启动tcp服务
            System.out.println("TCP服务器已开！!!!!!!1");
            TcpServer.Tcp().start();
            //广播提供网络服务
            udpSend.canInterServce();

        } else { //未连接网络
            //广播请求网络服务
            udpSend.requestInterServce();
        }



    }
    //格式化文件大小

    public String formatFileSize(long f) {
        String size;
        if (f == 0) {
            size = "0 kb";
        } else {
            if (f / 1024 / 1024 >= 1024) {
                if (((f / 1024 / 1024 / 1024.0 + "").length()) < 5) {
                    size = (f / 1024 / 1024 / 1024.0 + "") + "G";
                } else {
                    size = (f / 1024 / 1024 / 1024.0 + "").substring(0, 5) + "G";
                }
            } else if (f / 1024 >= 1024) {
                if ((f / 1024 / 1024.0 + "").length() < 5) {
                    size = (f / 1024 / 1024.0 + "") + "M";
                } else {
                    size = (f / 1024 / 1024.0 + "").substring(0, 5) + "M";
                }
            } else {
                if ((f / 1024.0 + "").length() < 5) {
                    size = (f / 1024.0) + "k";
                } else {
                    size = (f / 1024.0 + "").substring(0, 5) + "k";
                }
            }
        }
        return size;
    }
    //返回文件或目录path

    public String getDirOrFile(JFrame jf, String dirOrFile) {
        JFileChooser chooser = new JFileChooser(chooseStartPath);
        File file = null;
        if (dirOrFile.equals("d")) {//如果选择目录        
            chooser.setDialogTitle("目录选择器");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = chooser.showOpenDialog(jf);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                return file.getPath();
            }
        } else {//如果选择文件
            chooser.setDialogTitle("文件选择器");
            int returnVal = chooser.showOpenDialog(jf);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                return file.getPath();
            }
        }
        return null;
    }

    //遍历获得指定类型文件
    public ArrayList<File> getFileList(File file, ArrayList<File> list, String suffix, JTextArea jTxt) {
        File[] f = file.listFiles();
        if (jTxt != null) {
            jTxt.append("开始搜索后缀名为：" + suffix + " 的文件,\r\n   请等待···\r\n\r\n\r\n");
        }
        for (int i = 0; i < f.length; i++) {
            if (f[i].isDirectory()) {
                getFileList(f[i], list, suffix, null);
            } else {
                if (f[i].getName().endsWith(suffix)) {//如果是文件,后缀名为suffix 则添加至list
                    list.add(f[i]);
                }
            }
        }
        return list;
    }

    //写文件到指定目录 返回改后的文件名
    public String writeFileTo(String path, String savePath) throws Exception {
        File file = new File(path);
        InputStream is = new FileInputStream(path);
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        File saveFile = new File(savePath + "\\" + file.getName());
        if (saveFile.exists()) {//如果文件存在则重命名
            saveFile = new File(savePath + "\\" + file.getName() + "-重名" + new Date().getTime());
        }
        OutputStream os = new FileOutputStream(saveFile);
        byte[] data = new byte[1024 * 1024];
        if (file.length() < 1024 * 1024) {//如果文件小于一兆
            data = new byte[Integer.parseInt(file.length() + "")];
            is.read(data, 0, data.length);
            os.write(data, 0, data.length);
        } else {
            long yuShu = file.length() % (1024 * 1024);//余数
            long duoS = file.length() / 1024 / 1024;
            if (yuShu != 0) {
                duoS++;
            }
            int count = 0;
            while (is.read(data, 0, data.length) != -1) {
                count++;
                os.write(data, 0, data.length);
                if (yuShu != 0 && duoS == count - 1) {
                    data = new byte[Integer.parseInt(yuShu + "")];
                }
            }
        }
        is.close();
        os.close();
        return saveFile.getName();
    }

    //取系统当前时间字符串
    public String getTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return sdf.format(d);
    }

    //取系统当前时间字符串
    public String getTime1() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

//替换字符把文件路径
    public String pathRepalce(String path) {
        if (path.contains(":")) {
            path = path.replaceAll(":", "-");
        }
        if (path.contains("\\")) {
            path = path.replaceAll("\\", "-");
        }
        return path;
    }

    //多国语言翻译
    public String Translate(String currenLanguage, String toLanguage, String txt) throws Exception {
        if (currenLanguage == null) {
            currenLanguage = "auto";
        }
        //转码
        txt = URLEncoder.encode(txt, "utf-8");
        System.out.println(txt);
        String action = "http://translate.google.cn/translate_a/t?client=t&hl=" + currenLanguage + "&sl=" + currenLanguage + "&tl=" + toLanguage + "&ie=UTF-8&oe=UTF-8&ssel=0&tsel=0&uptl=" + toLanguage + "&sc=1&q=" + txt;
        URL url = new URL(action);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("User-agent", "IE/9.0"); // 必须，否则报错，到于FF的怎么写，没做过测试！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        http.connect();//连接  
        BufferedReader is = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String str;
        String temp = "";
        while ((str = is.readLine()) != null) {
            temp += str;
            System.out.println(str);
        }
        is.close();
        System.out.println(temp.split("\"")[1]);
        return temp.split("\"")[1];
    }

    //文件读取 循环取中文作为翻译样本
    public ArrayList<RowLanguage> getLanguageList(String filePath) throws Exception {
        ArrayList<RowLanguage> LanguageList = new ArrayList<RowLanguage>();//行语言列表
        BufferedReader br = null;
        if (charSet.equals("UNICODE")) {
            System.out.println("charSet.equals(\"UNICODE\")");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UNICODE"));
        } else if (charSet.equals("gbk")) {
            System.out.println("charSet.equals(\"gbk\")");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "gbk"));
        }
        String temp;
        int n = 0;
        while ((temp = br.readLine()) != null) {
            String[] s = temp.split("\\t");
            if (s.length < 2) {
                continue;
            }
            if (s.length == 3) { //如果只有汉字  格式如：<	启动	>
                n = 1;
            } else {//全语种格式
                n = 2;
            }
            // System.out.println("多国语言\\t分割成："+s.length+"份");
            LanguageList.add(new RowLanguage(s[n])); //创建一行语言类 并且中文赋值
            System.out.println("  简体中文：" + s[n]);
        }
        br.close();
        return LanguageList;
    }

    //文件读取 获得所有已经翻译的语种
    public ArrayList<RowLanguage> getLanguageFilshList(String filePath) throws Exception {
        ArrayList<RowLanguage> LanguageList = new ArrayList<RowLanguage>();//行语言列表
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UNICODE"));
        String temp;
        RowLanguage rl = null;
        //判断是否为多国语言文件
        while ((temp = br.readLine()) != null) {
            String[] s = temp.split("\\t");
            if (s.length < 2) {
                continue;
            }
            // System.out.println("多国语言\\t分割成："+s.length+"份");
            rl = new RowLanguage();
            String[] language = rl.getLanguage();
            try {
                for (int i = 0; i < language.length; i++) {
                    if (Tool.Tool().isNum(s[i + 1])) { //如果是数字
                        language[i] = null;
                    } else {//如果不是数字
                        language[i] = s[i + 1];
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(TranMain, "纯汉字翻译请选择【翻译所有语种】！", "操作", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            rl.setLanguage(language);
            LanguageList.add(rl); //创建一行语言类 并且中文赋值
            System.out.println("  简体中文：" + s[2]);
        }
        br.close();
        return LanguageList;
    }

    //多国语言google或数据库翻译并按格式写入文件    并存入数据库   如果是goole翻译 src为null 
    public boolean write(String filePath, ArrayList<RowLanguage> LanguageList, String src) throws Exception {
        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "unicode"));//ISO-8859-1
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "x-UTF-16LE-BOM"));
        String temp = "<";
        if (TranTxtOut.getText().length() > Integer.MAX_VALUE - 500) {//如果输出框文字太多 则清空
            TranTxtOut.setText("");
            TranTxtOut.removeAll();
        }
        if (LanguageList == null) {
            return false;
        }
        for (int i = 0; i < LanguageList.size(); i++) {
            TranTxtOut.append("<正在翻译>    [" + LanguageList.get(i).getLanguage()[1] + "]\r\n");
            TranTxtOut.setCaretPosition(TranTxtOut.getText().length());
            for (int j = 0; j < LanguageList.get(i).getLanguage().length; j++) {
                if (LanguageList.get(i).getLanguage()[j] == null) {  //如果这个语种未翻译 则开始翻译
                    //数据库查询  如果数据库查询不到 则google在线翻译 如果网络未连接 则提示
                    if (src != null) {
                        LanguageList.get(i).getLanguage()[j] = queryDataBase(src, LanguageList.get(i).getLanguage()[1], (String) Tool.languageStrMap.get(j));//获得数据库翻译
                        // System.out.println(LanguageList.get(i).getLanguage()[1] + "---数据库翻译后---- " + (String) Tool.languageStrMap.get(j) + "  " + j + " -----：" + LanguageList.get(i).getLanguage()[j]);
                        //判断数据库是否翻译
                    }
                    if (LanguageList.get(i).getLanguage()[j] == null) {//如果未翻译 则google查询
                        //如果网络连接 则google查询
                        if (Tool.internetConState) {//如果网络连接
                            //google查询    
                            System.out.println("google 联网查询 ：" + LanguageList.get(i).getLanguage()[1] + "查询中------------------");

                            LanguageList.get(i).getLanguage()[j] = Tool.Tool().Translate(null, (String) Tool.languageMap.get(j), LanguageList.get(i).getLanguage()[1]);//获得google翻译
                            if (DataBaeOperate.DBO().con != null) {
                                TranTxtOut.append("<google在线>	" + ((String) languageChsMap.get(j)) + "：\t" + LanguageList.get(i).getLanguage()[j]);
                            } else {
                                TranTxtOut.append("<google在线>	" + ((String) languageChsMap.get(j)) + "：\t" + LanguageList.get(i).getLanguage()[j] + " *数据库未连接\r\n");

                            }
                            TranTxtOut.setCaretPosition(TranTxtOut.getText().length());
                            //是否存在中文对应的google翻译                     //存数据库     通过简体中文和翻译来源查询  languageStrMap.get(j)语言是否已翻译 ，已翻译则不处理，未翻译存入数据库
                            saveToDateBase(LanguageList.get(i).getLanguage()[1], ((String) languageStrMap.get(j)), LanguageList.get(i).getLanguage()[j]);
                        } else if (Tool.valnConState) { //如果伪网络已连接
                            LanguageList.get(i).getLanguage()[j] = valnRequest(LanguageList.get(i).getLanguage()[1], (String) Tool.languageMap.get(j));//局域网请求
                            if (DataBaeOperate.DBO().con != null) {
                                TranTxtOut.append("<Vgoogle在线>	" + ((String) languageChsMap.get(j)) + "：\t" + LanguageList.get(i).getLanguage()[j]);
                            } else {
                                TranTxtOut.append("<Vgoogle在线>	" + ((String) languageChsMap.get(j)) + "：\t" + LanguageList.get(i).getLanguage()[j] + " *数据库未连接\r\n");

                            }
                            TranTxtOut.setCaretPosition(TranTxtOut.getText().length());
                        } else {//如果网络断开
                            LanguageList.get(i).getLanguage()[j] = j + "";
                        }

                    }
                }
                temp += "\t" + LanguageList.get(i).getLanguage()[j];
            }
            temp += "\t>\r\n";
            bw.write(temp);
            temp = "<";
        }
        bw.flush();
        bw.close();
        return true;
    }

    //局域网请求翻译结果   String[] s[0]=txt s[1]=chs s[2]=null(译文)  所有的回来的结果放在vector中  如果能匹配则 删除对象 继续执行 如果匹配不成则在一定时间后退出
    public String valnRequest(String chsStr, String languageType) throws Exception {
        String[] strs = {chsStr, languageType, null};
        System.out.println("目标语言：" + languageType);
        TcpClient.clientTrans.requestTrans(strs);
        String str = chsStr + "-" + languageType;
        boolean flag = true;
        String result = null;
        long lastTime = Tool.getTimel();
        long currentTume = 0;
        while (flag) { //如果缓存中不存在 并 且循环标志位true   200ms还没有 则从新发一次 300毫秒后依然没有则不再查询
            Thread.sleep(10);//延时10ms循环一次
            result = Tool.tansBuffer.get(str);
            if (result != null) {//结果已经查询到 则退出
                Tool.tansBuffer.remove(str);
                flag = false;
            }
            currentTume = Tool.getTimel();
            if (result == null && currentTume - lastTime > 200) {//再请求一次
                // TcpClient.clientTrans.requestTrans(strs);
            }
            if (result == null && currentTume - lastTime > 2000) {
                flag = false;
                return -1 + "";
            }
        }
        return result;
    }

    //翻译指定语种
    
    public boolean writeThis(String filePath, ArrayList<RowLanguage> LanguageList, String src, Vector vector) throws Exception {
        //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "unicode"));//ISO-8859-1
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "x-UTF-16LE-BOM"));
        String temp = "<";
        if (TranTxtOut.getText().length() > Integer.MAX_VALUE - 500) {//如果输出框文字太多 则清空
            TranTxtOut.setText("");
            TranTxtOut.removeAll();
        }
        if (LanguageList == null) {
            return false;
        }
        for (int i = 0; i < LanguageList.size(); i++) {
            TranTxtOut.append("<正在翻译>    [" + LanguageList.get(i).getLanguage()[1] + "]\r\n");
            TranTxtOut.setCaretPosition(TranTxtOut.getText().length());
            for (int j = 0; j < LanguageList.get(i).getLanguage().length; j++) {
                if (vector.contains(j)) {  //如果这个语种未翻译 则开始翻译
                    //数据库查询  如果数据库查询不到 则google在线翻译 如果网络未连接 则提示
                    if (LanguageList.get(i).getLanguage()[1] == null) {
                        return false;
                    }
                    if (src != null) {
                        LanguageList.get(i).getLanguage()[j] = queryDataBase(src, LanguageList.get(i).getLanguage()[1], (String) Tool.languageStrMap.get(j));//获得数据库翻译
                        // System.out.println(LanguageList.get(i).getLanguage()[1] + "---数据库翻译后---- " + (String) Tool.languageStrMap.get(j) + "  " + j + " -----：" + LanguageList.get(i).getLanguage()[j]);
                        //判断数据库是否翻译
                    }
                    if (LanguageList.get(i).getLanguage()[j] == null) {//如果未翻译 则google查询
                        //如果网络连接 则google查询
                        if (Tool.internetConState) {//如果网络连接
                            //google查询    
                            System.out.println("google 联网查询 ：" + LanguageList.get(i).getLanguage()[1] + "查询中------------------");

                            LanguageList.get(i).getLanguage()[j] = Tool.Tool().Translate(null, (String) Tool.languageMap.get(j), LanguageList.get(i).getLanguage()[1]);//获得google翻译
                            if (DataBaeOperate.DBO().con != null) {
                                TranTxtOut.append("<google在线>	" + ((String) languageChsMap.get(j)) + "：\t" + LanguageList.get(i).getLanguage()[j]);
                            } else {
                                TranTxtOut.append("<google在线>	" + ((String) languageChsMap.get(j)) + "：\t" + LanguageList.get(i).getLanguage()[j] + " *数据库未连接\r\n");

                            }
                            //是否存在中文对应的google翻译                     //存数据库     通过简体中文和翻译来源查询  languageStrMap.get(j)语言是否已翻译 ，已翻译则不处理，未翻译存入数据库
                            saveToDateBase(LanguageList.get(i).getLanguage()[1], ((String) languageStrMap.get(j)), LanguageList.get(i).getLanguage()[j]);
                        } else if (Tool.valnConState) { //如果伪网络已连接
                            LanguageList.get(i).getLanguage()[j] = valnRequest(LanguageList.get(i).getLanguage()[1], (String) Tool.languageMap.get(j));//局域网请求
                            if (DataBaeOperate.DBO().con != null) {
                                TranTxtOut.append("<Vgoogle在线>	" + ((String) languageChsMap.get(j)) + "：\t" + LanguageList.get(i).getLanguage()[j]);
                            } else {
                                TranTxtOut.append("<Vgoogle在线>	" + ((String) languageChsMap.get(j)) + "：\t" + LanguageList.get(i).getLanguage()[j] + " *数据库未连接\r\n");

                            }
                            TranTxtOut.setCaretPosition(TranTxtOut.getText().length());
                        } else {//如果网络断开
                            LanguageList.get(i).getLanguage()[j] = j + "";
                        }

                    }
                } else {
                    if (j == 1) {
                        LanguageList.get(i).getLanguage()[j] = LanguageList.get(i).getLanguage()[1];
                    } else {
                        LanguageList.get(i).getLanguage()[j] = j + "";
                    }
                }
                temp += "\t" + LanguageList.get(i).getLanguage()[j];
            }
            temp += "\t>\r\n";
            bw.write(temp);
            temp = "<";
        }
        bw.flush();
        bw.close();
        return true;
    }

    //存数据库     通过简体中文和翻译来源查询  languageStrMap.get(j)语言是否已翻译 ，已翻译则不处理，未翻译存入数据库
    //是否存在中文对应的google翻译
    public boolean saveToDateBase(String chsStr, String languageType, String tranStr) throws Exception {
        //DataBaeOperate dbo = new DataBaeOperate();
        DataBaeOperate db = new DataBaeOperate();
        if (DataBaeOperate.DBO().con == null) {//如果数据库未连接
            System.out.println("数据库未连接！！！！！！！！！！！！！！！！！！！！！！！！！！");
        } else {// 如果数据库连接
            if (db.isChsTranExist("google", chsStr)) { //存在google 中的中文
                if (!db.isTranExist("google", chsStr, languageType)) {//如果不存在多国语言翻译 则写入数据库
                    //根据翻译来源和中文 存某语种的翻译入数据库
                    db.saveTranDateBase("google", chsStr, languageType, tranStr);
                    TranTxtOut.append("--【数据库未存】-【已收录】\r\n");
                } else {
                    TranTxtOut.append("--【数据库已存】\r\n");
                }
            } else {//如果不存在google 来源的中文
                //先写入中文  
                db.saveChsSrc("google", chsStr);
                //写入对应的多国语言
                db.saveTranDateBase("google", chsStr, languageType, tranStr);
                TranTxtOut.append("--【数据库未存】-【已收录】\r\n");
            }
        }
        TranTxtOut.setCaretPosition(TranTxtOut.getText().length());
        return true;
    }

    //批量修改后更新配件库
    public boolean changeToDateBase(String chsStr, String languageType, String tranStr) throws Exception {
        //DataBaeOperate dbo = new DataBaeOperate();
        if (DataBaeOperate.DBO().con == null) {//如果数据库未连接          
            System.out.println("数据库未连接！！！！！！！！！！！！！！！！！！！！！！！！！！");
            TranTxtOut.append("数据库未连接！！！！！！！！！");
            return false;
        } else {// 如果数据库连接
            if (DataBaeOperate.DBO().isChsTranExist("配件库", chsStr)) { //存在配件库中的中文
                System.out.println("配件库中存在中文");
                if (!DataBaeOperate.DBO().isTranExist("配件库", chsStr, languageType)) {//如果不存在多国语言翻译 则写入数据库
                    System.out.println("配件库中不存在:" + languageType + "  写入");
                    //根据翻译来源和中文 存某语种的翻译入数据库
                    DataBaeOperate.DBO().saveTranDateBase("配件库", chsStr, languageType, tranStr);
                } else {
                    //修改数据库
                    System.out.println("配件库中存在:" + languageType + "  修改");
                    DataBaeOperate.DBO().saveTranDateBase("配件库", chsStr, languageType, tranStr);
                }
            } else {//如果不存在google 来源的中文
                //先写入中文  
                DataBaeOperate.DBO().saveChsSrc("google", chsStr);
                //写入对应的多国语言
                System.out.println("写入中文和多国语言");
                DataBaeOperate.DBO().saveTranDateBase("配件库", chsStr, languageType, tranStr);
            }
        }
        TranTxtOut.append("【配件库】-【已更新】\r\n");
        return true;
    }

    //存数据库     通过简体中文和翻译来源查询  languageStrMap.get(j)语言是否已翻译 ，已翻译则不处理，未翻译存入数据库
    //是否存在中文对应的google翻译
    public String queryDataBase(String src, String chsStr, String languageType) throws Exception {
        String tranStr = null;
        DataBaeOperate db = new DataBaeOperate();
        //DataBaeOperate dbo = new DataBaeOperate();
        if ((tranStr = db.TranSrcLanguageType(src, chsStr, languageType)) == null) {//不存在
//            if (tranStr == null) {
//                TranTxtOut.append("<src：" + src + ">\t" + "未找到\r\n");
//            } else {
//                TranTxtOut.append("<src：" + src + ">\t" + tranStr + "\r\n");
//            }
            //不指定src  指定中文 某语种 翻译是否存在
            tranStr = db.TranLanguageTypeNotSrc(languageType, chsStr);
//            if (tranStr == null) {
//                TranTxtOut.append("<数据库>\t" + "未找到\r\n");
//            } else {
//                TranTxtOut.append("<数据库>\t" + tranStr + "\r\n");
//            }
        }
//        try {
//            if (TranTxtOut.getText().length() > 10000) {
//                TranTxtOut.removeAll();
//            }
//            TranTxtOut.setCaretPosition(TranTxtOut.getText().length());
//        } catch (Exception e) {
//            TranTxtOut.removeAll();
//        }
        return tranStr;
    }
    //google全语种翻译 存入数据库   
    //存数据库     通过简体中文和翻译来源查询  languageStrMap.get(j)语言是否已翻译 ，已翻译则不处理，未翻译存入数据库
    /*
     * param
     * chsStr  简体中文内容
     * languageTypeStr 语种
     * languageTranStr 对应语种的中文翻译内容
     */

    public boolean googleTranSaveDataBase(String chsStr, String langguageTypeStr, String languageTranStr) {
        //查询数据库 翻译是否存在

        //存在 return false

        //不存在 存入数据库 return true
        return false;
    }

    //遍历文件判断查找文字是否存在，如果存在则返回文件路径存储并显示（以便提示用户当前操作的文件，和避免重复查找所有文件），若不存在则提示；
    //可能多个文件中都包含所查找的文字，所以要批量修改  返回所有包含查找文字的所有文件路径
    public ArrayList<String> strFile(String path, String str) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        File f = new File(path);
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (!isLanguageFile(files[i])) {//判断是否为多国语言文件 如果不是
                continue;
            }
            System.out.println(files[i].getPath());
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(files[i]), "UNICODE"));
            String temp;
            while ((temp = br.readLine()) != null) {
                String[] s = temp.split("\\t");
                if (s.length < 15) {
                    continue;
                }
                if (s[2].equals(str)) {
                    System.out.println("找到：" + s[CHS + 1]);
                    list.add(files[i].getPath());
                }
            }
            br.close();
        }
        return list;
    }

    //判断是否为多国语言文件
    public boolean isLanguageFile(File f) throws Exception {
        if (f.isDirectory()) {
            return false;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UNICODE"));
        String temp;
        temp = br.readLine();
        br.close();
        String[] s = temp.split("\\t");

        if (s.length == 17 && s[0].equals("<") && s[16].equals(">") || s.length>17) {//全语种
            charSet = "UNICODE";
            return true;
        } else if (s.length == 3 && s[0].equals("<") && s[2].equals(">")) {//纯汉字如 <	启动	> UNICODE 编码
            charSet = "UNICODE";
            return true;
        } else {//纯汉字如 <	启动	>
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
            temp = br.readLine();
            br.close();
            s = temp.split("\\t");
            if (s.length == 3 && s[0].equals("<") && s[2].equals(">")) {
                charSet = "gbk";
                return true;
            }
        }
        System.out.println("不是多国语言" + f.getPath());
        return false;
    }

    // 通过存储的文件重新读写需查找的文字，并修改其对应语种内容；其它内容读取不变，删除原文件，创建修改后的文件，文件名不变。->>修改成功
    //默认认为一个文件只有一个查找的文件，如：一个文件里面只有一个星期一
    /* param
     * list:包含查找文字的文件路径列表
     * langguage:修改哪个语言的翻译
     * str:修改后的文字内容
     */
    public boolean changeStr(ArrayList<String> list, String str, int langguage, String toStr) throws Exception {
        ArrayList<RowLanguage> LanguageList = new ArrayList<RowLanguage>();//行语言列表
        for (int i = 0; i < list.size(); i++) {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(list.get(i)), "UNICODE"));
            String temp;
            while ((temp = br.readLine()) != null) {
                String[] s = temp.split("\\t");
                if (s.length < 15) {
                    continue;
                }
                // System.out.println("多国语言\\t分割成："+s.length+"份");
                if (str.equals(s[2])) {   //2为简体中文 对应  temp.split("\\t"); 而非language数组
                    s[langguage + 1] = toStr;
                    System.out.println("修改后的文字" + toStr);
                }
                LanguageList.add(new RowLanguage(new String[]{s[1], s[2], s[3], s[4],
                    s[5], s[6], s[7], s[8], s[9], s[10],
                    s[11], s[12], s[13], s[14],
                    s[15]})); //创建一行语言类 并且所有赋值           
            }
            br.close();
            writeChange(list.get(i), LanguageList);//删除原有文件 创建修改后的新文件
            LanguageList.clear();
        }
        return false;
    }

    //修改查找的文字
    /*
     * param
     * filePath：文件路径
     * LanguageList：翻译语言 rowLanguage的列表
     */
    public boolean writeChange(String filePath, ArrayList<RowLanguage> LanguageList) throws Exception {
        File file = new File(filePath);
        if (file.delete()) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败！");
            return false;
        };//删除原有文件 创建新文件
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "x-UTF-16LE-BOM"));
        String temp = "<";
        for (int i = 0; i < LanguageList.size(); i++) {
            for (int j = 0; j < LanguageList.get(i).getLanguage().length; j++) {
                // LanguageList.get(i).getLanguage()[j] = Tool.Tool().Translate(null, (String) Tool.languageMap.get(j), LanguageList.get(i).getLanguage()[1]);     
                temp += "\t" + LanguageList.get(i).getLanguage()[j];
            }
            temp += "\t>";
            bw.write(temp);
            bw.newLine();
            temp = "<";
        }
        bw.flush();
        bw.close();
        return true;
    }

    /* 翻译指定语言   进度：未完成
     * param
     * filePath：文件写入路径
     * LanguageList：翻译语言 rowLanguage的列表
     * languages 指定翻译语种，如果为null的时候则翻译所有未翻译的语言
     */
    public boolean writeTranslate(String filePath, ArrayList<RowLanguage> LanguageList, int[] languages) throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "x-UTF-16LE-BOM"));
        String temp = "<";
        for (int i = 0; i < LanguageList.size(); i++) {
            for (int j = 0; j < LanguageList.get(i).getLanguage().length; j++) {
//                if (LanguageList.get(i).getLanguage()[j] == null) {
//                    LanguageList.get(i).getLanguage()[j] = Tool.Tool().Translate(null, (String) Tool.languageMap.get(j), LanguageList.get(i).getLanguage()[1]);
//                }
                if (languages == null) {//翻译所有语言
//                    if (LanguageList.get(i).getLanguage()[j].) {//如果未翻译 ，如果是纯数字 则表示未翻译
//                          //数据库查找翻译
//                      //******
//                          //google                        
//                         LanguageList.get(i).getLanguage()[j] = Tool.Tool().Translate(null, (String) Tool.languageMap.get(j), LanguageList.get(i).getLanguage()[1]);
//                    }
                } else {//翻译指定语言
                    for (int n = 0; n < languages.length; n++) {
                        //数据库查找翻译
                        //*********
                        //google翻译
                        LanguageList.get(i).getLanguage()[languages[n]] = Tool.Tool().Translate(null, (String) Tool.languageMap.get(languages[n]), LanguageList.get(i).getLanguage()[1]);
                    }
                }
                temp += "\t" + LanguageList.get(i).getLanguage()[j];
            }
            temp += "\t>";
            bw.write(temp);
            bw.newLine();
            temp = "<";
        }
        bw.flush();
        bw.close();
        return true;
    }

    //获得所有多国语言样本已翻译过的文字  导入数据库用
    public ArrayList<RowLanguage> getTranslateAllStr(File f) throws Exception {
        if (!isLanguageFile(f)) {
            return null;
        }
        ArrayList<RowLanguage> LanguageList = new ArrayList<RowLanguage>();//行语言列表
        System.out.println(f.getPath());
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UNICODE"));
        String temp;
        String[] s = null;
        while ((temp = br.readLine()) != null) {
            s = temp.split("\\t");
            for (int m = 1; m < s.length; m++) {  //s[1]=">"s[16]=">"
                if (isNum(s[m])) {   //如果s[i]为数字 则赋值为空
                    s[m] = null;
                }
            }
            if (s.length < 15) {
                continue;
            }
            LanguageList.add(new RowLanguage(new String[]{s[1], s[2], s[3], s[4],
                s[5], s[6], s[7], s[8], s[9], s[10],
                s[11], s[12], s[13], s[14],
                s[15]})); //创建一行语言类 并且所有赋值   
        }
        br.close();
        return LanguageList;
    }

    //判断是否为纯数字
    public boolean isNum(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    //判断是否为字母
    public boolean isChar(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        // System.out.println(pattern.matcher(str).matches());
        return pattern.matcher(str).matches();
    }

    //数据库翻译 单线程                test
    public void databaseTran(String dirPath, String src, int TranScope, Vector vector) {
        File f = new File(dirPath);//通过目录选择获得目录下所有文件
        File[] files = f.listFiles();
        int fileCount = files.length; //获得文件个数来分线程     
        if (fileCount == 0) {//如果无文件 退出翻译
            return;
        }
        File[] fileThread = null;
        String filePath = f.getPath() + "\\翻译后\\";
        File dir = new File(filePath);
        if (!dir.exists()) { //如果文件不存在
            dir.mkdirs();
        }
        new GoogleTranThread(filePath, files, src, TranScope, vector).start();//启动线程
    }
    //用多线程来翻译多文件  最多分五个线程

    public void GooleTranAll(String dirPath, String src, int TranScope, Vector vector) throws Exception {
        File f = new File(dirPath);//通过目录选择获得目录下所有文件
        File[] files = f.listFiles();
        int fileCount = files.length; //获得文件个数来分线程
        int quo = fileCount / Tool.onLineTranThreadCount;//商
        int sur = fileCount % Tool.onLineTranThreadCount;//余数 
        int ThreadCount = Tool.onLineTranThreadCount;
        if (fileCount == 0) {//如果无文件 退出翻译
            return;
        }
        if (sur != 0) {//如果余数不为零 且商不为零
            ThreadCount = Tool.onLineTranThreadCount + 1;
        }
        if (quo == 0) {//如果商为零 线程数为余数
            ThreadCount = sur;
        }
        File[] fileThread = null;
        String filePath = f.getPath() + "\\翻译后\\";
        File dir = new File(filePath);
        if (!dir.exists()) { //如果文件不存在
            dir.mkdirs();
        }
        TranTxtOut.append(" >>文件存放路径：" + filePath + "\r\n");
        for (int i = 0; i < ThreadCount; i++) {
            if (quo == 0) {  //如果商为零
                fileThread = new File[]{files[i]};
                //new GoogleTranThread(filePath, fileThread).start();//启动线程
            } else {      //商不为零
                if (sur == 0) {//如果余数为零
                    fileThread = new File[quo];
                    for (int n = 0; n < quo; n++) {
                        fileThread[n] = files[i * quo + n];
                    }

                } else {//余数不为零
                    if (i == ThreadCount - 1) {//如果是最后一个线程
                        fileThread = new File[sur];
                        for (int n = 0; n < sur; n++) {
                            fileThread[n] = files[i * quo + n];
                        }
                    } else {
                        fileThread = new File[quo];
                        for (int n = 0; n < quo; n++) {
                            fileThread[n] = files[i * quo + n];
                        }
                    }
                }
            }
            new GoogleTranThread(filePath, fileThread, src, TranScope, vector).start();//启动线程
        }

//        if (!f.exists()) {
//            f.mkdirs();
//        }
//        System.out.println(f.getPath());
    }

    //翻译线程
    class GoogleTranThread extends Thread {

        String filePath = null; //新文件缩放置目录
        File[] files = null;
        String src = null;//字典库源
        int TranScope = -1;//翻译范围
        Vector vector = null;

        public GoogleTranThread(String filePath, File[] files, String src, int TranScope, Vector vector) {
            this.filePath = filePath;
            this.files = files;
            this.src = src;
            this.TranScope = TranScope;
            this.vector = vector;
        }

        public void run() {
            String fileName = null;
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {//如果是文件夹则跳过
                    continue;
                }
                try {
                    if (!isLanguageFile(files[i])) {//判断是否为多国语言文件 如果不是 跳过循环
                        continue;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    TranTxtOut.setText("\r\n！！！！！！！！！！！！异常！！！！！！！！！！！！！\r\n\r\n" + ex.getMessage() + "\r\n\r\n！！！！！！！！！！！！！！！！\r\n");

                }
                if (files[i] == null) {//如果文件为空 退出循环
                    return;
                }
                fileName = files[i].getPath();
                TranTxtOut.append("<打开文件>    [" + fileName + "]\r\n");
                fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
                System.out.println("fileName:" + fileName);
                try {
                    if (TranScope == 0) {//翻译所有的语种
                        Tool t = new Tool();
                        t.write(filePath + "\\" + fileName, Tool.Tool().getLanguageList(files[i].getPath()), src);
                    } else if (TranScope == 1) {//翻译未翻译语种
                        Tool t = new Tool();
                        t.write(filePath + "\\" + fileName, Tool.Tool().getLanguageFilshList(files[i].getPath()), src);
                    } else if (TranScope == -1) {//翻译选中的语种
                        Tool t = new Tool();
                        t.writeThis(filePath + "\\" + fileName, Tool.Tool().getLanguageList(files[i].getPath()), src, vector);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    TranTxtOut.setText("\r\n！！！！！！！！！！！！异常！！！！！！！！！！！！！\r\n\r\n" + ex.getMessage() + "\r\n\r\n！！！！！！！！！！！！！！！！\r\n");
                }
            }
        }
    }

    //网络是否连接
    public boolean isInterntConnectionTest() {
        Socket ss = null;
        try {
            ss = new Socket("www.baidu.com", 80);
        } catch (Exception ex) {
            Tool.internetConState = false;//公共的网络连接状态
            ex.printStackTrace();
            TranTxtOut.setText("\r\n！！！！！！！！！！！！异常！！！！！！！！！！！！！\r\n\r\n" + ex.getMessage() + "\r\n\r\n！！！！！！！！！！！！！！！！\r\n");
            return false;
        }
        System.out.println("网络socket创建成功");
        String inter = null;
        try {
            inter = Tool.Tool().Translate(null, (String) Tool.languageMap.get(0), "网络测试");
            if (inter.equals("http://192.168.0.3/disable/disable.htm")) {//网络未连接
                Tool.internetConState = false;
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            TranTxtOut.setText("\r\n！！！！！！！！！！！！异常！！！！！！！！！！！！！\r\n\r\n" + ex.getMessage() + "\r\n\r\n！！！！！！！！！！！！！！！！\r\n");
        }

        if (ss == null) {
            Tool.internetConState = false;
            return false;
        } else {
            Tool.internetConState = true;
            return true;
        }
    }
    /*
     *     public static HashMap<Long, String> clientBufferMap = new HashMap<Long, String>();//客户端记录
     public static HashMap<Long, MessageDao> msgBufferMap = new HashMap<Long, MessageDao>();//消息缓冲记录
     */
//计时器
    long lastTime = 0;
    long currentTime = 0;
    MessageDao msg = null;
//计时器监听
    public void TimerListerning() {
        Date date = null;
        while (true) {
            try {
                date = new Date();
                currentTime = date.getTime();
                udpClient(); /*客户端!!!!*/
                msgInsureSend();//消息确认发送
                bestServce();  /*选择最优服务*/
                Thread.sleep(1);//1毫秒循环一次
            } catch (Exception ex) {
                //msgBufferMap.clear();//清除所有消息
                ex.printStackTrace();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex1);
                }
                continue;
            }
        }
    }

    //udp客户端数
    public  void udpClient(){
        if (!clientBufferMap.isEmpty()) {//如果 客户端为空 不执行
            Set set1 = clientBufferMap.keySet();//客户端
            Iterator it1 = set1.iterator();
            while (it1.hasNext()) {    //客户端
                lastTime = clientBufferMap.get(it1.next());
                if (currentTime - lastTime > 5 * 60 * 1000) {   //五分钟未再发则移除该客户端
                    clientBufferMap.remove(lastTime);
                }
            }
        }
    }
    
    //消息确认发送
    public void msgInsureSend() throws Exception {
        if (msgBufferMap.isEmpty()) {   //消息
            Set set2 = msgBufferMap.keySet();//普通信息
            Iterator it2 = set2.iterator();
            while (it2.hasNext()) {    //普通信息       需要写个并发保护
                Object o = null;
                try {
                    o = it2.next();
                } catch (Exception e) {
                    it2 = set2.iterator();
                    continue;
                }
                msg = msgBufferMap.get(o);
                lastTime = msg.getMsgmMark();
                if (currentTime - lastTime > 200) {  //如果消息在200毫秒类还在发送 则丢掉该信息
                    System.out.println("删除消息：" + msgBufferMap.remove(lastTime).getPacketID());
                } else if ((currentTime - lastTime) % 50 == 0) {  //二十毫秒重发一次该信息
                    System.out.println("消息重发：msgtype=" + msg.getMsgType());
                    UdpSend.udpSend().againMsg(msg);  //重发信息
                }
            }
        }
    }

    //1s内选定最佳服务器
    public void bestServce() {
        if (Tool.scanServceONOFF) {//选择最优服务
            if (currentTime - scanServceStartTime > 1000) {//1秒内得到最佳服务  超过一秒关闭
                //如果map现在为空 则提示 局域网内无网络服务提供
                if (ServceMap.isEmpty()) {
                    System.out.println("无网络服务提供");
                } else {
                    //挑选最优服务器 并设置服务器ip
                    chooseBaseServce();
                    //连接服务器
                    TcpClient tc = new TcpClient();
                    tc.start();
                }
                //关闭开关 
                Tool.scanServceONOFF = false;
            }
        }
    }

    // 取昵称  返回主机名
    public static String getName() throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        String address = addr.getHostName().toString();//获得本机名称
        return address;
    }

    //获得本地ip
    public static String getLocalHostIp() throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();//获得本机IP
        //  String address = addr.getHostName().toString();//获得本机名称
        return ip;
    }

    //取ip整形  后三段
    public int getIpToInt(String ip) {
        String s[] = ip.split("\\.");
        if (s.length != 4) {
            // System.out.println(s.length);
            return -1;
        }
        //System.out.println(ip + ":  " + Integer.parseInt(s[1]+s[2] + s[3]));
        return Integer.parseInt(s[1] + s[2] + s[3]);
    }

    //取ip整形  后三段
    public Long getIpToLong(String ip) {
        String s[] = ip.split("\\.");
        if (s.length != 4) {
            // System.out.println(s.length);
            return (long) -1;
        }
        //System.out.println(ip + ":  " + Integer.parseInt(s[1]+s[2] + s[3]));
        return Long.parseLong(s[0] + s[1] + s[2] + s[3]);
    }

    //刷新消息缓冲区 //更新消息缓冲区   如果消息未回复则重复发送
    public boolean updataBuffer(MessageDao msg) throws Exception {
        if (isBroadCastIP(msg.getReceiveUser())) {//如果是广播消息则不需要刷新缓冲区
            return false;
        }
        int msgMark = (int) msg.getMsgmMark();//普通消息的消息标示
        if (msg.getMsgType() == MsgType.REPLYTRANSLATE) {   //如果是回复翻译信息 则更新客户端
            //  Long ipMark = getIpToLong(msg.getReceiveUser());//客户端消息标示
            clientBufferMap.put(msg.getReceiveUser(), (new Date()).getTime());
        }
        msgBufferMap.put(msg.getMsgmMark(), msg);
        return true;
    }

    //对方接收消息成功后remove 掉msgBuffer中的数据
    public boolean msgBufferRemoveMsg(MessageDao msg) {
        msgBufferMap.remove(msg.getMsgmMark());
        return true;
    }
    //是否为广播消息

    public boolean isBroadCastIP(String ip) throws Exception {
        return ip.equals(equals(Tool.getBroadCastIP()));
    }

    // 得到广播ip
    public static String getBroadCastIP() throws Exception {
        String ip = getLocalHostIp().substring(0,
                getLocalHostIp().lastIndexOf(".") + 1)
                + "255";
        return ip;
    }

    //客户端个数
    public int getTranslateClinetCount() {
        //  return clientBufferMap.size(); 旧的 udp
        return Tool.clientBuffer.size();//新的tcp
    }

    //打开开关 选择最优服务
    public void openScanServce() {
        Tool.scanServceONOFF = true;
        Tool.scanServceStartTime = Tool.getTimel();
    }

    //保存客户端最少的服务器
    public String chooseBaseServce() {
        Tool.bastServceIp = getBastServceIp(ServceMap);
        return null;
    }

    //获得最佳服务器ip
    public String getBastServceIp(HashMap map) {
        String[] strs = new String[map.size()];
        int[] ints = new int[map.size()];
        Set set = map.keySet();
        Iterator it = set.iterator();
        String temp;
        for (int i = 0; it.hasNext(); i++) {
            temp = (String) it.next();
            strs[i] = temp;
            ints[i] = (int) map.get(temp);
        }
        //排序取最小
        String strTemp;
        int intTemp;
        for (int i = 0; i < map.size() - 1; i++) {
            if (ints[i] < ints[i + 1]) {
                intTemp = ints[i];
                strTemp = strs[i];
                ints[i] = ints[i + 1];
                strs[i] = strs[i + 1];
                ints[i + 1] = intTemp;
                strs[i + 1] = strTemp;
            }
        }
        return strs[map.size() - 1];
    }
//	// 获得机器信息
//	public static String getMachineInfo() {
//		// String manufacturer = null;
//		// String model = null;
//		// String device = null;
//		// int version = 3;
//		// try {
//		//
//		// Class<android.os.Build.VERSION> build_version_class =
//		// android.os.Build.VERSION.class;
//		// // 取得 android 版本
//		// java.lang.reflect.Field field = build_version_class
//		// .getField("SDK_INT");
//		// version = (Integer) field.get(new android.os.Build.VERSION());
//		//
//		// Class<android.os.Build> build_class = android.os.Build.class;
//		// // 取得牌子
//		// java.lang.reflect.Field manu_field = build_class
//		// .getField("MANUFACTURER");
//		// manufacturer = (String) manu_field.get(new android.os.Build());
//		// // 取得型號
//		// java.lang.reflect.Field field2 = build_class.getField("MODEL");
//		// model = (String) field2.get(new android.os.Build());
//		// // 模組號碼
//		// java.lang.reflect.Field device_field = build_class
//		// .getField("DEVICE");
//		// device = (String) device_field.get(new android.os.Build());
//		// // Tool.debug("Build.DEVICE:" + Build.DEVICE);
//		// // Tool.debug("Build.ID:" + Build.ID);
//		// // Tool.debug("Build.DISPLAY:" + Build.DISPLAY);
//		// // Tool.debug("Build.PRODUCT:" + Build.PRODUCT);
//		// // Tool.debug("Build.BOARD:" + Build.BOARD);
//		// // Tool.debug("Build.BRAND:" + Build.BRAND);
//		// // Tool.debug("Build.MODEL:" + );
//		// String xinxi = "android " + "牌子:" + manufacturer + " 型號:" + model
//		// + " SDK版本:" + version + " 模組號碼:" + device;
//		// } catch (Exception e) {
//		// }
//
//		return Build.MODEL;
//	}

    /**
     * 对象转数组
     *
     * @param obj
     * @return
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 数组转对象
     *
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            System.out.println("bytes:" + bytes.toString() + "bytes.length:" + bytes.length);
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception ex) {
            // toObject(bytes);
            ex.printStackTrace();
        }
        return obj;
    }

    // 改昵称
//	public static void changeName(String name,MainActivity mainA) {
//		Writer wb = null;
//		File file = new File(CFGPATH + "/config.data");
//		try {
//			wb = new OutputStreamWriter(new FileOutputStream(file));
//
//			wb.write(name + "\r\n");
//			wb.close();
//			//刷新列表
//			mainA.SAList.get(0).put("name", name+"(自己)");
//			mainA.userList.get(0).setName(name);
//			Message m = new Message();
//			m.what = mainA.LIST_FLUSH;
//			mainA.handler.sendMessage(m);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
    // 取昵称
//	public static String getName() {
//		File dir = new File(CFGPATH);
//		File file = new File(CFGPATH + "/config.data");
//		Writer wb = null;
//		BufferedReader rb = null;
//		String name = null;
//		// 是否插入sd卡
//		boolean sdCardExist = Environment.getExternalStorageState().equals(
//				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
//		try {
//			if (!sdCardExist) {// 如果没有插入sd卡
//				return Tool.getMachineInfo() + ("");
//			}
//			// 如果文件不存在 即为第一次使用
//			if (!dir.exists()) {
//				dir.mkdirs();
//				// 昵称默认为机器型号
//				wb = new OutputStreamWriter(new FileOutputStream(file));
//				wb.write(Tool.getMachineInfo() + "\r\n");
//				wb.close();
//			}
//			// 读取昵称
//			rb = new BufferedReader(new InputStreamReader(new FileInputStream(
//					file)));
//			name = rb.readLine();
//			rb.close();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return name;
//	}
    //返回当前时间 long
    public static long getTimel() {
        return (new Date()).getTime();
    }

    // 聊天记录记录
    public static void chatRecord(String ip, String msg) {
        msg = MsgEx.get(ip) + msg;
        MsgEx.put(ip, msg);
    }

    // 去聊天记录
    public static String getChatRecord(String ip) {
        if (MsgEx == null || MsgEx.isEmpty()) {// 如果为空的话
            return "";
        }
        return MsgEx.get(ip);
    }

    // 时间转换
    public static String getChangeTime(long timel) {
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sfd.format(timel);
    }

//	// 获得初始目录
//	public static String getStartPath(String startPath) {
//		if (startPath == null) {
//			return FileChoose.ADDRESS;
//		} else {
//			File file = new File(startPath);
//			if (file.isFile()) {
//				return file.getParent();
//			} else {
//				return startPath;
//			}
//		}
//	}
    /**
     * 打开文件
     *
     * @param file
     */
//	public static void openFile(Activity a,File file){ 
//	     
//	    Intent intent = new Intent(); 
//	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
//	    //设置intent的Action属性 
//	    intent.setAction(Intent.ACTION_VIEW); 
//	    //获取文件file的MIME类型 
//	    String type = getMIMEType(file); 
//	    //设置intent的data和Type属性。 
//	    intent.setDataAndType(/*uri*/Uri.fromFile(file), type); 
//	    //跳转 
//	    a.startActivity(intent);   
//	     
//	} 
    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。 
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") {
            return type;
        }
        //在MIME和文件类型的匹配表中找到对应的MIME类型。 
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？ 
            if (end.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
            }
        }
        return type;
    }
//	 MIME_MapTable是所有文件的后缀名所对应的MIME类型的一个String数组：
//	 
//	Java代码  <span style="white-space: pre;">    </span>
    private static final String[][] MIME_MapTable = {
        //{后缀名，MIME类型} 
        {".3gp", "video/3gpp"},
        {".apk", "application/vnd.android.package-archive"},
        {".asf", "video/x-ms-asf"},
        {".avi", "video/x-msvideo"},
        {".bin", "application/octet-stream"},
        {".bmp", "image/bmp"},
        {".c", "text/plain"},
        {".class", "application/octet-stream"},
        {".conf", "text/plain"},
        {".cpp", "text/plain"},
        {".doc", "application/msword"},
        {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
        {".xls", "application/vnd.ms-excel"},
        {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
        {".exe", "application/octet-stream"},
        {".gif", "image/gif"},
        {".gtar", "application/x-gtar"},
        {".gz", "application/x-gzip"},
        {".h", "text/plain"},
        {".htm", "text/html"},
        {".html", "text/html"},
        {".jar", "application/java-archive"},
        {".java", "text/plain"},
        {".jpeg", "image/jpeg"},
        {".jpg", "image/jpeg"},
        {".js", "application/x-javascript"},
        {".log", "text/plain"},
        {".m3u", "audio/x-mpegurl"},
        {".m4a", "audio/mp4a-latm"},
        {".m4b", "audio/mp4a-latm"},
        {".m4p", "audio/mp4a-latm"},
        {".m4u", "video/vnd.mpegurl"},
        {".m4v", "video/x-m4v"},
        {".mov", "video/quicktime"},
        {".mp2", "audio/x-mpeg"},
        {".mp3", "audio/x-mpeg"},
        {".mp4", "video/mp4"},
        {".mpc", "application/vnd.mpohun.certificate"},
        {".mpe", "video/mpeg"},
        {".mpeg", "video/mpeg"},
        {".mpg", "video/mpeg"},
        {".mpg4", "video/mp4"},
        {".mpga", "audio/mpeg"},
        {".msg", "application/vnd.ms-outlook"},
        {".ogg", "audio/ogg"},
        {".pdf", "application/pdf"},
        {".png", "image/png"},
        {".pps", "application/vnd.ms-powerpoint"},
        {".ppt", "application/vnd.ms-powerpoint"},
        {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
        {".prop", "text/plain"},
        {".rc", "text/plain"},
        {".rmvb", "audio/x-pn-realaudio"},
        {".rtf", "application/rtf"},
        {".sh", "text/plain"},
        {".tar", "application/x-tar"},
        {".tgz", "application/x-compressed"},
        {".txt", "text/plain"},
        {".wav", "audio/x-wav"},
        {".wma", "audio/x-ms-wma"},
        {".wmv", "audio/x-ms-wmv"},
        {".wps", "application/vnd.ms-works"},
        {".xml", "text/plain"},
        {".z", "application/x-compress"},
        {".zip", "application/x-zip-compressed"},
        {"", "*/*"}
    };          
}
