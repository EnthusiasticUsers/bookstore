package cn.itcast.com.dao;

import cn.itcast.com.domain.Book;
import java.util.List;

public interface BookDao {

    //获取图书信息
    public List<Book> selectByKey(String key);

    //获取指定图书信息
    public List<Book> selectById(Integer id);

    //增加图书信息
    public boolean add(Book book);

    //更改图书信息
    public boolean update(Book book);

    //删除图书信息
    public boolean delete(Integer id);

    //批量删除图书信息
    public boolean batchDelete(List<Book> books);



}
