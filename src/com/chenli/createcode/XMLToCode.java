/**
 * Project Name:javaTest        
 * File Name:XMLTest.java            
 * Package Name:com.chenli.xmltest           
 * Date:2013-11-10下午6:26:48 
 * Copyright (c) 2013, 185817196@qq.com All Rights Reserved.           
 */
package com.chenli.createcode;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ClassName:XMLToCode <br/>
 * date: 2013-11-10下午6:26:48 <br/>
 * @author 陈立
 * @version 1.0
 */
public class XMLToCode {


	
	/**
	 * 返回 InitViewcode对象
	 * @param String XML文件的字符串内容
	 * @return InitViewcode对象
	 */
	public InitViewcode read(String txt) throws Exception {
		InputStream inputStream = StringTOInputStream(txt);
		return read(inputStream);
	}
	
	/**
	 * 返回 InitViewcode对象
	 * @param file XML文件
	 * @return InitViewcode对象
	 */
	public InitViewcode read(File file) throws Exception {
		InputStream inputStream = new FileInputStream(file);
		return read(inputStream);
	}
	
	/**
	 * 返回 InitViewcode对象
	 * @param inputStream 布局文件内容的InputStream 对象
	 * @return InitViewcode对象
	 */
	private InitViewcode read(InputStream inputStream) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		InitViewcode initViewcode = new InitViewcode();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(inputStream);
			// root <university>
			Element root = doc.getDocumentElement();
			if (root == null)
				return null;
			System.out.println(root.getAttribute("name"));
			// all college node
			NodeList collegeNodes = root.getChildNodes();
			if (collegeNodes == null)
				return null;
				recursion(collegeNodes,initViewcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return initViewcode;
	}

	/**
	 * 递归添加所有属性值到InitViewcode中
	 * @param nodeList NodeList对象
	 * @param initViewcode initViewcode生成代码的对象 用来添加View对象
	 * @return Node
	 */
	private  void recursion(NodeList nodeList, InitViewcode initViewcode){
		if (nodeList == null){
			return ;
		}else{
			for(int i=0;i<nodeList.getLength();i++){
				recursion(nodeList.item(i).getChildNodes(),initViewcode);
			}
			for (int n = 0; n < nodeList.getLength(); n++) {
				Node college = nodeList.item(n);
				if (college != null && college.getNodeType() == Node.ELEMENT_NODE) {
					String viewName = college.getNodeName();
					String viewId = null;
					if ((college.getAttributes().getNamedItem("android:id")) != null) {
						viewId = college.getAttributes().getNamedItem("android:id").getNodeValue().substring(5);
					}
					View viewDao = new ViewDao(viewId, viewName);
					initViewcode.addView(viewDao);
				}
			}
		}
	}
	
	/**
	 * 将String转换成InputStream
	 * @param str  String对象
	 * @return InputStream对象
	 */
	public InputStream StringTOInputStream(String str) throws Exception {

		ByteArrayInputStream is = new ByteArrayInputStream(
				str.getBytes("ISO-8859-1"));
		return is;
	}
	
	/**
	 * 打印示例
	 */
	public void showExample() throws Exception{
		System.out.println("布局文件源码***************************************\r\n\r\n"+str+"\r\n\r\n");
		System.out.println("布局文件源码 end***************************************\r\n\r\n\r\n\r\n");
		System.out.println("生成的源代码***************************************\r\n\r\n");
		System.out.println( read(str2).getCode()+"\r\n\r\n");
		System.out.println("生成的源代码 end***************************************\r\n\r\n");
		
	}

	/** 测试txt*/
	private String str = "<FrameLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
			+ "    android:layout_width=\"fill_parent\"\n"
			+ "    android:layout_height=\"fill_parent\"\n"
			+ "    android:background=\"@drawable/big4\" >\n"
			+ "\n"
			+ "    <LinearLayout\n"
			+ "        android:id=\"@+id/gllinear\"\n"
			+ "        android:layout_width=\"fill_parent\"\n"
			+ "        android:layout_height=\"400dp\"\n"
			+ "        android:orientation=\"vertical\" >\n"
			+ "    </LinearLayout>\n"
			+ "\n"
			+ "    <TextView\n"
			+ "        android:id=\"@+id/nameid\"\n"
			+ "        android:layout_width=\"wrap_content\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:layout_marginLeft=\"480dp\"\n"
			+ "        android:layout_marginTop=\"350dp\"\n"
			+ "        android:text=\"TTTT\"\n"
			+ "        android:textColor=\"@android:color/white\"\n"
			+ "        android:textSize=\"35dp\" />\n"
			+ "\n"
			+ "    <Button\n"
			+ "        android:id=\"@+id/test\"\n"
			+ "        android:layout_width=\"wrap_content\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:layout_marginLeft=\"480dp\"\n"
			+ "        android:layout_marginTop=\"400dp\"\n"
			+ "        android:text=\"test\" />\n"
			+ "\n"
			+ "    <LinearLayout\n"
			+ "        android:id=\"@+id/gllinear\"\n"
			+ "        android:layout_width=\"fill_parent\"\n"
			+ "        android:layout_height=\"400dp\"\n"
			+ "        android:orientation=\"vertical\" >\n"
			+ "    </LinearLayout>\n"
			+ "\n"
			+ "    <TextView\n"
			+ "        android:layout_width=\"wrap_content\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:layout_marginLeft=\"480dp\"\n"
			+ "        android:layout_marginTop=\"350dp\"\n"
			+ "        android:text=\"TTTT\"\n"
			+ "        android:textColor=\"@android:color/white\"\n"
			+ "        android:textSize=\"35dp\" />\n"
			+ "\n"
			+ "    <Button\n"
			+ "        android:id=\"@+id/test\"\n"
			+ "        android:layout_width=\"wrap_content\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:layout_marginLeft=\"480dp\"\n"
			+ "        android:layout_marginTop=\"400dp\"\n"
			+ "        android:text=\"test\" />\n"
			+ "\n"
			+ "    <TextView\n"
			+ "        android:id=\"@+id/test555\"\n"
			+ "        android:layout_width=\"wrap_content\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:layout_marginLeft=\"480dp\"\n"
			+ "        android:layout_marginTop=\"350dp\"\n"
			+ "        android:text=\"TTTT\"\n"
			+ "        android:textColor=\"@android:color/white\"\n"
			+ "        android:textSize=\"35dp\" />\n"
			+ "\n"
			+ "    <Button\n"
			+ "        android:id=\"@+id/test11\"\n"
			+ "        android:layout_width=\"wrap_content\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:layout_marginLeft=\"480dp\"\n"
			+ "        android:layout_marginTop=\"400dp\"\n"
			+ "        android:text=\"test\" />\n"
			+ "\n"
			+ "    <LinearLayout\n"
			+ "        android:id=\"@+id/gllinesssar\"\n"
			+ "        android:layout_width=\"fill_parent\"\n"
			+ "        android:layout_height=\"400dp\"\n"
			+ "        android:orientation=\"vertical\" />\n"
			+ "\n"
			+ "    <Button\n"
			+ "        android:id=\"@+id/test16661\"\n"
			+ "        android:layout_width=\"wrap_content\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:layout_marginLeft=\"480dp\"\n"
			+ "        android:layout_marginTop=\"400dp\"\n"
			+ "        android:text=\"test\" />\n" + "\n" + "</FrameLayout>";
	
	String str2 = "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
			+ "    xmlns:tools=\"http://schemas.android.com/tools\"\n"
			+ "    xmlns:zhongh=\"http://schemas.android.com/apk/res/com.zhonghong.radio\"\n"
			+ "    android:layout_width=\"match_parent\"\n"
			+ "    android:layout_height=\"match_parent\"\n"
			+ "    android:background=\"@drawable/radio_background\" >\n"
			+ "\n"
			+ "    <!-- RADIO上条 -->\n"
			+ "\n"
			+ "    <com.zhongh.home.TopTitle\n"
			+ "        android:id=\"@+id/title\"\n"
			+ "        android:layout_width=\"match_parent\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:layout_alignParentTop=\"true\"\n"
			+ "        zhongh:moreid=\"1\"\n"
			+ "        zhongh:returnid=\"0\"\n"
			+ "        zhongh:text=\"Radio\" />\n"
			+ "    <!--  -->\n"
			+ "  <FrameLayout\n"
			+ "        android:id=\"@+id/cd_framelayout\"\n"
			+ "        android:layout_width=\"match_parent\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:layout_below =\"@id/title\" >\n"
			+ "	  <RelativeLayout\n"
			+ "            android:id=\"@+id/radio_relativelayoutmidd\"\n"
			+ "            android:layout_width=\"match_parent\"\n"
			+ "            android:layout_height=\"wrap_content\" >\n"
			+ "    <!-- 收音机中间页面  LAYOUT -->\n"
			+ "    <com.zhonghong.radio.RadioMiddle\n"
			+ "        android:id=\"@+id/radio_middle\"\n"
			+ "        android:layout_width=\"match_parent\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "      />\n"
			+ "\n"
			+ "    \n"
			+ "    \n"
			+ "    <!-- 功能按键 Layout -->\n"
			+ "    <LinearLayout\n"
			+ "        android:id=\"@+id/cd_bottom_buts\"\n"
			+ "        android:layout_alignParentBottom=\"true\"\n"
			+ "        android:layout_width=\"match_parent\"\n"
			+ "        android:layout_height=\"wrap_content\"\n"
			+ "        android:orientation=\"horizontal\" >\n"
			+ "\n"
			+ "        \n"
			+ "        <!-- 左下图片 -->\n"
			+ "        <ImageView\n"
			+ "            android:layout_width=\"wrap_content\"\n"
			+ "            android:layout_height=\"wrap_content\"\n"
			+ "            android:background=\"@drawable/cd_leftbutoom\" />\n"
			+ "\n"
			+ "        \n"
			+ "        <!-- 上一曲 -->\n"
			+ "        <com.zhongh.myself.MyButtonB\n"
			+ "            android:id=\"@+id/radio_pre_but\"\n"
			+ "            android:layout_width=\"wrap_content\"\n"
			+ "            android:layout_height=\"wrap_content\"\n"
			+ "            android:textColor=\"#ffffff\"\n"
			+ "            zhongh:src=\"@drawable/radio_pre_xml\"\n"
			+ "            zhongh:text=\"上一曲\" />\n"
			+ "    \n"
			+ "        <!-- 步退-->\n"
			+ "        <com.zhongh.myself.MyButtonB\n"
			+ "            android:id=\"@+id/radio_step_pre_but\"\n"
			+ "            android:layout_width=\"wrap_content\"\n"
			+ "            android:layout_height=\"wrap_content\"\n"
			+ "            android:textColor=\"#ffffff\"\n"
			+ "            zhongh:src=\"@drawable/radio_pacepre_xml\"\n"
			+ "            zhongh:text=\"步退\" />\n"
			+ "\n"
			+ "        \n"
			+ "        <!-- 波段 -->\n"
			+ "        <com.zhongh.myself.MyButtonB\n"
			+ "              android:id=\"@+id/radio_band_but\"\n"
			+ "            android:layout_width=\"wrap_content\"\n"
			+ "            android:layout_height=\"wrap_content\"\n"
			+ "            android:textColor=\"#ffffff\"\n"
			+ "            zhongh:src=\"@drawable/radio_band_xml\"\n"
			+ "            zhongh:text=\"调频\" />\n"
			+ "\n"
			+ "        \n"
			+ "        <!-- 步进 -->\n"
			+ "        <com.zhongh.myself.MyButtonB\n"
			+ "              android:id=\"@+id/radio_step_next_but\"\n"
			+ "            android:layout_width=\"wrap_content\"\n"
			+ "            android:layout_height=\"wrap_content\"\n"
			+ "            android:textColor=\"#ffffff\"\n"
			+ "            zhongh:src=\"@drawable/radio_play_xml\"\n"
			+ "            zhongh:text=\"步进\" />\n"
			+ "\n"
			+ "        \n"
			+ "        <!-- 下一曲 -->\n"
			+ "        <com.zhongh.myself.MyButtonB\n"
			+ "              android:id=\"@+id/radio_next_but\"\n"
			+ "            android:layout_width=\"wrap_content\"\n"
			+ "            android:layout_height=\"wrap_content\"\n"
			+ "            android:textColor=\"#ffffff\"\n"
			+ "            zhongh:src=\"@drawable/radio_next_xml\"\n"
			+ "            zhongh:text=\"下一曲\" />\n"
			+ "\n"
			+ "        \n"
			+ "        <!-- 右下图片 -->\n"
			+ "        <ImageView\n"
			+ "            android:layout_width=\"wrap_content\"\n"
			+ "            android:layout_height=\"wrap_content\"\n"
			+ "            android:src=\"@drawable/cd_rightbutoom\" />\n"
			+ "    </LinearLayout>\n"
			+ "	    <!-- 阴影 -->\n"
			+ "	    <View \n"
			+ "	        android:id=\"@+id/shadow\"\n"
			+ "	        android:layout_width=\"match_parent\"\n"
			+ "	        android:layout_height=\"match_parent\"\n"
			+ "	        android:visibility=\"invisible\"\n"
			+ "	        android:alpha=\"0.8\"\n"
			+ "	        android:background=\"#000000\"\n"
			+ "	        />\n"
			+ "              <!--键盘布局  放在最下面以免被挡住-->\n"
			+ "        <com.zhonghong.radio.RadioBoard\n"
			+ "            android:id=\"@+id/radio_activity_Keyboard\"\n"
			+ "            android:layout_width=\"wrap_content\"\n"
			+ "   		     android:layout_height=\"wrap_content\"  \n"
			+ "   		  \n"
			+ "   		         />\n"
			+ "        </RelativeLayout>\n"
			+ "    </FrameLayout>\n"
			+ "\n"
			+ "</RelativeLayout>";
	public static void main(String[] args) throws Exception {
		XMLToCode mXMLToCode=new XMLToCode();
		mXMLToCode.showExample();
	}

}
