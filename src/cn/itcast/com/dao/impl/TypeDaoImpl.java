package cn.itcast.com.dao.impl;

import cn.itcast.com.dao.TypeDao;
import cn.itcast.com.domain.Type;
import cn.itcast.com.util.DruidUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TypeDaoImpl implements TypeDao {


    @Override
    public List<Type> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        String sql = "select * from type";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Type.class));
    }
}
