package cn.itcast.com.dao.impl;

import cn.itcast.com.dao.BookDao;
import cn.itcast.com.util.DruidUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.itcast.com.domain.Book;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());

    @Override
    public List<Book> selectByKey(String key) {
        if(key == null) key = "%%";
        else key = "%" + key + "%";
        String sql = "select " +
                     "book.id id,name,price,image,type.type type " +
                     "from " +
                     "book,type " +
                     "where " +
                     "book.type = type.id " +
                     "and " +
                     "(name like ? or price like ? or image like ? or type.type like ?)";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class), key, key, key, key);
    }

    @Override
    public List<Book> selectById(Integer id) {
        String sql = "select * from book where id = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Book.class), id);
    }

    @Override
    public boolean add(Book book) {
        String sql = "insert into book(name,price,image,type) values(?,?,?,?)";
        int count = jdbcTemplate.update(sql, book.getName(), book.getPrice(), book.getImage(), book.getType());
        return count > 0;
    }

    @Override
    public boolean update(Book book) {
        String sql = "update book set name=?, price=?, image=?, type=? where id=?";
        int count = jdbcTemplate.update(sql, book.getName(), book.getPrice(), book.getImage(), book.getType(), book.getId());
        return count > 0;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "delete from book where id = ?";
        int count = jdbcTemplate.update(sql, id);
        return count > 0;
    }

    @Override
    public boolean batchDelete(List<Book> books) {
        boolean isDel = true;
        String sql = "delete from book where id = ?";
        for(Book book : books){
            int count = jdbcTemplate.update(sql, book.getId());
            if(count < 0){
                isDel = false;
            }
        }
        return isDel;
    }
}
