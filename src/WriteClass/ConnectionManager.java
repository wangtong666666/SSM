package WriteClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionManager {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://118.190.202.28/WT_TEST?useUnicode=true&amp;characterEncoding=UTF-8";
    public static final String UID = "root";
    public static final String PWD = "875207";
    protected Connection conn;
    protected PreparedStatement pstmt;
    protected ResultSet rs;

    public Connection getConn() {
        try {
            Class.forName(DRIVER);
            this.conn = DriverManager.getConnection(URL, UID, PWD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.conn;
    }

    public void colseAll() {
        try {
            if (this.rs != null) {
                this.rs.close();
            }
            if (this.pstmt != null) {
                this.pstmt.close();
            }
            if (this.conn != null)
                this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}