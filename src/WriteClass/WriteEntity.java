package WriteClass;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * ********************************************************
 *
 * @author DoDo
 * @ClassName: WriteEntity
 * @Description: 创建 hibernate 实体对象类
 * @date 2012-12-26 下午06:02:04
 * ******************************************************
 */
public class WriteEntity {
    ReadTable rt = new ReadTable();
    public String webHtml = "WebRoot\\pages\\"; //页面生成路径(WEB 里边)
    public String address = ""; // 项目绝对路径

    private String table_name = ""; // 表名
    private String entity_name = ""; // 类名
    private String locahost = ""; // java代码生成路径

    private String comments_table = ""; // 表说明
    private Map<String, String> comment_map = new HashMap<String, String>(); // 表属性明说
    private Map<String, String[][]> hibernate_map = new HashMap<String, String[][]>(); // hibernate
    // 实体对象信息
    //private  String pkval="";//转义后的id别名

    private String isTable;//判断是数据表还是视图


    /**
     * ********************************************************
     *
     * @param table
     * @return String
     * @Title: Write
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @date 2012-12-26 下午06:02:27
     * *******************************************************
     */
    public void Write(String table) {
        table_name = table;
        ReadTable rt = new ReadTable();
        Map<String, String[][]> table_map = rt.read(table); // 表信息
        comments_table = rt.findTableComments(table); // 表说明
        comment_map = rt.findColComment(table); // 表属性明说
        hibernate_map = getHibernate(table_map); // 数据库 实体对象信息

        String addr[] = new File(NewEntity.class.getName()).getAbsolutePath()
                .replace("%20", " ").split("\\\\");
        address = ""; // 项目绝对路径
        for (int i = 0; i < addr.length - 1; i++) {
            address += addr[i] + "\\";
        }
        locahost = address + "src\\"; // newEntity 类 路径
        entity_name = getEntityName(); // 类名


//		String [] pkvalue=rt.getPK(table).split("_");
//		for(int i=0;i<pkvalue.length;i++){
//			String eachArray=(pkvalue[i].substring(0, 1).toUpperCase())+(pkvalue[i].substring(1, pkvalue[i].length()));
//			pkval+=eachArray;
//		}
        isTable = table_name.substring(0, 4).toUpperCase();

        Mapper("ky\\xml\\", "ky.xml"); //生成数据查询接口
        MapperXml("ky\\xml\\", "ky.xml");//生成数据查询xml


        entity("ky\\entity\\", "ky.entity");

        dao("ky\\dao\\", "ky.dao");
        daoImpl("ky\\dao\\Impl\\", "ky.dao.Impl");

        service("ky\\service\\", "ky.service");
        serviceImpl("ky\\service\\Impl\\", "ky.service.Impl");


        action("ky\\action\\", "ky.action");

        listHtml();//生成list页面（jsp）


        if (!isTable.equals("VIEW")) {
            saveHtml();//生成save页面（jsp）
            updateHtml();//生成update页面（jsp）
        }
    }

    /**
     * ********************************************************
     *
     * @param map table 数据
     * @return Map
     * @Title: getHibernate
     * @Description: 获取 hibernate 属性
     * @date 2012-12-26 下午06:03:29
     * *******************************************************
     */
    public Map getHibernate(Map<String, String[][]> map) {

        Map<String, String[][]> h_map = new HashMap<String, String[][]>();

        for (Map.Entry<String, String[][]> t_m : map.entrySet()) { // hibernate
            // 属性名 和
            // 数据类型

            String[] t_name = t_m.getKey().split("_");
            String h_name = "";

            for (int i = 0; i < t_name.length; i++) {
                if (i > 0) {
                    h_name += t_name[i].substring(0, 1).toUpperCase()
                            + t_name[i].substring(1, t_name[i].length());
                } else {
                    h_name += t_name[i].substring(0, t_name[i].length());
                }

            }
            String type = t_m.getValue()[0][0];
            if (type.equals("varchar2") || type.equals("varchar")
                    || type.equals("nvarchar2") || type.equals("nvarchar")
                    || type.equals("char")) {
                type = "String";
            } else if (type.equals("number")) {

                if (Integer.parseInt(t_m.getValue()[0][1]) > 0) {
                    type = "Double";
                } else {
                    type = "Integer";
                }

            } else if (type.equals("int") || type.equals("integer")) {
                type = "Integer";
            } else if (type.equals("long")) {
                type = "Long";
            } else if (type.equals("date")) {
                type = "Date";
            } else if (type.equals("float") || type.equals("double")) {
                type = "Double";
            }

            h_map.put(t_m.getKey(), new String[][]{{h_name, type}});

        }

        return h_map;
    }

    /**
     * ********************************************************
     *
     * @return String
     * @Title: getEntityName
     * @Description: 生成对象名
     * @date 2012-12-27 下午03:11:19
     * *******************************************************
     */
    public String getEntityName() {

        String[] t_name = table_name.toLowerCase().split("_");
        String e_name = "";
        for (int i = 0; i < t_name.length; i++) {
            e_name += t_name[i].substring(0, 1).toUpperCase()
                    + t_name[i].substring(1, t_name[i].length());
        }

        return e_name;
    }

    /**
     * ********************************************************
     *
     * @param file   文件路径
     * @param string 写入内容
     * @Title: writeEntity
     * @Description: 写入实体类
     * @date 2012-12-27 下午04:24:49
     * *******************************************************
     */
    private void writeEntity(String file, String string) {
        System.out.println(file);

        try {
            FileOutputStream outs = new FileOutputStream(file, false);
            PrintStream p = new PrintStream(outs);
            p.println(string);
            p.flush();
            p.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("创建失败 ： " + file);
        }
        System.out.println("创建成功 ： " + file);
    }

    private void entity(String path, String pak) {

        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        String newFile = locahost + path + entity_name + ".java"; // 要生成的实体类
        entity_txt.append("package ").append(pak).append(";\r\n"); // 包名
        entity_txt.append("import java.util.Date; ").append("\r\n"); // 包名

        entity_txt.append("\r\n");

        // 表注释
        entity_txt.append("/**").append("\r\n");
        entity_txt.append("* ********************************************************").append("\r\n");
        entity_txt.append("* @ClassName: ").append(entity_name).append("\r\n");
        entity_txt.append("* @Description: ").append(comments_table).append("\r\n");
        entity_txt.append("* @author 用wzl写的自动生成").append("\r\n");
        entity_txt.append("* @date ").append(new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss ").format(new Date())).append("\r\n");
        entity_txt.append("*******************************************************").append("\r\n");
        entity_txt.append("*/").append("\r\n");

        entity_txt.append("public class ").append(entity_name).append("{").append("\r\n"); // 类开始
        entity_txt.append("\r\n");

        // ----------- 属性
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            entity_txt.append("\t").append("private ").append(h_m.getValue()[0][1]).append(" ").append(h_m.getValue()[0][0]).append(";"); // 属性信息
            entity_txt.append("\t\t//").append(comment_map.get(h_m.getKey())).append("\r\n"); // 说明信息
        }

        entity_txt.append("\r\n");
        // -------------- get 和 set 方法
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {

            String name = h_m.getValue()[0][0]; // 属性名
            String m_name = name.substring(0, 1).toUpperCase()
                    + name.substring(1, name.length()); // 首字母大写 属性名
            String type = h_m.getValue()[0][1]; // 数据类型
            String t_name = h_m.getKey(); // 在数据库里的字段名

            // get方法
            entity_txt.append("\t").append("public ").append(type).append(
                    " get").append(m_name).append("() {").append("\r\n");
            entity_txt.append("\t\t").append("return this.").append(name)
                    .append(";\r\n");
            entity_txt.append("\t").append("}").append("\r\n");
            entity_txt.append("\r\n");
            // set 方法
            entity_txt.append("\t").append("public void").append(" set")
                    .append(m_name).append("(").append(type).append(" ")
                    .append(name).append(") {").append("\r\n");
            entity_txt.append("\t\t").append("this.").append(name)
                    .append(" = ").append(name).append(";\r\n");
            entity_txt.append("\t").append("}").append("\r\n");
            entity_txt.append("\r\n");
        }
        entity_txt.append("}").append("\r\n"); // 类结束

        writeEntity(newFile, entity_txt.toString());
    }

    public void Mapper(String path, String pak) {


        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        String name = entity_name + "Mapper";
        String newFile = locahost + path + name + ".java"; // 要生成的实体类
        entity_txt.append("package ").append(pak).append(";\r\n"); // 包名
        entity_txt.append("\r\n");
        entity_txt.append("import ").append("java.util.Date").append(";\r\n");
        entity_txt.append("import ").append("java.util.List").append(";\r\n");
        entity_txt.append("import ").append("java.util.Map").append(";\r\n");
        entity_txt.append("import ").append("ky.entity." + entity_name + "").append(";\r\n");
        entity_txt.append("import ").append("ky.util.PageView").append(";\r\n");


        // 表注释
        entity_txt.append("/**").append("\r\n");
        entity_txt.append(
                "* ********************************************************")
                .append("\r\n");
        entity_txt.append("* @ClassName: ").append(name).append("\r\n");
        entity_txt.append("* @Description: ").append(comments_table).append(
                "\r\n");
        entity_txt.append("* @author 数据查询接口").append("\r\n");
        entity_txt.append("* @date ").append(
                new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss ")
                        .format(new Date())).append("\r\n");
        entity_txt.append(
                "*******************************************************")
                .append("\r\n");
        entity_txt.append("*/").append("\r\n");

        entity_txt.append("public interface ").append("" + name + " extends BaseMapper<" + entity_name + ">").append("{").append("\r\n"); // 类开始

        entity_txt.append("\r\n");

        entity_txt.append("}").append("\r\n"); // 类结束
        writeEntity(newFile, entity_txt.toString());
    }


    public void MapperXml(String path, String pak) {
        StringBuffer entity_txt = new StringBuffer(); // 要生成的信息
        String name = entity_name + "Mapper";//类名
        String type = "ky.entity." + entity_name;//实体路径
        String newFile = locahost + path + name + ".xml"; // 要生成的实体类
        String pk_name = "";//主键名称
        if (!isTable.equals("VIEW")) {
            pk_name = rt.getPK(table_name.toUpperCase());//主键名称
        }
        String oneval = "";

        //xml 文件表头
        entity_txt.append("<?xml version='1.0' encoding='UTF-8' ?>").append("\r\n");
        entity_txt.append("<!DOCTYPE mapper").append("\r\n");
        entity_txt.append("PUBLIC '-//mybatis.org//DTD Mapper 3.0//EN'").append("\r\n");
        entity_txt.append("'http://mybatis.org/dtd/mybatis-3-mapper.dtd'>").append("\r\n");
        entity_txt.append("<mapper namespace='" + pak + "." + name + "'>").append("\r\n");

        entity_txt.append("\r\n");
        entity_txt.append("\t").append("<resultMap type=\"" + type + "\" id=\"resultMap\">").append("\r\n");
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            if (pk_name.equals(h_m.getKey())) {
                entity_txt.append("\t\t").append("<id column=\"" + pk_name + "\" ").append("property=\"" + h_m.getValue()[0][0] + "\" />").append("\r\n"); // 属性信息(id)
                oneval = h_m.getValue()[0][0];
            } else {
                entity_txt.append("\t\t").append("<result column=\"" + h_m.getKey() + "\" ").append("property=\"" + h_m.getValue()[0][0] + "\" />").append("\r\n"); // 属性信息
            }
        }
        entity_txt.append("\t").append("</resultMap>").append("\r\n");
        entity_txt.append("\r\n");

        String param = "";
        String pvalue = "";

        int num1 = 1;
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            if (!pk_name.equals(h_m.getKey())) {
                param += (h_m.getKey()) + (","); // 数据表的所有字段
                //pvalue+=("#{"+h_m.getValue()[0][0]+"},");
                if (h_m.getValue()[0][1].equals("Integer")) {
                    pvalue += ("#{" + h_m.getValue()[0][0] + ",jdbcType=INTEGER},");
                } else {
                    pvalue += ("#{" + h_m.getValue()[0][0] + ",jdbcType=VARCHAR},");
                }
            }
            num1++;
        }
        param = param.substring(0, param.length() - 1);
        pvalue = pvalue.substring(0, pvalue.length() - 1);


        entity_txt.append("\t").append("<!-- 公用的判断条 -->").append("\r\n");
        entity_txt.append("\t").append("<sql id=\"configParamSql\">").append("\r\n");
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {

            if (h_m.getValue()[0][1].equals("Integer")) {
                entity_txt.append("\t\t\t\t\t").append("<if test=\"searchBean." + h_m.getValue()[0][0] + " !=null  and  searchBean." + h_m.getValue()[0][0] + "!=0\">").append("\r\n");
            } else {
                entity_txt.append("\t\t\t\t\t").append("<if test=\"searchBean." + h_m.getValue()[0][0] + " !=null  and  searchBean." + h_m.getValue()[0][0] + "!=''\">").append("\r\n");
            }

            entity_txt.append("\t\t\t\t\t\t\t").append("and obj." + h_m.getKey() + "=#{searchBean." + h_m.getValue()[0][0] + "}").append("\r\n");
            entity_txt.append("\t\t\t\t\t\t").append("</if>").append("\r\n");
        }
        entity_txt.append("\t").append("</sql>").append("\r\n");
        entity_txt.append("\r\n");

        //select的sql语句(分页)
        entity_txt.append("\t").append("<select id=\"selectPage\" resultMap=\"resultMap\" parameterType=\"ky.util.PageView\">").append("\r\n");
        entity_txt.append("\t\t").append("SELECT * FROM (SELECT A.*, ROWNUM RN FROM ( ").append("\r\n");
        entity_txt.append("\t\t\t").append("SELECT * FROM " + table_name + " obj ").append("\r\n");
        entity_txt.append("\t\t\t\t").append("<where>").append("\r\n");
        entity_txt.append("\t\t\t\t\t").append("<include refid=\"configParamSql\"/>").append("\r\n");
        entity_txt.append("\t\t\t\t").append("</where>").append("\r\n");
        entity_txt.append("\t\t\t\t").append("<if test=\"sort !=null  and  sort!=''\">").append("\r\n");
        entity_txt.append("\t\t\t\t\t").append("order by obj.${sort} ${order}").append("\r\n");
        entity_txt.append("\t\t\t\t").append("</if>").append("\r\n");
        entity_txt.append("\t\t\t\t").append("<if test=\"sort==null\">").append("\r\n");
        entity_txt.append("\t\t\t\t\t").append("order by obj." + pk_name + " desc").append("\r\n");
        entity_txt.append("\t\t\t\t").append("</if>").append("\r\n");
        entity_txt.append("\t\t\t").append(")A WHERE ROWNUM &lt;= 1* (#{pageNum}*#{pageSize}))").append("\r\n");
        entity_txt.append("\t\t").append("WHERE RN &gt;= (#{pageNum}-1)*#{pageSize}+1").append("\r\n");
        entity_txt.append("\t").append("</select>").append("\r\n");
        entity_txt.append("\r\n");


        //select的sql语句(不分页)
        entity_txt.append("\t").append("<select id=\"selectList\" resultMap=\"resultMap\" parameterType=\"" + type + "\">").append("\r\n");
        entity_txt.append("\t\t").append("select * from " + table_name + "").append("\r\n");
        entity_txt.append("\t\t").append("<where>").append("\r\n");
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            if (h_m.getValue()[0][1].equals("Integer")) {
                entity_txt.append("\t\t\t\t").append("<if test=\"" + h_m.getValue()[0][0] + " !=null  and  " + h_m.getValue()[0][0] + "!=0\">").append("\r\n");
            } else {
                entity_txt.append("\t\t\t\t").append("<if test=\"" + h_m.getValue()[0][0] + " !=null  and  " + h_m.getValue()[0][0] + "!=''\">").append("\r\n");
            }
            entity_txt.append("\t\t\t\t").append("and " + h_m.getKey() + "=#{" + h_m.getValue()[0][0] + "}").append("\r\n");
            entity_txt.append("\t\t\t").append("</if>").append("\r\n");
        }
        entity_txt.append("\t\t").append("</where>").append("\r\n");
        entity_txt.append("\t").append("</select>").append("\r\n");
        entity_txt.append("\r\n");


        //视图没有添加，修改和删除功能
        if (!isTable.equals("VIEW")) {
            //insert的sql语句
            entity_txt.append("\t").append("<insert id=\"save\" parameterType=\"" + type + "\">").append("\r\n");
            entity_txt.append("\t\t").append("<selectKey resultType=\"java.lang.Integer\" keyProperty=\"id\" order=\"BEFORE\">").append("\r\n");
            entity_txt.append("\t\t\t").append("select idauto.nextval from dual").append("\r\n");
            entity_txt.append("\t\t").append("</selectKey>").append("\r\n");
            entity_txt.append("\t\t").append("<![CDATA[insert into " + table_name + "(" + param + ")").append("\r\n");
            entity_txt.append("\t\t").append("values(" + pvalue + ")]]>").append("\r\n");
            entity_txt.append("\t").append("</insert>").append("\r\n");
            entity_txt.append("\r\n");

            //detele的sql语句
            entity_txt.append("\t").append("<delete id=\"delete\" parameterType=\"java.lang.Integer\">").append("\r\n");
            entity_txt.append("\t\t").append("delete from " + table_name + "").append("\r\n");
            entity_txt.append("\t\t").append("where " + rt.getPK(table_name) + "=#{id}").append("\r\n");
            entity_txt.append("\t").append("</delete>").append("\r\n");
            entity_txt.append("\r\n");


            //update 的sql语句
            entity_txt.append("\t").append("<update id='update' parameterType='" + type + "'>").append("\r\n");
            entity_txt.append("\t\t").append("update " + table_name + " set").append("\r\n");
            int num2 = 1;
            for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {  //INTEGER  integer
                if (num2 == num1 - 1) {
                    if (h_m.getValue()[0][1].equals("Integer")) {
                        entity_txt.append("\t\t").append("" + h_m.getKey() + " = #{" + h_m.getValue()[0][0] + ",jdbcType=INTEGER}").append("\r\n");
                    } else {
                        entity_txt.append("\t\t").append("" + h_m.getKey() + " = #{" + h_m.getValue()[0][0] + ",jdbcType=VARCHAR}").append("\r\n");
                    }

                } else {
                    if (h_m.getValue()[0][1].equals("Integer")) {
                        entity_txt.append("\t\t").append("" + h_m.getKey() + " = #{" + h_m.getValue()[0][0] + ",jdbcType=INTEGER},").append("\r\n");
                    } else {
                        entity_txt.append("\t\t").append("" + h_m.getKey() + " = #{" + h_m.getValue()[0][0] + ",jdbcType=VARCHAR},").append("\r\n");
                    }
                }
                num2++;
            }
            entity_txt.append("\t\t").append("where " + rt.getPK(table_name) + "=#{" + oneval + "}").append("\r\n");
            entity_txt.append("\t").append("</update>").append("\r\n");
            entity_txt.append("\r\n");
        }

        //查询分页信息总条数
        entity_txt.append("\t").append("<select id=\"total\" resultType=\"int\"	parameterType=\"ky.util.PageView\">").append("\r\n");
        entity_txt.append("\t\t").append("select count(*) from " + table_name + " obj").append("\r\n");
        entity_txt.append("\t\t").append("<where>").append("\r\n");
        entity_txt.append("\t\t\t").append("<include refid=\"configParamSql\"/>").append("\r\n");
        entity_txt.append("\t\t").append("</where>").append("\r\n");
        entity_txt.append("\t").append("</select>").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("</mapper>").append("\r\n");
        writeEntity(newFile, entity_txt.toString());

    }

    private void dao(String path, String pak) {

        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        String name = entity_name + "Dao"; // 类名
        String newFile = locahost + path + name + ".java"; // 要生成的实体类
        entity_txt.append("package ").append(pak + ";").append("\r\n"); // 包名
        entity_txt.append("\r\n");

        entity_txt.append("import ky.entity." + entity_name + ";").append("\r\n");
        entity_txt.append("import java.util.List;").append("\r\n");
        entity_txt.append("import java.util.Map;").append("\r\n");
        entity_txt.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        entity_txt.append("import org.springframework.stereotype.Repository;").append("\r\n");
        entity_txt.append("import ").append("ky.util.PageView").append(";\r\n");
        entity_txt.append("\r\n");

        // 表注释
        entity_txt.append("/**").append("\r\n");
        entity_txt.append("* ********************************************************").append("\r\n");
        entity_txt.append("* @ClassName: ").append(name).append("\r\n");
        entity_txt.append("* @Description: ").append(comments_table).append("\r\n");
        entity_txt.append("* @author 用wzl写的自动生成dao包").append("\r\n");
        entity_txt.append("* @date ").append(new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss ").format(new Date())).append("\r\n");
        entity_txt.append("*******************************************************").append("\r\n");
        entity_txt.append("*/").append("\r\n");


        entity_txt.append("@Repository").append("\r\n");
        entity_txt.append("public interface " + name + " extends BaseDao<" + entity_name + ">{").append("\r\n");
        entity_txt.append("\r\n");
        entity_txt.append("}").append("\r\n"); // 类结束
        writeEntity(newFile, entity_txt.toString());
    }

    public void daoImpl(String path, String pak) {

        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        String name = entity_name + "DaoImpl"; // 类名
        String newFile = locahost + path + name + ".java"; // 要生成的实体类
        String briefName = entity_name + "M";
        entity_txt.append("package ").append(pak + ";").append("\r\n"); // 包名
        entity_txt.append("\r\n");

        entity_txt.append("import ky.entity." + entity_name + ";").append("\r\n");
        entity_txt.append("import ky.dao." + entity_name + "Dao;").append("\r\n");
        entity_txt.append("import java.util.List;").append("\r\n");
        entity_txt.append("import java.util.Map;").append("\r\n");
        entity_txt.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        entity_txt.append("import org.springframework.stereotype.Repository;").append("\r\n");
        entity_txt.append("import ").append("ky.util.PageView").append(";\r\n");
        entity_txt.append("\r\n");

        // 表注释
        entity_txt.append("/**").append("\r\n");
        entity_txt.append("* ********************************************************").append("\r\n");
        entity_txt.append("* @ClassName: ").append(name).append("\r\n");
        entity_txt.append("* @Description: ").append(comments_table).append("\r\n");
        entity_txt.append("* @author 用wzl写的自动生成daoImpl包").append("\r\n");
        entity_txt.append("* @date ").append(new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss ").format(new Date())).append("\r\n");
        entity_txt.append("*******************************************************").append("\r\n");
        entity_txt.append("*/").append("\r\n");


        entity_txt.append("@Repository").append("\r\n");
        entity_txt.append("\t").append("public class " + name + " extends BaseDaoImpl<" + entity_name + "> implements " + entity_name + "Dao{").append("\r\n");
        entity_txt.append("}").append("\r\n");
        writeEntity(newFile, entity_txt.toString());
    }


    /**
     * ********************************************************.<br>
     * [方法] service <br>
     * [描述] TODO(这里用一句话描述这个方法的作用) <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] void <br>
     * [时间] 2014-5-5 下午05:39:39 <br>
     * ********************************************************.<br>
     */
    private void service(String path, String pak) {
        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        String name = entity_name + "Service"; // 类名
        String newFile = locahost + path + name + ".java"; // 要生成的实体类
        entity_txt.append("package ").append(pak).append(";\r\n"); // 包名
        entity_txt.append("\r\n");

        entity_txt.append("import ky.entity." + entity_name + ";").append("\r\n");
        entity_txt.append("import java.util.Date;").append("\r\n");
        entity_txt.append("import java.util.List;").append("\r\n");
        entity_txt.append("import java.util.Map;").append("\r\n");
        entity_txt.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        entity_txt.append("import org.springframework.stereotype.Service;").append("\r\n");
        entity_txt.append("import ky.util.PageView;").append("\r\n");
        entity_txt.append("\r\n");

        // 表注释
        entity_txt.append("/**").append("\r\n");
        entity_txt.append("* ********************************************************").append("\r\n");
        entity_txt.append("* @ClassName: ").append(name).append("\r\n");
        entity_txt.append("* @Description: ").append(comments_table).append("\r\n");
        entity_txt.append("* @author 生成service类").append("\r\n");
        entity_txt.append("* @date ").append(new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss ").format(new Date())).append("\r\n");
        entity_txt.append("*******************************************************").append("\r\n");
        entity_txt.append("*/").append("\r\n");

        entity_txt.append("@Service").append("\r\n");
        entity_txt.append("public interface ").append(name + "{").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("\t").append("public PageView selectPage(PageView pageView) ;").append("\r\n");

        entity_txt.append("\t").append("public List<" + entity_name + "> selectList(" + entity_name + " obj);").append("\r\n");

        if (!isTable.equals("VIEW")) {

            entity_txt.append("\t").append("public int save(" + entity_name + " obj);").append("\r\n");

            entity_txt.append("\t").append("public int delete(String idArray);").append("\r\n");

            entity_txt.append("\t").append("public int update(" + entity_name + " obj);").append("\r\n");
        }

        entity_txt.append("\r\n");
        entity_txt.append("}").append("\r\n"); // 类结束

        writeEntity(newFile, entity_txt.toString());
    }


    //生成service代码
    private void serviceImpl(String path, String pak) {
        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        String name = entity_name + "ServiceImpl"; // 类名
        String newFile = locahost + path + name + ".java"; // 要生成的实体类
        entity_txt.append("package ").append(pak).append(";\r\n"); // 包名
        entity_txt.append("\r\n");

        entity_txt.append("import ky.entity." + entity_name + ";").append("\r\n");
        entity_txt.append("import ky.dao." + entity_name + "Dao;").append("\r\n");
        entity_txt.append("import ky.service." + entity_name + "Service;").append("\r\n");
        entity_txt.append("import java.util.Date;").append("\r\n");
        entity_txt.append("import java.util.List;").append("\r\n");
        entity_txt.append("import java.util.Map;").append("\r\n");
        entity_txt.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        entity_txt.append("import org.springframework.stereotype.Service;").append("\r\n");
        entity_txt.append("import ky.util.PageView;").append("\r\n");
        entity_txt.append("\r\n");

        // 表注释
        entity_txt.append("/**").append("\r\n");
        entity_txt.append("* ********************************************************").append("\r\n");
        entity_txt.append("* @ClassName: ").append(name).append("\r\n");
        entity_txt.append("* @Description: ").append(comments_table).append("\r\n");
        entity_txt.append("* @author 生成service类").append("\r\n");
        entity_txt.append("* @date ").append(new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss ").format(new Date())).append("\r\n");
        entity_txt.append("*******************************************************").append("\r\n");
        entity_txt.append("*/").append("\r\n");

        entity_txt.append("@Service").append("\r\n");
        entity_txt.append("public class ").append(name + " extends BaseServiceImpl implements " + entity_name + "Service{").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("\t").append("@Autowired").append("\r\n");
        entity_txt.append("\t").append("private " + entity_name + "Dao " + entity_name.toLowerCase() + "Dao;").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("\t").append("public PageView selectPage(PageView pageView) {").append("\r\n");
        entity_txt.append("\t\t").append("return " + entity_name.toLowerCase() + "Dao.getPageView(pageView);").append("\r\n");
        entity_txt.append("\t").append("}").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("\t").append("public List<" + entity_name + "> selectList(" + entity_name + " obj) {").append("\r\n");
        entity_txt.append("\t\t").append("return " + entity_name.toLowerCase() + "Dao.selectList(obj);").append("\r\n");
        entity_txt.append("\t").append("}").append("\r\n");
        entity_txt.append("\r\n");

        if (!isTable.equals("VIEW")) {
            entity_txt.append("\t").append("public int update(" + entity_name + " obj) {").append("\r\n");
            entity_txt.append("\t\t").append("int param=1;").append("\r\n");
            entity_txt.append("\t\t").append("int param1=" + entity_name.toLowerCase() + "Dao.update(obj);").append("\r\n");
            entity_txt.append("\t\t").append("if(param1<1)param=param1;").append("\r\n");
            entity_txt.append("\t\t").append("return param;").append("\r\n");
            entity_txt.append("\t").append("}").append("\r\n");
            entity_txt.append("\r\n");


            entity_txt.append("\t").append("public int save(" + entity_name + " obj){").append("\r\n");
            entity_txt.append("\t\t").append("int param=1;").append("\r\n");
            entity_txt.append("\t\t").append("int param1=" + entity_name.toLowerCase() + "Dao.save(obj);").append("\r\n");
            entity_txt.append("\t\t").append("if(param1<1) param=param1;").append("\r\n");
            entity_txt.append("\t\t").append("return param;").append("\r\n");
            entity_txt.append("\t").append("}").append("\r\n");


            entity_txt.append("\t").append("public int delete(String idArray) {").append("\r\n");
            entity_txt.append("\t\t").append("int param=1;").append("\r\n");
            entity_txt.append("\t\t").append("String[] id_Array = idArray.split(\"-\");").append("\r\n");
            entity_txt.append("\t\t").append("if(id_Array.length>1){").append("\r\n");
            entity_txt.append("\t\t\t").append("for (int i = 0; i < id_Array.length; i++) {").append("\r\n");
            entity_txt.append("\t\t\t\t").append("int param1 = " + entity_name.toLowerCase() + "Dao.delete(Integer.parseInt(id_Array[i]));").append("\r\n");
            entity_txt.append("\t\t\t\t").append("if(param1<1) param=param1;").append("\r\n");
            entity_txt.append("\t\t\t").append("}").append("\r\n");
            entity_txt.append("\t\t").append("}else{").append("\r\n");
            entity_txt.append("\t\t\t").append("int param1=" + entity_name.toLowerCase() + "Dao.delete(Integer.parseInt(idArray));").append("\r\n");
            entity_txt.append("\t\t\t").append("if(param1<1) param=param1;").append("\r\n");
            entity_txt.append("\t\t").append("}").append("\r\n");
            entity_txt.append("\t\t").append("return param;").append("\r\n");
            entity_txt.append("\t").append("}").append("\r\n");
        }

        entity_txt.append("\r\n");
        entity_txt.append("}").append("\r\n"); // 类结束

        writeEntity(newFile, entity_txt.toString());
    }


    //	生成action的代码
    private void action(String path, String pak) {
        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        String name = entity_name + "Action"; // 类名
        String newFile = locahost + path + name + ".java"; // 要生成的实体类

        entity_txt.append("package ").append(pak).append(";\r\n"); // 包名

        entity_txt.append("\r\n");

        entity_txt.append("import ky.entity." + entity_name + ";").append("\r\n");
        entity_txt.append("import ky.service." + entity_name + "Service;").append("\r\n");
        entity_txt.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        entity_txt.append("import java.util.Date;").append("\r\n");
        entity_txt.append("import java.util.List;").append("\r\n");
        entity_txt.append("import java.util.Map;").append("\r\n");
        entity_txt.append("import ky.util.PageView;").append("\r\n");

        entity_txt.append("import org.apache.struts2.ServletActionContext;").append("\r\n");
        entity_txt.append("import org.apache.struts2.convention.annotation.Action;").append("\r\n");
        entity_txt.append("import org.apache.struts2.convention.annotation.Namespace;").append("\r\n");
        entity_txt.append("import org.apache.struts2.convention.annotation.ParentPackage;").append("\r\n");
        entity_txt.append("import org.apache.struts2.convention.annotation.Result;").append("\r\n");
        entity_txt.append("import java.io.UnsupportedEncodingException;").append("\r\n");
        entity_txt.append("\r\n");


        // 表注释
        entity_txt.append("/**").append("\r\n");
        entity_txt.append("* ********************************************************").append("\r\n");
        entity_txt.append("* @ClassName: ").append(name).append("\r\n");
        entity_txt.append("* @Description: ").append(comments_table).append("\r\n");
        entity_txt.append("* @author 生成Action类").append("\r\n");
        entity_txt.append("* @date ").append(new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss ").format(new Date())).append("\r\n");
        entity_txt.append("*******************************************************").append("\r\n");
        entity_txt.append("*/").append("\r\n");


        entity_txt.append("@ParentPackage(" + "\"" + "tiles-default\")").append("\r\n");
        entity_txt.append("@Namespace(\"/" + entity_name.toLowerCase() + "\")").append("\r\n");

        entity_txt.append("public class ").append(name + " extends BaseAction<" + entity_name + ">{").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("\t").append("@Autowired()").append("\r\n");
        entity_txt.append("\t").append("private " + entity_name + "Service " + entity_name + "Ser;").append("\r\n");


        entity_txt.append("\t").append("@Action(value = \"selectPage\", results = {@Result(name = \"selectPage\", location = \"/pages/" + entity_name + "/list.jsp\") })").append("\r\n");
        entity_txt.append("\t").append("public String seletePage(){").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("\t\t").append("//接受前台参数，并进行数据查询").append("\r\n");
        entity_txt.append("\t\t").append("PageView pageView = " + entity_name + "Ser.selectPage(this.pageParam(\"page\", \"rows\",\"order\", \"sort\",model ));").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("\t\t").append("//返回数据到界面").append("\r\n");
        entity_txt.append("\t\t").append("this.returnPageInfo(pageView);").append("\r\n");
        entity_txt.append("\r\n");
        entity_txt.append("\t\t").append("return \"selectPage\";").append("\r\n");
        entity_txt.append("\t").append("}").append("\r\n");
        entity_txt.append("\r\n");


        entity_txt.append("\t").append("@Action(value = \"select\", results = {@Result(name = \"select\", location = \"/pages/" + entity_name + "/list.jsp\") })").append("\r\n");
        entity_txt.append("\t").append("public String selete(){").append("\r\n");
        entity_txt.append("\r\n");
        entity_txt.append("\t\t").append("List<" + entity_name + "> list = " + entity_name + "Ser.selectList(model);").append("\r\n");
        entity_txt.append("\t\t").append("this.jsonArray(list);").append("\r\n");
        entity_txt.append("\t\t").append("return \"select\";").append("\r\n");
        entity_txt.append("\t").append("}").append("\r\n");
        entity_txt.append("\r\n");


        if (!isTable.equals("VIEW")) {
            entity_txt.append("\t").append("@Action(value = \"save\", results = {@Result(name = \"save\", location = \"/pages/" + entity_name + "/list.jsp\") })").append("\r\n");
            entity_txt.append("\t").append("public String save(){").append("\r\n");
            entity_txt.append("\r\n");
            entity_txt.append("\t\t").append("this.returnU_D_S_info(" + entity_name + "Ser.save(model));").append("\r\n");
            entity_txt.append("\t\t").append("return \"save\";").append("\r\n");
            entity_txt.append("\t").append("}").append("\r\n");
            entity_txt.append("\r\n");

            entity_txt.append("\t").append("@Action(value = \"detele\", results = {@Result(name = \"detele\", location = \"/pages/" + entity_name + "/list.jsp\") })").append("\r\n");
            entity_txt.append("\t").append("public String detele() throws UnsupportedEncodingException{").append("\r\n");
            entity_txt.append("\r\n");
            entity_txt.append("\t\t").append("String idArray=this.getRequest(\"idArray\");//获得前台复选框的值").append("\r\n");
            entity_txt.append("\t\t").append("this.returnU_D_S_info(" + entity_name + "Ser.delete(idArray));").append("\r\n");
            entity_txt.append("\t\t").append("return \"detele\";").append("\r\n");
            entity_txt.append("\t").append("}").append("\r\n");
            entity_txt.append("\r\n");

            entity_txt.append("\t").append("@Action(value = \"update\", results = {@Result(name = \"update\", location = \"/pages/" + entity_name + "/list.jsp\") })").append("\r\n");
            entity_txt.append("\t").append("public String update(){").append("\r\n");
            entity_txt.append("\r\n");
            entity_txt.append("\t\t").append("this.returnU_D_S_info(" + entity_name + "Ser.update(model));").append("\r\n");
            entity_txt.append("\t\t").append("return \"update\";").append("\r\n");
            entity_txt.append("\t").append("}").append("\r\n");
            entity_txt.append("\r\n");
        }

        entity_txt.append("}").append("\r\n"); // 类结束
        writeEntity(newFile, entity_txt.toString());
    }


    public void listHtml() {
        String params = "";
        String upParams = "";
        String pk_name = rt.getPK(table_name.toUpperCase());
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            if (pk_name.equals(h_m.getKey())) {
                pk_name = "";
                pk_name = h_m.getValue()[0][0]; // 属性信息(id)
            }
        }


        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            if (pk_name != h_m.getValue()[0][0]) {
                params += "'" + h_m.getValue()[0][0] + "',";
            }
            upParams += "'up_" + h_m.getValue()[0][0] + "',";
        }

        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        entity_txt.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>  ").append("\r\n");
        entity_txt.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>  ").append("\r\n");
        entity_txt.append("<%@ include file=\"/WEB-INF/common/commons.jspf\" %>").append("\r\n");
        entity_txt.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd\"> ").append("\r\n");
        entity_txt.append("<HTML xmlns=\"http://www.w3.org/1999/xhtml\"> ").append("\r\n");
        entity_txt.append("\t").append("<HEAD>  ").append("\r\n");
        entity_txt.append("\t").append("    <meta charset=\"UTF-8\">  ").append("\r\n");
        entity_txt.append("\t").append("	<title>后台菜单</title>  ").append("\r\n");

        entity_txt.append("\t").append("</HEAD>     ").append("\r\n");
        entity_txt.append("\t").append("<script type=\"text/javascript\">").append("\r\n");

        entity_txt.append("\t").append("	$(function () {").append("\r\n");
        entity_txt.append("\t").append("		//关闭修改界面时,刷新列表").append("\r\n");
        entity_txt.append("\t").append("		$(\"#" + entity_name + "UpdateDiv\").window({").append("\r\n");
        entity_txt.append("\t").append("       		onBeforeClose: function () { //当面板关闭之前触发的事件").append("\r\n");
        entity_txt.append("\t").append("         		 $('#" + entity_name + "Tab').datagrid('load');  ").append("\r\n");
        entity_txt.append("\t").append("                 $(\"#update_msg\").html(\"&nbsp;\");").append("\r\n");
        entity_txt.append("\t").append("       		}").append("\r\n");
        entity_txt.append("\t").append(" 		});").append("\r\n");
        entity_txt.append("\t").append("		//关闭添加界面时,刷新列表").append("\r\n");
        entity_txt.append("\t").append("		$('#" + entity_name + "SaveDiv').window({").append("\r\n");
        entity_txt.append("\t").append("      		 onBeforeClose: function () { //当面板关闭之前触发的事件").append("\r\n");
        entity_txt.append("\t").append("         		 $('#" + entity_name + "Tab').datagrid('load');  ").append("\r\n");
        entity_txt.append("\t").append("                 $(\"#add_msg\").html(\"&nbsp;\");").append("\r\n");
        entity_txt.append("\t").append("		     }").append("\r\n");
        entity_txt.append("\t").append("	    });").append("\r\n");
        entity_txt.append("\t").append("    });").append("\r\n");
        entity_txt.append("\t").append("</script>  ").append("\r\n");
        entity_txt.append("\t").append("<BODY>   ").append("\r\n");
        entity_txt.append("\t").append("	<table id=\"" + entity_name + "Tab\" class=\"easyui-datagrid\" data-options=\"url:'/APP/" + entity_name.toLowerCase() + "/selectPage.action',pageList:[10,20,22,30,40] , ").append("\r\n");
        entity_txt.append("\t").append("	       singleSelect:true,fit:true,fitColumns:true,toolbar:'#tb',pagination:true,idField:'" + pk_name + "',singleSelect:false,pageSize:22\" >  ").append("\r\n");
        entity_txt.append("\t").append("		   <thead>   ").append("\r\n");
        entity_txt.append("\t").append("		          <tr>   ").append("\r\n");
        entity_txt.append("\t").append("                        <th data-options=\"field:'ck',checkbox:true\"></th>").append("\r\n");
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            entity_txt.append("\t\t").append("		            <th data-options=\"field:'" + h_m.getValue()[0][0] + "',sortable:true\" width=\"20\">" + h_m.getValue()[0][0] + "</th> ").append("\r\n");
        }
        entity_txt.append("\t").append("		          </tr>   ").append("\r\n");
        entity_txt.append("\t").append("		   </thead>   ").append("\r\n");
        entity_txt.append("\t").append("     </table>   ").append("\r\n");
        entity_txt.append("\r\n");


        entity_txt.append("\t").append("     <!-- 搜索div,功能div -->").append("\r\n");
        entity_txt.append("\t").append("     <div id=\"tb\">").append("\r\n");
        entity_txt.append("\t").append("          <form method=\"post\" id=\"" + entity_name + "SearchForm\">").append("\r\n");

        entity_txt.append("\t").append("               <div  class=\"formDiv\">").append("\r\n");
        entity_txt.append("\t").append("         			<ul >").append("\r\n");
        entity_txt.append("\t").append("         			 	<li>").append("\r\n");
        entity_txt.append("\t").append("      						字段：<INPUT class=textfield size=18  name=\"\" >  ").append("\r\n");
        entity_txt.append("\t").append("     		     	 	</li> ").append("\r\n");
        entity_txt.append("\t").append("     				  	<li >").append("\r\n");
        entity_txt.append("\t").append("     		     			字段：<INPUT class=textfield size=18  name=\"\" > ").append("\r\n");
        entity_txt.append("\t").append("     		     	  	</li>").append("\r\n");
        entity_txt.append("\t").append("		     		  	<li >").append("\r\n");
        entity_txt.append("\t").append("		     			         字段：<INPUT class=textfield size=18  name=\"\" > ").append("\r\n");
        entity_txt.append("\t").append("		     		  	</li>").append("\r\n");
        entity_txt.append("\t").append("		     			<li>").append("\r\n");
        entity_txt.append("\t").append("		     			      <a class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\" href=\"javascript:void(0)\"  onclick=\"FormData('" + entity_name + "Tab','" + entity_name + "SearchForm')\">查询</a>").append("\r\n");
        entity_txt.append("\t").append("		     			</li>").append("\r\n");
        entity_txt.append("\t").append("	     	    	</ul>").append("\r\n");
        entity_txt.append("\t").append("			   </div>").append("\r\n");
        entity_txt.append("\t").append("          </form>").append("\r\n");
        entity_txt.append("\t").append("		<div class=\"operate\">").append("\r\n");
        entity_txt.append("\t").append("			<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-add\" plain=\"true\" onclick=\"saveOpen('" + entity_name + "SaveDiv','" + entity_name + "SaveIframe')\">新增</a>").append("\r\n");
        entity_txt.append("\t").append("			<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-edit\" plain=\"true\" onclick=\"updateOpen('" + entity_name + "Tab','" + entity_name + "UPdateDiv','" + entity_name + "UPdateIframe','" + pk_name + "')\">修改(详细)</a>").append("\r\n");
        entity_txt.append("\t").append("			<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-cut\" plain=\"true\" onclick=\"delete_info('" + entity_name + "Tab','" + entity_name.toLowerCase() + "','" + pk_name + "')\">删除</a>").append("\r\n");
        entity_txt.append("\t").append("		</div>").append("\r\n");
        entity_txt.append("\t").append("   </div>").append("\r\n");
        entity_txt.append("\r\n");

        if (!isTable.equals("VIEW")) {
            entity_txt.append("\t").append("    <!-- 添加界面 -->").append("\r\n");
            entity_txt.append("\t").append("    <div id=\"" + entity_name + "SaveDiv\" class=\"easyui-window\" closed=\"true\" modal=\"true\" iconCls=\"icon-add\" title=\"添加\" style=\"width:530px;height:350px;text-align: center;\">").append("\r\n");
            entity_txt.append("\t").append("		<iframe scrolling=\"auto\" id=\"" + entity_name + "SaveIframe\" frameborder=\"0\"   style=\"width:97%;height:97%;margin-top: 1%;\"></iframe>").append("\r\n");
            entity_txt.append("\t").append("   </div>").append("\r\n");
            entity_txt.append("\r\n");

            entity_txt.append("\t").append("    <!-- 修改界面 -->").append("\r\n");
            entity_txt.append("\t").append("    <div id=\"" + entity_name + "UPdateDiv\" class=\"easyui-window\" closed=\"true\" modal=\"true\" iconCls=\"icon-edit\" title=\"修改\" style=\"width:530px;height:350px;text-align: center;\">").append("\r\n");
            entity_txt.append("\t").append("		<iframe scrolling=\"auto\" id=\"" + entity_name + "UPdateIframe\" frameborder=\"0\"   style=\"width:97%;height:97%;margin-top: 1%;\"></iframe>").append("\r\n");
            entity_txt.append("\t").append("   </div>").append("\r\n");
            entity_txt.append("\r\n");
        }

        entity_txt.append("\t").append("</BODY>").append("\r\n");
        entity_txt.append("</html>").append("\r\n");

        String file = address + webHtml + entity_name + "\\";
        createFile(file);      //创建文件夹
        writeEntity(file + "list.jsp", entity_txt.toString());
    }


    public void saveHtml() {
        String pk_name = rt.getPK(table_name.toUpperCase());
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            if (pk_name.equals(h_m.getKey())) {
                pk_name = "";
                pk_name = h_m.getValue()[0][0]; // 属性信息(id)
            }
        }
        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        entity_txt.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>  ").append("\r\n");
        entity_txt.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>  ").append("\r\n");
        entity_txt.append("<%@ include file=\"/WEB-INF/common/commons.jspf\" %>").append("\r\n");

        entity_txt.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd\"> ").append("\r\n");
        entity_txt.append("<HTML xmlns=\"http://www.w3.org/1999/xhtml\"> ").append("\r\n");
        entity_txt.append("\t").append("<HEAD>  ").append("\r\n");
        entity_txt.append("\t").append("    <meta charset=\"UTF-8\">  ").append("\r\n");
        entity_txt.append("\t").append("	<title>后台菜单</title>  ").append("\r\n");

        entity_txt.append("\t").append("</HEAD>     ").append("\r\n");

        entity_txt.append("\t").append("<BODY>   ").append("\r\n");


        entity_txt.append("\t\t").append("<!-- 添加界面 --> ").append("\r\n");
        entity_txt.append("\t\t").append("<div class=\"easyui-layout\" data-options=\"fit:true\">").append("\r\n");
        entity_txt.append("\t\t\t").append("<form id=\"" + entity_name + "SaveForm\" method=\"post\" > ").append("\r\n");
        entity_txt.append("\t\t\t\t").append("<div data-options=\"region:'center'\" style=\"padding:10px;\">").append("\r\n");

        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            if (pk_name != h_m.getValue()[0][0]) {
                entity_txt.append("\t\t\t\t\t").append("<p>" + h_m.getValue()[0][0] + ":&nbsp;&nbsp;<input class=\"easyui-validatebox\" data-options=\"required:true,missingMessage:'字段不可以为空'\" name=\"" + h_m.getValue()[0][0] + "\" ></p> ").append("\r\n");
            }
        }
        entity_txt.append("\t\t\t\t\t").append("<p ><span id=\"add_msg\" style=\"margin-left: 60px;color: green;font: 13px;font-weight: bolder;\">&nbsp;</span></p>").append("\r\n");
        entity_txt.append("\t\t\t\t").append("</div>").append("\r\n");


        entity_txt.append("\t\t\t\t").append("<div data-options=\"region:'south',border:false\" style=\"text-align:right;padding:10px;padding-bottom: 5px;\" >").append("\r\n");
        entity_txt.append("\t\t\t\t\t").append("<a class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-ok'\" href=\"javascript:void(0)\" style=\"margin-right: 15px \" onclick=\"add_save('" + entity_name + "SaveForm','" + entity_name.toLowerCase() + "')\">添加</a>").append("\r\n");
        entity_txt.append("\t\t\t\t\t").append("<a class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-cut'\" href=\"javascript:void(0)\" style=\"margin-right: 15px \" onclick=\"doReset('" + entity_name + "SaveForm')\">重置</a>").append("\r\n");
        entity_txt.append("\t\t\t\t").append("</div>").append("\r\n");


        entity_txt.append("\t\t\t").append("</form>").append("\r\n");
        entity_txt.append("\t\t").append("</div>").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("\t").append("</BODY>").append("\r\n");
        entity_txt.append("</html>").append("\r\n");

        String file = address + webHtml + entity_name + "\\";
        createFile(file);      //创建文件夹
        writeEntity(file + "save.jsp", entity_txt.toString());
    }


    public void updateHtml() {
        String pk_name = rt.getPK(table_name.toUpperCase());
        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            if (pk_name.equals(h_m.getKey())) {
                pk_name = "";
                pk_name = h_m.getValue()[0][0]; // 属性信息(id)
            }
        }

        StringBuffer entity_txt = new StringBuffer("\r\n"); // 要生成的信息
        entity_txt.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>  ").append("\r\n");
        entity_txt.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>  ").append("\r\n");
        entity_txt.append("<%@ include file=\"/WEB-INF/common/commons.jspf\" %>").append("\r\n");
        entity_txt.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd\"> ").append("\r\n");
        entity_txt.append("<HTML xmlns=\"http://www.w3.org/1999/xhtml\"> ").append("\r\n");
        entity_txt.append("\t").append("<HEAD>  ").append("\r\n");
        entity_txt.append("\t").append("    <meta charset=\"UTF-8\">  ").append("\r\n");
        entity_txt.append("\t").append("	<title>后台菜单</title>  ").append("\r\n");

        entity_txt.append("\t").append("</HEAD>     ").append("\r\n");

        entity_txt.append("\t").append("	<script type=\"text/javascript\">").append("\r\n");
        entity_txt.append("\t").append("		$(function () {").append("\r\n");
        entity_txt.append("\t").append("     		 $.ajax({").append("\r\n");
        entity_txt.append("\t").append("				type : 'post',").append("\r\n");
        entity_txt.append("\t").append("				dataType : 'json',").append("\r\n");
        entity_txt.append("\t").append("				url:'/APP/" + entity_name.toLowerCase() + "/select.action',").append("\r\n");
        entity_txt.append("\t").append("				data:{'" + pk_name + "':${param.pkName}},").append("\r\n");
        entity_txt.append("\t").append("				error : function() {// 请求失败处理函数").append("\r\n");
        entity_txt.append("\t").append("					alert('请求失败');").append("\r\n");
        entity_txt.append("\t").append("				},").append("\r\n");
        entity_txt.append("\t").append("				success : function(data) { // 请求成功后处理函数。").append("\r\n");
        entity_txt.append("\t").append("                	var inputValArray=$('#" + entity_name + "UpdateForm').serialize().split('&');").append("\r\n");
        entity_txt.append("\t").append("					for(var i=0;i<inputValArray.length;i++){").append("\r\n");
        entity_txt.append("\t").append("					     var inputName=inputValArray[i].split('=')[0];").append("\r\n");
        entity_txt.append("\t").append("					     $(\"#" + entity_name + "UpdateForm [name='\"+inputName+\"']\").val(row_info(data[0],inputName));").append("\r\n");
        entity_txt.append("\t").append("					}").append("\r\n");
        entity_txt.append("\t").append("				}").append("\r\n");
        entity_txt.append("\t").append("			});").append("\r\n");
        entity_txt.append("\t").append("     	});").append("\r\n");
        entity_txt.append("\t").append("	</script>  ").append("\r\n");


        entity_txt.append("\t").append("<BODY>   ").append("\r\n");
        entity_txt.append("\t\t").append("<!-- 修改界面 --> ").append("\r\n");
        entity_txt.append("\t\t").append("<div class=\"easyui-layout\" data-options=\"fit:true\">").append("\r\n");
        entity_txt.append("\t\t\t").append("<form id=\"" + entity_name + "UpdateForm\" method=\"post\" > ").append("\r\n");
        entity_txt.append("\t\t\t\t").append("<div data-options=\"region:'center'\" style=\"padding:10px;\">").append("\r\n");

        for (Map.Entry<String, String[][]> h_m : hibernate_map.entrySet()) {
            if (pk_name != h_m.getValue()[0][0]) {
                entity_txt.append("\t").append("		            <p>" + h_m.getValue()[0][0] + ":&nbsp;&nbsp;<input class=\"easyui-validatebox\" data-options=\"required:true,missingMessage:'字段不可以为空'\" name=\"" + h_m.getValue()[0][0] + "\" ></p> ").append("\r\n");
            } else {
                entity_txt.append("\t").append("		            <input type='hidden' name=\"" + h_m.getValue()[0][0] + "\" > ").append("\r\n");
            }
        }
        entity_txt.append("\t").append("                    <p ><span id=\"update_msg\" style=\"margin-left: 60px;color: green;font: 13px;font-weight: bolder;\">&nbsp;</span></p>").append("\r\n");
        entity_txt.append("\t\t\t\t").append("</div>").append("\r\n");


        entity_txt.append("\t\t\t\t").append("<div data-options=\"region:'south',border:false\" style=\"text-align:right;padding:10px;padding-bottom: 5px;\" >").append("\r\n");
        entity_txt.append("\t\t\t\t\t").append("<a class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-ok'\" href=\"javascript:void(0)\" onclick=\"update_save('" + entity_name + "UpdateForm','" + entity_name.toLowerCase() + "')\">修改</a>").append("\r\n");
        entity_txt.append("\t\t\t\t").append("</div>").append("\r\n");


        entity_txt.append("\t\t\t").append("</form>").append("\r\n");
        entity_txt.append("\t\t").append("</div>").append("\r\n");
        entity_txt.append("\r\n");

        entity_txt.append("\t").append("</BODY>").append("\r\n");
        entity_txt.append("</html>").append("\r\n");

        String file = address + webHtml + entity_name + "\\";
        createFile(file);      //创建文件夹
        writeEntity(file + "update.jsp", entity_txt.toString());
    }


    //读取指定类中的java代码
    public String readFile(String fileName) {
        String output = "";

        File file = new File(fileName);

        if (file.exists()) {
            if (file.isFile()) {
                try {
                    BufferedReader input = new BufferedReader(new FileReader(file));
                    StringBuffer buffer = new StringBuffer();
                    String text;

                    while ((text = input.readLine()) != null)
                        buffer.append(text + "\r\n");
                    output = buffer.toString();
                } catch (IOException ioException) {
                    System.err.println("File Error!");
                }
            } else if (file.isDirectory()) {
                String[] dir = file.list();
                output += "Directory contents:\r\n";

                for (int i = 0; i < dir.length; i++) {
                    output += dir[i] + "\r\n";
                }
            }
        } else {
            System.err.println("Does not exist!");
        }
        return output;
    }

    /**
     * ********************************************************
     *
     * @param file void
     * @Title: createFile
     * @Description: 生成文件夹
     * @date 2013-1-5 下午04:25:32
     * *******************************************************
     */
    private void createFile(String fileway) {
        File file = new File(fileway);
        file.mkdirs();
        file.mkdir();
    }

}
