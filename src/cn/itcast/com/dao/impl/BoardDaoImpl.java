package cn.itcast.com.dao.impl;

import cn.itcast.com.dao.BoardDao;
import cn.itcast.com.domain.Board;
import cn.itcast.com.util.DruidUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BoardDaoImpl implements BoardDao {

    @Override
    public boolean insert(Board board) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        String sql = "insert into board(title,author,content) values(?,?,?)";
        int count =  jdbcTemplate.update(sql, board.getTitle(), board.getAuthor(), board.getContent());
        return count > 0;
    }

    @Override
    public List<Board> selectByKey(String key) {
        if(key == null) key = "%%";
        else key = "%" + key + "%";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        String sql = "select * from board where title like ? or author like ? or content like ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Board.class), key, key, key);
    }

    @Override
    public boolean delete(Integer id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        String sql = "delete from board where id = ?";
        int count = jdbcTemplate.update(sql, id);
        return count > 0;
    }

    @Override
    public boolean batchDelete(List<Board> boards) {
        boolean isDel = true;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        String sql = "delete from board where id = ?";
        for(Board board : boards){
            int count = jdbcTemplate.update(sql, board.getId());
            if(count < 0){
                isDel = false;
            }
        }
        return isDel;
    }
}
