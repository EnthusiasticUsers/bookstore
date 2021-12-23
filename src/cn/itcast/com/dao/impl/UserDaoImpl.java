package cn.itcast.com.dao.impl;

import cn.itcast.com.dao.UserDao;
import cn.itcast.com.domain.Hobby;
import cn.itcast.com.domain.User;
import cn.itcast.com.util.DruidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());

    @Override
    public List<User> show() {
       String sql = "select * from user";
       return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User login(String username, String password) {
        try {
            String sql = "select * from user where username = ? and password = password(?) and power = 1";
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
            if(user != null) {
                user.setPassword(password);
                return user;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean register(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement ptmt = null;
        try {
            conn = DruidUtil.getConnection();
            conn.setAutoCommit(false);//开启事务
            String sql1 = "insert into user(username,password,sex,degree,portrait) values(?,password(?),?,?,?)";
            ptmt = conn.prepareStatement(sql1);
            ptmt.setString(1,user.getUsername());
            ptmt.setString(2,user.getPassword());
            ptmt.setInt(3,user.getSex());
            ptmt.setInt(4,user.getDegree());
            ptmt.setString(5,user.getPortrait());
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
                return false;
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
        try {
            String sql = "select id from user where username = ? and password = password(?)";
            return jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Integer getNextId() {
        try {
            String sql = "select max(id) from user";
            Integer num = jdbcTemplate.queryForObject(sql, Integer.class);
            if(num == null) {
                return 1;
            } else {
                return num + 1;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return 1;
    }


    @Override
    public User selectById(Integer id){
        String sql = "select id,username,password from user where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    @Override
    public boolean update(User user) {
        String sql = "";
        if(user.getUsername() != null && user.getPassword() != null){
            sql = "update user set username = ?,password = password(?) where id = ?";
            int count = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getId());
            return count > 0;
        }else{
            sql = "update user set power = ? where id = ?";
            int count = jdbcTemplate.update(sql, user.getPower(), user.getId());
            return count > 0;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            String sql1 = "delete from user where id = ?";
            int count1 = jdbcTemplate.update(sql1, id);
            String sql2 = "delete from user_hobby where uid = ?";
            int count2 = jdbcTemplate.update(sql2, id);
            return count1 > 0;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


}
