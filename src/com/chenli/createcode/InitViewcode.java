/**
 * Project Name:javaTest        
 * File Name:InitView.java            
 * Package Name:com.chenli.xmltest           
 * Date:2013-11-10下午8:14:43 
 * Copyright (c) 2013, 185817196@qq.com All Rights Reserved.           
 */
package com.chenli.createcode;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ClassName:InitView <br/>       
 * date: 2013-11-10下午8:14:43 <br/>        
 * @author 陈立      
 * @version 1.0          
 */
public class InitViewcode {
	/** 存放组件信息的集合对象 ArrayList*/
	private ArrayList<View> ViewList=null;
	
	public  InitViewcode(){
		ViewList =new ArrayList<View>();
	}
	
	/**
	 * 添加组件
	 * @param View View对象
	 */
	public void addView(View View){
		ViewList.add(View);
	}
	
	/**
	 * 生成组件代码
	 * @return 可以用的源码
	 */
	public String getCode(){
		String code="";
		Collections.sort(ViewList);
		code=getDeclareCode()+getInstanceCode();
		return code;
	}
	
	/**
	 * 生成声明代码		
	 * @return 代码
	 */
	private String getDeclareCode(){
		String declare ="";
		for(View vd:ViewList){
			declare+=vd.getDeclareCode();
		}
		return declare;
	}
	/**
	 * 生成实例化的代码 
	 * @return 代码
	 */
	private String getInstanceCode(){
		String instance="";
		for(View vd:ViewList){
			instance+=vd.getInstanceCode();
		}
		return "private void initView(){ \r\n"+instance+"\r\n}";
	}
}
