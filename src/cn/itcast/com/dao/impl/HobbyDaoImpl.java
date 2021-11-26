package cn.itcast.com.dao.impl;

import cn.itcast.com.dao.HobbyDao;
import cn.itcast.com.domain.Hobby;
import cn.itcast.com.util.DruidUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class HobbyDaoImpl implements HobbyDao {

    @Override
    public List<Hobby> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        String sql = "select * from hobby";
        List<Hobby> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Hobby.class));
        return list;
    }
}
