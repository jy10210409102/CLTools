/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tool;

import com.chenli.dao.RowLanguage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import static tool.Tool.languageStrMap;

/**
 *
 * @author Administrator
 */
public class DataBaeOperate {

    public DataBaeOperate() {
    }
    private static DataBaeOperate dbo = new DataBaeOperate();

    public static DataBaeOperate DBO() {
        return dbo;
    }
    public static String ip;
    public static String duanKou;
    public static String dataName;
    public static String name;
    public static String pwd;
    public static Connection con = null;//数据库连接对象
    public static JTextArea TranTxtOut = null;//翻译工具主界面文本输出框
    public static JComboBox projectStr = null;//翻译主界面的 项目名称组合框

    public void lianjieshujuku() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        //Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:mysql://" + ip + ":" + duanKou + "/" + dataName + "";
        //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hx","root","123");
        this.con = DriverManager.getConnection(url, name, pwd);
    }

    public boolean inserData() throws Exception {
        if (con == null) {
            return false;
        }
        PreparedStatement ps = con.prepareStatement("select * from language");
        //ps.setInt(1, 1);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            // TranTxtOut.append("\r\n" + rs.getString(7));
        }
        rs.close();
        rs = null;
        ps.close();
        ps = null;
        return false;
    }

    //导入数据库 替换与写入  type 为替换还是合并   1为替换 2为合并
    public boolean inserTranslateAllStr(ArrayList<RowLanguage> rowL, String src, int type) throws Exception {
        // TranTxtOut.append("\r\n" + "rowL.length:" + rowL.size() + "rowL.get(1).getLanguage().length" + rowL.get(1).getLanguage().length);
        if (con == null) {
            return false;
        }
        PreparedStatement ps = null;
        for (int i = 0; i < rowL.size(); i++) {

            //如果存在项目src 的中文 则 修改替换其中翻译  否则 直接添加写入！！！！！！！！！！！！！！！
            if (isChsTranExist(src, rowL.get(i).getLanguage()[1])) {//如果存在中文记录  update
                if (type == 1) {//替换 update
                    //update language set " + languageType + "=? where chs=? and src=?
                    ps = con.prepareStatement("update  language set time=? , ENGLISH=?,CHS=?,CHR=?,ARABIC=?,HEBREW=?,RUSSIAN=?,"
                            + "GREECE=?,SLOVENE=?,PORTUGAL=?,TURKSIH=?,FRENCH=?,GERMAN=?,ITALY=?,SPAIN=?,HUNGARY=?,POLISH=?,RUMANIA=?,"
                            + "CZECH=?,VIETNAMESE=?,DENMARK=?,JAPANESE=?,THAI=? where src=? and CHS=? "); //一共二十五个问号 即二十二种语言

                    for (int n = 0; n < Tool.languageCount; n++) { //循环 22ci 22种语言
                        if (n < rowL.get(i).getLanguage().length) {  //  目前只有15种
                            ps.setString(n + 2, rowL.get(i).getLanguage()[n]);
                        } else {
                            ps.setString(n + 2, null);
                        }
                    }
                    ps.setString(1, Tool.Tool().getTime1());
                    ps.setString(24, src);
                    ps.setString(25, rowL.get(i).getLanguage()[1]);
                    ps.executeUpdate();
                    //不能结束
                } else if (type == 2) {//合并 update
                    for (int s = 0; s < rowL.get(i).getLanguage().length; s++) {
                        //如果 s 源的中文 c 的语中 a 不存在翻译 则存入数据库
                        if (!isTranExist(src, rowL.get(i).getLanguage()[1], (String) languageStrMap.get(s))) {
                            saveTranDateBase(src, rowL.get(i).getLanguage()[1], (String) languageStrMap.get(s), rowL.get(i).getLanguage()[s]);
                        }
                    }
                }
            } else {
                //如果不存在项目src 的中文  则直接写入
                ps = con.prepareStatement("insert into language(SRC,TIME,ENGLISH,CHS,CHR,ARABIC,HEBREW,RUSSIAN,"
                        + "GREECE,SLOVENE,PORTUGAL,TURKSIH,FRENCH,GERMAN,ITALY,SPAIN,HUNGARY,POLISH,RUMANIA,"
                        + "CZECH,VIETNAMESE,DENMARK,JAPANESE,THAI)"
                        + "values(?,?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?,?,"
                        + "?,?,?,?,?,?,?);"); //一共二十四个问号
                // TranTxtOut.append("\r\n" + "src:" + src);
                ps.setString(1, src);
                ps.setString(2, Tool.Tool().getTime1());
                for (int n = 0; n < Tool.languageCount; n++) {
                    if (n < rowL.get(i).getLanguage().length) {
                        ps.setString(n + 3, rowL.get(i).getLanguage()[n]);
                    } else {
                        ps.setString(n + 3, null);
                    }
                }
                ps.executeUpdate();
            }
        }
        //ps.setInt(1, 1);
        ps.close();
        ps = null;
        return true;

    }

    //查询是否有对应的中文、语种的翻译   根据中文和语种判断是否有此中文的记录
    public boolean isLanguageTypeTranExist(String languageType, String chsStr) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = con.prepareStatement("select " + languageType + " from language where chs=?"); //
        ps.setString(1, chsStr);
        rs = ps.executeQuery();
        if (rs.next()) {//如果存在结果
            if (rs.getString(1) == null || rs.getString(1).equals("") || Tool.Tool().isNum(rs.getString(1))) { //如果不存在翻译 
                //  TranTxtOut.append("\r\n" + "不存在：\t languageType:" + languageType + " chsStr:" + chsStr);
                rs.close();
                rs = null;
                ps.close();
                ps = null;
                return false;
            } else {
                rs.close();
                rs = null;
                ps.close();
                ps = null;
                //  TranTxtOut.append("\r\n" + "存在：\t languageType:" + languageType + " chsStr:" + chsStr);
                return true;
            }
        }
        rs.close();
        rs = null;
        ps.close();
        ps = null;
        return false;


    }

    //根据中文 获得某语种的第一个查找到的翻译  去掉src         最后查google词库
    public String TranLanguageTypeNotSrc(String languageType, String chsStr) throws Exception {
        PreparedStatement ps = null;
        String sql = "select " + languageType + ",src  from language where chs=? and src!='google'";
        ps = con.prepareStatement(sql); //
        ps.setString(1, chsStr);
        ResultSet rs = ps.executeQuery();
        System.out.println(sql + "\r\n");
        String Transtr = null;
        String src;
        while (rs.next()) {
            Transtr = rs.getString(1);
            src = rs.getString(2);
            if (Transtr == null || Transtr.equals("") || Tool.Tool().isNum(Transtr)) { //如果不存在翻译 
                // TranTxtOut.append("\r\n" + src + "不存在：\t languageType:" + languageType + " chsStr:" + chsStr);
                Transtr = null;
                continue;
            } else {
                // TranTxtOut.append("\r\n" + src + "存在：\t languageType:" + languageType + " chsStr:" + chsStr);
                System.out.println("数据库来源 src: " + src);
                rs.close();
                rs = null;
                ps.close();
                ps = null;
                return Transtr;
            }
        }
        //查完其它词库再查google词库
        Transtr = TranLanguageTypeNotSrcGoogle(languageType, chsStr);
        if (Transtr == null || Transtr.equals("") || Tool.Tool().isNum(Transtr)) {
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            return null;
        }
        rs.close();
        rs = null;
        ps.close();
        ps = null;
        return Transtr;


    }

    //根据中文   //查google词库
    public String TranLanguageTypeNotSrcGoogle(String languageType, String chsStr) throws Exception {
        PreparedStatement ps = null;
        String sql = "select " + languageType + " from language where chs=? and src='google'";
        ps = con.prepareStatement(sql); //
        ps.setString(1, chsStr);
        ResultSet rs = ps.executeQuery();
        System.out.println(sql + "\r\n");
        String Transtr = null;
        while (rs.next()) {
            Transtr = rs.getString(1);
            if (Transtr == null || Transtr.equals("") || Tool.Tool().isNum(Transtr)) { //如果不存在翻译 
                // TranTxtOut.append("\r\n" + src + "不存在：\t languageType:" + languageType + " chsStr:" + chsStr);
                Transtr = null;
                continue;
            } else {
                // TranTxtOut.append("\r\n" + src + "存在：\t languageType:" + languageType + " chsStr:" + chsStr);
                System.out.println("数据库来源 src:google ");
                rs.close();
                rs = null;
                ps.close();
                ps = null;
                return Transtr;
            }
        }
        //查完其它词库再查google词库
        rs.close();
        rs = null;
        ps.close();
        ps = null;
        return null;
    }

    //查询是否有对应的中文  根据中文和词库判断是否有此中文的记录
    public boolean isChsTranExist(String tranSrc, String chsStr) throws Exception {
        PreparedStatement ps = null;
        ps = con.prepareStatement("select * from language where src='" + tranSrc + "' and chs='" + chsStr + "'"); //
        ResultSet rs = ps.executeQuery();
        // while (rs.next()) {
        if (rs.next()) {//如果存在
            // TranTxtOut.append("\r\n" + "存在：\t tranSrc:" + tranSrc + " chsStr:" + chsStr);
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            return true;
        }
        //TranTxtOut.append("\r\n" + "不存在：\ttranSrc:" + tranSrc + " chsStr:" + chsStr);
        rs.close();
        rs = null;
        ps.close();
        ps = null;
        return false;

    }

    //查询数据库 根据 词库src 、中文 查找是否存在 某语种 的翻译是否存在
      /*
     * param
     * tranSrc 数据库中src 翻译来源
     * chsStr  简体中文内容
     * languageType 语种
     * sql 语句原型select greece from language where src='众鸿' and chs='星期一';
     */
    public boolean isTranExist(String tranSrc, String chsStr, String languageType) throws Exception {
        // TranTxtOut.append("\r\n" + "tranSrc:" + tranSrc + " chsStr:" + chsStr + " languageTypeStr:" + languageType);
        PreparedStatement ps = null;
        ps = con.prepareStatement("select  " + languageType + " from language where src='" + tranSrc + "' and chs='" + chsStr + "'"); //
        ResultSet rs = ps.executeQuery();
        // while (rs.next()) {

        if (!rs.next()) {
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            return false;
        }
        if (rs.getString(1) == null || rs.getString(1).equals("") || Tool.Tool().isNum(rs.getString(1))) {   //如果存在 返回true
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            return false;
        }
        rs.close();
        rs = null;
        ps.close();
        ps = null;
        return true;

    }

    //根据 src 中文 语种 取得对应的翻译   
    public String TranSrcLanguageType(String tranSrc, String chsStr, String languageType) throws Exception {
        // TranTxtOut.append("\r\n" + "tranSrc:" + tranSrc + " chsStr:" + chsStr + " languageTypeStr:" + languageType);
        PreparedStatement ps = null;
        String sql = "select  " + languageType + " from language where src = ?  and  chs=? ;";
        ps = con.prepareStatement(sql); //
        ps.setString(1, tranSrc);
        ps.setString(2, chsStr);
        ResultSet rs = ps.executeQuery();
        System.out.println(sql + "\r\n");

        if (!rs.next()) {
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            return null;
        }
        String str = rs.getString(1);
        if (str == null || str.equals("") || Tool.Tool().isNum(str)) {   //如果存在 返回true
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            return null;
        } else {
            String ss = rs.getString(1);
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            System.out.println("数据库来源 src:" + tranSrc);
            //   TranTxtOut.append("\r\n" + rs.getString(1));
            return ss;
        }

        //return rs.getString(1);
    }

    //按翻译来源写中文到数据库
    public boolean saveChsSrc(String tranSrc, String chsStr) throws Exception {
        // TranTxtOut.append("\r\n" + "tranSrc:" + tranSrc + " chsStr:" + chsStr);
        PreparedStatement ps = null;
        ps = con.prepareStatement("insert into language(SRC,TIME,CHS) values(?,?,?);"); //
        ps.setString(1, tranSrc);
        ps.setString(2, Tool.Tool().getTime1());
        ps.setString(3, chsStr);
        ps.executeUpdate();

        ps.close();
        ps = null;
        return true;
    }

    //某一翻译修改存入数据库  ("配件库", chsStr, languageType, tranStr);
    public boolean saveTranDateBase(String tranSrc, String chsStr, String languageType, String languageTypeStr) throws Exception {
        //  TranTxtOut.append("\r\n" + "tranSrc:" + tranSrc + " chsStr:" + chsStr + " languageTypeStr:" + languageTypeStr);
        PreparedStatement ps = null;
        ps = con.prepareStatement("update language set " + languageType + "=? where chs=? and src=?;"); //
        ps.setString(1, languageTypeStr);
        ps.setString(2, chsStr);
        ps.setString(3, tranSrc);
        ps.executeUpdate();

        ps.close();
        ps = null;
        return true;
    }

    //获取数据库所有项目名  select distinct  src from language; 
    public Vector<String> getAllProjectName() throws SQLException {
        Vector<String> vector = new Vector<String>();
        PreparedStatement ps = null;
        ps = con.prepareStatement("select distinct src from language; "); //
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            vector.add(rs.getString(1));
        }
        projectStr.setModel(new DefaultComboBoxModel(vector));
        projectStr.updateUI();
        rs.close();
        rs = null;
        ps.close();
        ps = null;
        return vector;

    }

    //管理员登陆 是否存在此用户
    public boolean isManager(String userName, String pwd) throws SQLException {
        PreparedStatement ps = null;
        ps = con.prepareStatement("select *  from manager where username =? and password=?; "); //
        ps.setString(1, userName);
        ps.setString(2, pwd);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return true;
        }
        rs.close();
        rs = null;
        ps.close();
        ps = null;
        return false;
    }

      //是否存在某id翻译
    
    public boolean isIdSran(int id) throws Exception{
         PreparedStatement ps = null;
        ps = con.prepareStatement("select *  from language where id=?; "); //
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return true;
        }
        rs.close();
        rs = null;
        ps.close();
        ps = null;
        return false;
    }
    //删除指定id翻译
    public boolean deleteInId(int id) throws Exception {
        PreparedStatement ps = null;
        ps = con.prepareStatement("delete from language where id=? ;"); //
        ps.setInt(1, id);
        int i=ps.executeUpdate();
        ps.close();
        ps = null;
        return true;
    }
    
      //删除指定范围id 翻译
    public boolean deleteBetweenId(int id1,int id2) throws Exception {
        PreparedStatement ps = null;
        ps = con.prepareStatement("delete from language where id>=? and id<=? ;"); //
        ps.setInt(1, id1);
        ps.setInt(2, id2);
        int i=ps.executeUpdate();
        ps.close();
        ps = null;
        return true;
    }
    
    //删除某词库
    //删除指定范围id 翻译
    public boolean deleteDic(String KuMing) throws Exception {
        PreparedStatement ps = null;
        ps = con.prepareStatement("delete from language where src=? ;"); //
        ps.setString(1, KuMing);
        int i=ps.executeUpdate();
        ps.close();
        ps = null;
        return true;
    }
    
    //查询数据库 按词库 和中文 取翻译
    //
    //数据库翻译
}
