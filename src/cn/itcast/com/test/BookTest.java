package cn.itcast.com.test;


import cn.itcast.com.domain.Book;
import cn.itcast.com.service.impl.BookServiceImpl;
import org.junit.Test;

import java.util.List;

public class BookTest {

    private BookServiceImpl bookService = new BookServiceImpl();

    @Test
    public void test01(){
        List<Book> books = bookService.selectByKey("美学");
        for(Book book : books){
            System.out.println(book);
        }
    }

    @Test
    public void test02(){
        List<Book> books = bookService.selectById(1);
        for(Book book : books){
            System.out.println(book);
        }
    }

    @Test
    public void test03(){
        Integer id = 5;
        String name = "那些年一起追过的女孩";
        Double price = 56.9;
        String image = "bookonline08.jpg";
        String type = "5";
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setPrice(price);
        book.setImage(image);
        book.setType(type);
        boolean count = bookService.update(book);
        System.out.println(count);
    }

}
