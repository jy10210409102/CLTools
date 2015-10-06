/**
 *
 */
package com.chenli.adb;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * date: 2014-5-7下午7:11:13 <br/>
 *
 * @author zhonghong.ChenLi
 */
public class Operater {

    private Operater() {
    }
    private static final Operater mOperater = new Operater();

    public static Operater getInstance() {
        return mOperater;
    }

    /**
     * 获得所有连接的设备ID
     *
     * @throws Exception
     */
    public String[] getDevicesID() throws Exception {
        String[] cmd;
        String temp = doCmd("adb devices");
        cmd = temp.split("\r\n");
        String[] result = new String[cmd.length - 1];
        for (int i = 1; i < cmd.length; i++) {	//从第二行开始
            result[i - 1] = cmd[i].split("\t")[0];
            //System.out.println(result[i-1] );
        }
        return result;
    }

    /**
     * 获得设备信息
     */
    public String getDeviceName(String deviceId) throws Exception {
        String temp = doCmd("adb -s " + deviceId + " shell \r\n cat /system/build.prop | grep \"product\"");
        temp = temp.split("\r\n")[0];
        temp = temp.substring(temp.indexOf("=") + 1, temp.length());
        //System.out.println("设备名："+temp);
        return temp;
    }

    /**
     * 得到应用包名
     */
    private String getApkPackageName(String apkPath) {
        String path = apkPath;
        String[] messag = AnalysisApk.unZip(path, "C://11");  //解压缓存放在C://11
        //System.out.println(messag[0] + "-------->" + messag[1]);
        return messag[1];
    }

    /**
     * 安装
     */
    public void installApp(String deviceId, String apkPath) throws Exception {
        //先卸载
        doCmd("adb -s " + deviceId + " uninstall " + getApkPackageName(apkPath));
        //安装
        doCmd("adb -s " + deviceId + " install " + apkPath);
    }

    /**
     * 移动文件或目录下的文件 文件拷到设备目录 (不支持中文目录)
     *
     * @throws Exception
     */
    public void moveFile(String deviceId, String filePath, String deviceDir) throws Exception {
        File file = new File(filePath);
        if (file.isDirectory()) {
            deviceDir += filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());
        }
        System.out.println(deviceDir);
        doCmd("adb -s " + deviceId + " push " + filePath + " " + deviceDir);
    }
    
    /**
     * 拷出并打开文件
     * @param filePath 本地路径
     */
    public void outOpenFile(String deviceId ,String filePath ,String deviceDir) throws Exception{
        doCmd("adb -s " + deviceId + " pull " + deviceDir + " " + filePath);
    }

    /**
     * windows 下打开文件
     * @param filePath 文件路径
     */
    public void openFile(String filePath) throws IOException{
       System.out.println("要打开的路径： " + filePath);
       File file = new File(filePath);
       if(file.exists()){
           Runtime.getRuntime().exec("cmd /c start " + filePath);
       }
       
    }
    /**
     * 获得当前目录下所有文件或文件夹
     *
     * @throws Exception
     */
    public String[] getDirFile(String deviceId, String path, ArrayList<String> dirList, ArrayList<String> fileList) throws Exception {
        String temp = doCmd("adb -s " + deviceId + " shell \r\n  ls -l " + path);
        String[] array = temp.split("\r\n");
        dirList.clear();
        fileList.clear();
        for (int i = 0; i < array.length; i++) {
            if (array[i].trim().equals("")) {
                continue;
            }

            if (array[i].charAt(0) == '-') {
                fileList.add(getLSName(array[i]));
                System.out.println("```文件：" + getLSName(array[i]));
            } else /*if (array[i].charAt(0) == 'd')*/ {
                dirList.add(getLSName(array[i]));
                System.out.println("```文件夹：" + getLSName(array[i]));
            }
        }
        return array;
    }

    /**
     * 解析出文件夹或文件名
     */
    private String getLSName(String lsStr) {
//        if(lsStr.length() < 55){
//            return "";
//        }
//        String[] sa = lsStr.substring(55).split(" ");
//        return sa[0];

        String[] sa = lsStr.split(" ");
        if (sa[sa.length - 1].contains("/")) {
            return sa[sa.length - 3];
        }
        return sa[sa.length - 1];
    }

    /**
     * 删除文件或文件夹
     */
    public void delFileOrDir(String deviceId, String devicePath) throws Exception {
        doCmd("adb -s " + deviceId + " shell \r\n rm -rf " + devicePath);
    }

    /**
     * 判断字符串是否含中文字符
     */
    public boolean isHaveChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            String bb = str.substring(i, i + 1);
            boolean cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", bb);
            if (cc) {
                return true;
            }
        }
        return false;
    }
    /**
     * 执行命令
     *
     * @param cmd
     */
    private String doCmd(String cmd) throws Exception {
        String text = "";
        Process process = Runtime.getRuntime().exec(cmd);
        final InputStream is = process.getInputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String tempStr = null;
        while ((tempStr = reader.readLine()) != null) {
            System.out.println("读到的数据:" + tempStr);
            text += tempStr + "\r\n";
        }
        reader.close();
        is.close();
        return text;
    }
}
