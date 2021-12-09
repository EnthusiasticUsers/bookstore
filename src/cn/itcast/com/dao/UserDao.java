package cn.itcast.com.dao;

import cn.itcast.com.domain.User;

import java.sql.SQLException;

public interface UserDao {

    //登录验证
    public User login(String username,String password);

    //注册
    public boolean register(User user) throws SQLException;

    //用户ID
    public Integer selectId(String username,String password);

    //获取最大用户下一个ID
    public Integer getNextId();
}
