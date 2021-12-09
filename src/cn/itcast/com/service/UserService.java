package cn.itcast.com.service;

import cn.itcast.com.domain.User;

import java.sql.SQLException;

public interface UserService {

    public User login(String username,String password);

    public boolean register(User user) throws SQLException;

    public Integer selectId(String username,String password);
}
