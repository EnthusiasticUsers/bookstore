package cn.itcast.com.dao.impl;

import cn.itcast.com.dao.BoardDao;
import cn.itcast.com.domain.Board;
import cn.itcast.com.util.DruidUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BoardDaoImpl implements BoardDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());

    @Override
    public boolean insert(Board board) {
        String sql = "insert into board(title,author,content) values(?,?,?)";
        int count =  jdbcTemplate.update(sql, board.getTitle(), board.getAuthor(), board.getContent());
        return count > 0;
    }

    @Override
    public List<Board> selectByKey(String key) {
        if(key == null) key = "%%";
        else key = "%" + key + "%";
        String sql = "select b.id,b.title,b.content,b.thumbs_up,b.thumbs_down,b.time,u.username author,u.portrait " +
                     "from " +
                     "board b,user u " +
                     "where " +
                     "b.author = u.id and (b.title like ? or u.username like ? or b.content like ?)";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Board.class), key, key, key);
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "delete from board where id = ?";
        int count = jdbcTemplate.update(sql, id);
        return count > 0;
    }

    @Override
    public boolean batchDelete(List<Board> boards) {
        boolean isDel = true;
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
