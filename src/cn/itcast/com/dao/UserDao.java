package cn.itcast.com.dao;

import cn.itcast.com.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    //登录验证
    public User login(String username,String password);

    //注册
    public boolean register(User user) throws SQLException;

    //用户ID
    public Integer selectId(String username,String password);

    //获取单个用户
    public User selectById(Integer id);

    //获取最大用户下一个ID
    public Integer getNextId();

    //获取所有用户
    public List<User> show();

    //修改用户
    public boolean update(User user);

    //删除用户
    public boolean delete(Integer id);
}
