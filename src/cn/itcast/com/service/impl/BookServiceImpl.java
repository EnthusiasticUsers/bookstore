package cn.itcast.com.service.impl;

import cn.itcast.com.dao.impl.BookDaoImpl;
import cn.itcast.com.service.BookService;
import cn.itcast.com.domain.Book;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDaoImpl bookDao = new BookDaoImpl();

    @Override
    public List<Book> selectByKey(String key) {
        return bookDao.selectByKey(key);
    }

    @Override
    public List<Book> selectById(Integer id) {
        return bookDao.selectById(id);
    }

    @Override
    public boolean add(Book book) {
        return bookDao.add(book);
    }

    @Override
    public boolean update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public boolean delete(Integer id) {
        return bookDao.delete(id);
    }

    @Override
    public boolean batchDelete(List<Book> books) {
        return bookDao.batchDelete(books);
    }
}
