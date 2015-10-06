/**
 * Project Name:javaTest        
 * File Name:InitViewDao.java            
 * Package Name:com.chenli.xmltest           
 * Date:2013-11-10下午8:43:42 
 * Copyright (c) 2013, 185817196@qq.com All Rights Reserved.           
 */
package com.chenli.createcode;


/**
 * 存储组件名称和id信息
 * ClassName:ViewDao <br/>       
 * date: 2013-11-10下午8:43:42 <br/>        
 * @author 陈立      
 * @version 1.0         
 */
public class ViewDao implements View{
	/** 组件ID名称*/
	public String id;
	/** 组件类型 如：TextView*/
	public String viewName;
	
	/**
	 * 构造方法
	 * @param id  		组件id名称
	 * @param viewName	组件类型
	 */
	public ViewDao(String id,String viewName) {
		this.id = id;
		this.viewName = viewName;
	}
	
	/** 按组件名排序 先比较首字母 在比较是不是同一组件*/
	@Override
	public int compareTo(Object dao) {
		if(!(dao instanceof ViewDao)){
			throw new IllegalArgumentException("参数类型不对");
		}
		return this.viewName.compareTo(((ViewDao)dao).viewName);
	}
	
	/**
	 * 返回申明代码		格式如 private View id;
	 * @return		生成的声明代码 
	 */
	public String getDeclareCode(){
		if(id==null){
			return "";
		}
		return "private "+this.viewName +" "+this.id+";\r\n";
	}
	
	/**
	 * 生成实例化的代码	格式如：id=(View)findViewById(R.id.id); findViewById(id)
	 * @return		生成的实例代码 
	 */
	public String getInstanceCode(){
		if(id==null){
			return "";
		}
		return "\t"+this.id +" = ("+this.viewName+")findViewById(R.id."+this.id+");\r\n";
	}
}
