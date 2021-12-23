package cn.itcast.com.service;

import cn.itcast.com.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    public User login(String username,String password);

    public boolean register(User user) throws SQLException;

    public Integer selectId(String username,String password);

    public User selectById(Integer id);

    public List<User> show();

    //修改用户
    public boolean update(User user);

    //删除用户
    public boolean delete(Integer id);
}
