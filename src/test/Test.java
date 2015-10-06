/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import GUI.CodeCreate.CCTool;
import com.chenli.dao.RowLanguage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import tool.Tool;

/**
 *
 * @author Administrator
 */
public class Test {

    ArrayList<RowLanguage> LanguageList = new ArrayList<RowLanguage>();

    public static void main(String[] args) {
        //  RowLanguage rowL=new RowLanguage();
        // String[] str=rowL.getLanguage();
        Test test = new Test();
        try {
            // t.write("F:\\多国语言样本\\chenli.txt",  t.getLanguageList("F:\\多国语言样本\\zhmain.txt"));
            //Tool.Tool().getLanguageList("F:\\多国语言样本\\appIpod.txt");
//            ArrayList<String> strs= Tool.Tool().strFile("F:\\多国语言样本","星期一");
//            Tool.Tool().changeStr(strs,"星期一",Tool.CHR+1,"星期一");
            //   DataBaeOperate.DBO().isChsTranExist("google", "星期一");
            //  DataBaeOperate.DBO().saveChsSrc("google", "星期一");
            //   DataBaeOperate.DBO().saveTranDateBase("google", "星期一", "ENGLISH", "zzzzzzzzzz");
            //  DataBaeOperate.DBO().isTranExist("众鸿", "星期一", ((String) languageStrMap.get(Tool.GREECE)));
            //   Test.aa();
//            Test.getLocalHostIp();
//            Test.getName();
//            final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
//            final String info = runtime.getName();
//            final int index = info.indexOf("@");
//            if (index != -1) {
//                final int pid = Integer.parseInt(info.substring(0, index));
//                System.out.println(info);
//                System.out.println(pid);
//            }
//            HashMap<String, Integer> map = new HashMap();
//            map.put("192.168.0.1", 5);
//            map.put("192.168.0.2", 4);
//            map.put("192.168.0.3", 4);
//            map.put("192.168.0.5", 5);
//            map.put("192.168.0.6", 7);
//            System.out.println(test.getBastServceIp(map));
            // System.out.println(map.get(1));
            // test.getIpToInt("192.168.255.255");
          //  System.out.println(test.getHigh(2.5));
          //  test.getFileNameRecord("file_press.bmp");
//            ArrayList<File> list = test.getContianRecordFileList(new File("C:\\Documents and Settings\\Administrator\\桌面\\新建文件夹"));
//            for(int  i=0;i<list.size();i++){
//                System.out.println(list.get(i).getName());
//            }
           test.getXMLFileName("C:\\Documents and Settings\\Administrator\\桌面\\新建文件夹\\chenli_dispressed.png");
        //   test.restorePath("C:\\Documents and Settings\\Administrator\\桌面\\新建文件夹\\chenli.png","ok");
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       //生成xml文件名 
    public String getXMLFileName(String str){
        System.out.println(str.substring(0, str.indexOf("."))+"_xml.xml");
        return str.substring(0, str.indexOf("."))+"_xml.xml";
    }
     //获取源文件名 不含后缀 不含路径
    public String getSimpleFileName(String path){
        System.out.println(path.substring(path.lastIndexOf("\\")+1, path.indexOf(".")));
        return path.substring(path.lastIndexOf("\\")+1, path.indexOf("."));
    }
        //还原文件路径
    public String restorePath(String path,String record){
        System.out.println(path.substring(0, path.indexOf("."))+"_"+record+path.substring(path.indexOf("."),path.length()));
        return path.substring(0, path.indexOf("."))+"_"+record+path.substring(path.indexOf("."),path.length());
    }
        //获得文件原名 如fileName_pressed.bmp 返回filename.bmp
    public String getSrcName(String name){
        String txt;
       txt= name.substring(0, name.lastIndexOf("_"))+name.substring(name.indexOf("."), name.length());
       System.out.println(txt);
        return null;
    }
     //过滤得到**后缀的图片
        FileFilter filter = new FileNameExtensionFilter(
                "bmp", "BMP","png","PNG");
//获取目录所有含标示的图片文件集合
    public ArrayList<File> getContianRecordFileList(File folder){
        if(!folder.isDirectory()){
            return null;
        }
        ArrayList<File> list=new ArrayList<File>();
        File[] files= folder.listFiles((java.io.FileFilter) filter); //获得过滤的文件后缀的文件数组
        //匹配文件
        for(int i=0;i<files.length;i++){
            if(CCTool.recordVector.contains(getFileNameRecord(files[i].getName()))){ //如果文件名存在标示
                list.add(files[i]);
            }
        }         
        return list;
    } 
    
    //文件如file_press.bmp 获得press
    public String getFileNameRecord(String fileName){
       //System.out.println( fileName.substring(fileName.lastIndexOf("_")+1,fileName.indexOf(".")));
        return fileName.substring(fileName.lastIndexOf("_")+1,fileName.indexOf("."));
    }
    
    
    //求高度
    public double getHigh(double t) {
        return 0.5 * 10 * t * t;
    }

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

    //取ip整形  后两段
    public int getIpToInt(String ip) {
        String s[] = ip.split("\\.");
        if (s.length != 4) {
            System.out.println(s.length);
            return -1;
        }
        System.out.println(ip + ":  " + Integer.parseInt(s[1] + s[2] + s[3]));
        return Integer.parseInt(s[1] + s[2] + s[3]);
    }
    // 取昵称  返回主机名

    public static String getName() throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        String address = addr.getHostName().toString();//获得本机名称
        System.out.println("address:" + address);
        return address;
    }

    //获得本地ip
    public static String getLocalHostIp() throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();//获得本机IP
        //  String address = addr.getHostName().toString();//获得本机名称
        System.out.println("ip:" + ip);
        return ip;
    }
    //文件读取 循环取中文作为翻译样本

    public ArrayList<RowLanguage> getLanguageList(String filePath) throws Exception {
        ArrayList<RowLanguage> LanguageList = new ArrayList<RowLanguage>();//行语言列表
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UNICODE"));
        String temp;
        while ((temp = br.readLine()) != null) {
            String[] s = temp.split("\\t");
            LanguageList.add(new RowLanguage(s[1], s[2])); //创建一行语言类 并且中文赋值
            System.out.println(s[1] + "  简体中文：" + s[2]);
        }
        br.close();
        return LanguageList;
    }

    public boolean write(String filePath, ArrayList<RowLanguage> LanguageList) throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UNICODE"));
        String temp = "<";
        for (int i = 0; i < LanguageList.size(); i++) {
            for (int j = 0; j < LanguageList.get(i).getLanguage().length; j++) {
                //test
                if (LanguageList.get(i).getLanguage()[j] == null) {
                    LanguageList.get(i).getLanguage()[j] = Tool.Tool().Translate(null, (String) Tool.languageMap.get(j), LanguageList.get(i).getLanguage()[1]);
                }
                //test end
                temp += "\t" + LanguageList.get(i).getLanguage()[j];
            }
            temp += "\t>";
            bw.write(temp);
            bw.newLine();
            temp = "<";
        }
        bw.flush();
        bw.close();
        return false;
    }

    //转化字符串为十六进制编码
    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        System.out.println("方法一：" + str);
        return "0x" + str;//0x表示十六进制
    }

    public static void show16(String s) {
        //  int i = Integer.parseInt(s);
        Integer in = Integer.valueOf(s, 16);
        System.out.println("方法er：" + in.toString());
    }

    public static void aa() {
        int i = 0x01 << 4;
        System.out.println(i);
    }
}
