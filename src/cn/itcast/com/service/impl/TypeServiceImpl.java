package cn.itcast.com.service.impl;

import cn.itcast.com.dao.impl.TypeDaoImpl;
import cn.itcast.com.domain.Type;
import cn.itcast.com.service.TypeService;

import java.util.List;


public class TypeServiceImpl implements TypeService {

    private TypeDaoImpl typeDao = new TypeDaoImpl();

    @Override
    public List<Type> findAll() {
        return typeDao.findAll();
    }
}
