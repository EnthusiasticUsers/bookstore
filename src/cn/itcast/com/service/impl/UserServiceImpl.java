package cn.itcast.com.service.impl;

import cn.itcast.com.dao.impl.UserDaoImpl;
import cn.itcast.com.domain.User;
import cn.itcast.com.service.UserService;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public boolean login(String username, String password) {
        return userDao.login(username,password);
    }

    @Override
    public boolean register(User user) throws SQLException {
        return userDao.register(user);
    }

    @Override
    public Integer selectId(String username, String password) {
        return userDao.selectId(username, password);
    }

}
