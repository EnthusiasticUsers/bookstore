package cn.itcast.com.dao.impl;

import cn.itcast.com.dao.UserDao;
import cn.itcast.com.domain.Hobby;
import cn.itcast.com.domain.User;
import cn.itcast.com.util.DruidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean login(String username, String password) {
        Connection conn = null;
        PreparedStatement ptmt = null;
        ResultSet rs = null;
        try {
            conn = DruidUtil.getConnection();
            String sql = "select username,password from user where username = ? and password = ?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,username);
            ptmt.setString(2,password);
            rs = ptmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(ptmt,conn,rs);
        }

        return false;
    }

    @Override
    public boolean register(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement ptmt = null;
        try {
            conn = DruidUtil.getConnection();
            conn.setAutoCommit(false);//开启事务
            String sql1 = "insert into user(username,password,sex,degree) values(?,?,?,?)";
            ptmt = conn.prepareStatement(sql1);
            ptmt.setString(1,user.getUsername());
            ptmt.setString(2,user.getPassword());
            ptmt.setInt(3,user.getSex());
            ptmt.setInt(4,user.getDegree());
            int count = ptmt.executeUpdate();
            if(count > 0){
                Integer uid = getNextId();
                String sql2 = "insert into user_hobby(uid,hid) values(?,?)";
                //循环插入爱好
                for(Hobby hobby : user.getHobbies()){
                    ptmt = conn.prepareStatement(sql2);
                    ptmt.setInt(1,uid);
                    ptmt.setInt(2,hobby.getId());
                    ptmt.executeUpdate();
                }
            }else{
                System.out.println("错误:找不到账户");
            }
            //若以上操作都成功则提交事务
            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();//失败则回滚事务
            e.printStackTrace();
        }finally {
            DruidUtil.close(ptmt,conn);
        }
        return false;
    }

    @Override
    public Integer selectId(String username, String password) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        try {
            String sql = "select id from user where username = ? and password = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Integer getNextId() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        try {
            String sql = "select max(id) from user";
            Integer num = jdbcTemplate.queryForObject(sql, Integer.class);
            return num+1;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
