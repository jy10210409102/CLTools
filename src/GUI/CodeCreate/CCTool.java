/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.CodeCreate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Administrator
 */
/*
 思路：
 图片名称格式如：
 图片_dispressed.bmp  
 图片_pressed.bmp

 统计 原始“图片”个数 大于等于二 则生成xml文件  
 vector  存标示  如dispressed 、pressed
 map1存有效无效一对标示， 正反标示成对出现
 map2 存放每个标示对应一段<item>代码
 * 
 * 添加规则 只需在配置中添加 recordVector 和 codeMap中 对应添加即可

 */
public class CCTool {

    private static final CCTool ccTool = new CCTool();

    public static CCTool cCT() {
        return ccTool;
    }

    private CCTool() {
    }
    /**
     * **********************************集合创建*******************************************************
     */
    
    /*集合创建*/
    public static Vector<String> recordVector = new Vector<String>(); //存放标示的集合
    public static HashMap<String, String> twainMap = new HashMap<String, String>();//成对标示
    public static HashMap<String, String> codeMap = new HashMap<String, String>();//标示 代码
    public static String photoName = "~！@#￥%……%￥#@！~";//代码标示 当利用代码的时候用正确图片名替换
    /**
     * *********************************配置********************************************
     * 可无限拓展
     */
    
    //按钮状态xml固定代码
    public String buttonXmlStar = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + "<selector xmlns:android=\"http://schemas.android.com/apk/res/android\">";
    public String buttonXmlEnd = "</selector>";
    public String selectorEndWith = "_xml.xml";//生成xml的后缀
    /**
     * 过滤得到**后缀的图片
     */
    FileFilter filter = new FileNameExtensionFilter(
            "bmp", "BMP", "png", "PNG");

    //标示配置 Vector
    static {
        recordVector.add("checked");//state_checked="rtrue"  //多选框选中状态
        recordVector.add("dispressed");//state_pressed="false"  非按下状态
        recordVector.add("pressed");//state_pressed="true"  按下状态
        recordVector.add("disenable");//state_disenable="false"  无效状态
    }

    /**
     * 成对标示配置 Map   先不考虑成对的标示写在一起的问题
     */
    static {
        twainMap.put("dispressed", "pressed");//成对 按下和飞按下的标示
    }

    /**
     * 标示代码配置 Map 加注释
     */
    static {
        codeMap.put("checked", "    <item android:state_checked=\"true\" android:drawable=\"@drawable/" + photoName + "\"></item>");//多选框选中状态
        codeMap.put("disenable", "    <item android:state_enabled=\"false\" android:drawable=\"@drawable/" + photoName + "\"></item>");//无效状态
        codeMap.put("dispressed", "    <item android:state_pressed=\"false\" android:drawable=\"@drawable/" + photoName + "\"></item>");//非按下状态
        codeMap.put("pressed", "    <item android:state_pressed=\"true\" android:drawable=\"@drawable/" + photoName + "\"></item>");//按下状态
      
    }

    /**
     * ************************************功能编写*****************************************
     */
    public static void main(String[] args) {
        try {
            CCTool.ccTool.XMLCreate(new File("C:\\Documents and Settings\\Administrator\\桌面\\新建文件夹"));
        } catch (Exception ex) {
            Logger.getLogger(CCTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //主线

    /**
     * 主线
     * @param file 文件路劲
     * @throws Exception  文件不存在
     */
    public void XMLCreate(File file) throws Exception {
        ArrayList<String> list = getContianRecordFileList(file);
        int count;//计数
        String txt = "";//xml正体文本
        String srcPath;//原路径名
        String filename;//不含后缀不含目录的文件名
        //循环判断并创建xml
        for (int i = 0; i < list.size(); i++) {
            count = 0;
            txt = "";
            for (int n = 0; n < recordVector.size(); n++) {
                //还原成含标示的文件路径 判断文件是否存在
                srcPath = restorePath(list.get(i), recordVector.get(n));
                if (new File(srcPath).exists()) {
                    count++;
                    //合成txt
                    filename = getSimpleFileName(srcPath);
                    txt += codeMap.get(recordVector.get(n)).replaceAll(photoName, filename) + "\r\n";
                }
            }
            if (count >= 2) { //如果有两个以上的规则则生成xml
                txt = buttonXmlStar + "\r\n" + txt + buttonXmlEnd; //组合成xml的内容
                //写成xml文件
                String name = getXMLFileName(list.get(i));//生成xml path
                createXML(name, txt);
            }
        }
    }

    /**
     * 获取目录所有含标示的图片文件集合   文件基类集合  如fileName_pressed.bmp 返回filename.bmp 等的集合
     * @param folder 文件夹
     * @return 文件路径list
     */
    public ArrayList<String> getContianRecordFileList(File folder) {
        if (!folder.isDirectory()) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        File[] files = folder.listFiles(/*(java.io.FileFilter) filter*/); //获得过滤的文件后缀的文件数组
        //匹配文件
        String nameTemp;
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){//如果是文件夹
                continue;
            }
            if (recordVector.contains(getFileNameRecord(files[i].getName()))) { //如果文件名存在标示
                //获取原始文件名 如 a_pressed.bmp>> a.bmp
                nameTemp = getSrcName(files[i].getPath());
                if (!list.contains(nameTemp)) {//如果列表不存在此原始图片 则添加
                    list.add(nameTemp);
                }

            }
        }
        return list;
    }

    /**
     * 生成对应的xml文件
     */
    public boolean createXML(String name, String txt) throws Exception {
        OutputStreamWriter o = new OutputStreamWriter(new FileOutputStream(new File(name)));
        o.write(txt);//小文件 直接写了
        o.flush();
        o.close();
        return false;
    }

    //文件如file_press.bmp 获得press
    public String getFileNameRecord(String fileName) {
        return fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("."));
    }

    //获得文件原名 如fileName_pressed.bmp 返回filename.bmp
    public String getSrcName(String name) {
        return name.substring(0, name.lastIndexOf("_")) + name.substring(name.lastIndexOf("."), name.length());
    }

    /**
     * 还原文件路径
     * @param path 路径
     * @param record 标记
     * @return  还原文件路径
     */
    public String restorePath(String path, String record) {
        return path.substring(0, path.lastIndexOf(".")) + "_" + record + path.substring(path.lastIndexOf("."), path.length());
    }

    /**
     * 获取源文件名 不含后缀 不含路径
     * @param path 路径
     * @return 文件名
     */
    public String getSimpleFileName(String path) {
        return path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
    }

    /**
     * 生成xml文件名 
     * @param str 文件名
     * @return 含后缀的文件名
     */
    public String getXMLFileName(String str) {
        return str.substring(0, str.lastIndexOf(".")) + selectorEndWith;
    }

    /**
     * show 规则
     * @return 规则内容
     */
    public String showRule() {
        String txt = "";
        for (int i = 0; i < recordVector.size(); i++) {
            txt += "      图片次后缀：" + recordVector.get(i) + "\r\n"
                    + "对应代码：" + codeMap.get(recordVector.get(i)).replaceAll(photoName, "\"photoName\"") + "\r\n\r\n";
        }
        txt += "\r\n      可以简单加规则，欢迎加规则·····";
        return txt;
    }
}
