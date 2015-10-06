/**
 * Project Name:javaTest        
 * File Name:View.java            
 * Package Name:com.chenli.xmltest           
 * Date:2013-11-10下午11:23:14 
 * Copyright (c) 2013, 185817196@qq.com All Rights Reserved.           
 */
package com.chenli.createcode;

/**
 * ClassName:View <br/>       
 * date: 2013-11-10下午11:23:14 <br/>        
 * @author 陈立      
 * @version  1.0         
 */
public interface View extends Comparable<Object>{
	
	/**
	 * 返回申明代码
	 * @return
	 */
	public String getDeclareCode();
	
	/**
	 * 生成实例化的代码
	 * @return
	 */
	public String getInstanceCode();
	
	
}
