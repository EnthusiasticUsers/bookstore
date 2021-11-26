package cn.itcast.com.dao.impl;

import cn.itcast.com.dao.DegreeDao;
import cn.itcast.com.domain.Degree;
import cn.itcast.com.util.DruidUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DegreeDaoImpl implements DegreeDao {

    @Override
    public List<Degree> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        String sql = "select * from degree";
        List<Degree> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Degree.class));
        return list;
    }
}
