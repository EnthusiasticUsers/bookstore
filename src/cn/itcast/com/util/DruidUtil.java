package cn.itcast.com.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DruidUtil {
    public static DataSource ds;

    static {
        InputStream is = DruidUtil.class.getClassLoader().getResourceAsStream("conn.properties");
        Properties pro = new Properties();
        try {
            pro.load(is);
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource(){
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void close(Statement stmt, Connection conn){
        close(stmt,conn,null);
    }

    public static void close(Statement stmt, Connection conn, ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        if(stmt != null){
            try{
                stmt.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

    }

}
