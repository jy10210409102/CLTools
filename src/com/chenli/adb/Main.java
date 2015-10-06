/**
 * 
 */
package com.chenli.adb;

import java.util.ArrayList;

/**
 * @author chenli
 */
public class Main {

	public static void main(String[] args) throws Exception {
		Operater mOperater = Operater.getInstance();
		String[] devices = mOperater.getDevicesID();  //获得设备id
		/*
		for(int i=0 ; i < devices.length ; i++){
			System.out.println("设备ID：" + devices[i]);
			System.out.println("设备名称" + mOperater.getDeviceName(devices[i]));////获得设备名称
		}
		
		System.out.println("安装中····");
		mOperater.installApp(devices[1], "E:\\zuiSO文件\\zuiserver.apk");
		System.out.println("安装完成！");
		
		System.out.println("开始移动文件····");
		//mOperater.moveFile(devices[1], "E:\\zuiSO文件\\zuiserver.apk", "/mnt/sdcard/ZuiLauncher/data");
		mOperater.moveFile(devices[1], "E:\\sscom", "/mnt/sdcard/tencent");
		System.out.println("移动文件完成！");
		*/
		
		
		ArrayList<String> dirList = new ArrayList<String>();
		ArrayList<String> fileList = new ArrayList<String>();
		mOperater.getDirFile(devices[1], "/mnt/sdcard/ZuiLauncher/data", dirList, fileList);
		for(String str : dirList){
			System.out.println("文件夹： " + str);
		}
		for(String str : fileList){
			System.out.println("文件： " + str);
		}
		
		
	}

}
