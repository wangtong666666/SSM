package WriteClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * ********************************************************
 *
 * @author DoDo
 * @ClassName: ReadTable
 * @Description: 读取表属性
 * @date 2012-12-26 下午05:36:42
 * ******************************************************
 */
public class ReadTable {

    ConnectionManager db = new ConnectionManager();


    /**
     * ********************************************************
     *
     * @param table
     * @return Map
     * @Title: read
     * @Description: 获取表字段名和数据类型
     * @date 2012-12-26 下午05:37:20
     * *******************************************************
     */
    public Map read(String table) {
        Map<String, String[][]> map = new HashMap<String, String[][]>();
        Connection conn = db.getConn();
        PreparedStatement pstmt = null;
        ResultSetMetaData rsmd = null;
        String findsql = "select * from " + table;

        try {
            pstmt = conn.prepareStatement(findsql);
//			pstmt.execute();    // 这点特别要注意:如果是Oracle  而对于mysql可以不用加.
            rsmd = (ResultSetMetaData) pstmt.getMetaData();

            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {

                map.put(rsmd.getColumnName(i).toLowerCase(), new String[][]{{rsmd.getColumnTypeName(i).toLowerCase(), rsmd.getScale(i) + ""}});

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {

            }
        }
        return map;
    }


    /**
     * ********************************************************
     *
     * @param table
     * @param attribute
     * @return boolean
     * @Title: findDecimals
     * @Description: 查询小数位数
     * @date 2012-12-27 上午11:49:35
     * *******************************************************
     */
    public int findDecimals(String table, String attribute) {
//		String sql = "SELECT nvl(data_scale,0) FROM ALL_TAB_COLUMNS WHERE OWNER= (select default_tablespace from user_users) and data_type = 'NUMBER' and table_name = '"+table.toUpperCase()+"'  and column_name = '"+attribute.toUpperCase()+"' ";
        String sql = "SELECT NUMERIC_SCALE FROM information_schema. COLUMNS where (DATA_TYPE = 'double' or DATA_TYPE = 'float') and COLUMN_NAME = '" + attribute.toUpperCase() + "' and TABLE_NAME = '" + table.toUpperCase() + "'";
        Connection conn = db.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int num = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return num;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {

            }
        }
        return num;
    }


    /**
     * ********************************************************
     *
     * @param table 表名
     * @return String
     * @Title: findTableComments
     * @Description: 查询 Oracle 数据库 Comments 表说明
     * @date 2012-12-27 下午05:55:44
     * *******************************************************
     */
    public String findTableComments(String table) {
        String sql = "select IFNULL(COLUMN_NAME,'') from information_schema.COLUMNS where table_name='" + table.toUpperCase() + "'";
        Connection conn = db.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String comments = "";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                comments = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {

            }
        }
        return comments;
    }

    /**
     * ********************************************************
     *
     * @param table
     * @return Map
     * @Title: findColComment
     * @Description: 查询该表所有属性的说明
     * @date 2012-12-27 下午05:59:25
     * *******************************************************
     */
    public Map findColComment(String table) {
        Map<String, String> map = new HashMap<String, String>();
        Connection conn = db.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String findsql = "select column_name,IFNULL(column_comment,'') from information_schema.COLUMNS where table_name='" + table.toUpperCase() + "'";
        try {
            pstmt = conn.prepareStatement(findsql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                map.put(rs.getString(1).toLowerCase(), rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {

            }
        }
        return map;
    }


    /**
     * ********************************************************
     *
     * @param table
     * @return Map
     * @Title: findColComment
     * @Description: 查询该表的主键名称
     * @date 2012-12-27 下午05:59:25
     * *******************************************************
     */

    public String getPK(String table_name) {

        String pk_name = "";
        Connection conn = db.getConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
//		String sql="select  col.column_name as name from user_constraints con,user_cons_columns col where con.constraint_name=col.constraint_name and con.constraint_type='P' and col.table_name='"+table_name.toUpperCase()+"'";
        String sql = "select COLUMN_NAME as name from INFORMATION_SCHEMA.COLUMNS where COLUMN_KEY='PRI' AND table_name='" + table_name.toUpperCase() + "'";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                pk_name = rs.getString("name").toLowerCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {

            }
        }
        return pk_name;
    }
}
