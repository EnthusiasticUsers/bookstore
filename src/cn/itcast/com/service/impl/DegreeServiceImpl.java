package cn.itcast.com.service.impl;

import cn.itcast.com.dao.impl.DegreeDaoImpl;
import cn.itcast.com.dao.impl.UserDaoImpl;
import cn.itcast.com.domain.Degree;
import cn.itcast.com.service.DegreeService;

import java.util.List;

public class DegreeServiceImpl implements DegreeService {

    private DegreeDaoImpl degreeDao = new DegreeDaoImpl();

    @Override
    public List<Degree> findAll() {
        return degreeDao.findAll();
    }
}
