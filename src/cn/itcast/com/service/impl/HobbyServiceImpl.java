package cn.itcast.com.service.impl;

import cn.itcast.com.dao.impl.HobbyDaoImpl;
import cn.itcast.com.domain.Hobby;
import cn.itcast.com.service.HobbyService;

import java.util.List;

public class HobbyServiceImpl implements HobbyService {

    private HobbyDaoImpl hobbyDao = new HobbyDaoImpl();

    @Override
    public List<Hobby> findAll() {
        return  hobbyDao.findAll();
    }
}
