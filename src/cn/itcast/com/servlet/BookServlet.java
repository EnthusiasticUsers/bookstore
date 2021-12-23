package cn.itcast.com.servlet;

import cn.itcast.com.domain.Book;
import cn.itcast.com.service.impl.BookServiceImpl;
import cn.itcast.com.util.JsonUtil;
import cn.itcast.com.util.StatusUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Random;


@MultipartConfig(location = "/")
@WebServlet("/book")
public class BookServlet extends HttpServlet {

    private BookServiceImpl bookService = new BookServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //获取所有参数
        String status = request.getParameter("status");
        status = new String(Base64.getDecoder().decode(status));
        String id = request.getParameter("id");
        String ids = request.getParameter("ids");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String type = request.getParameter("type");
        String key = request.getParameter("key");
        List<Book> books = null;
        //初始化返回json
        String json = "";

        //选择操作
        if ("show".equals(status)) {
            books = bookService.selectByKey(key);
            json = JsonUtil.objToStr(books);
        } else if ("add".equals(status)) {
            Part imageFile = request.getPart("imageFile");
            String image = getTheOnlyFileName(imageFile);
            Book book = getBook(null ,name, price, image, type, imageFile);
            boolean flag = bookService.add(book);
            json = flag ? StatusUtil.success("插入图书信息成功!") : StatusUtil.failed("插入图书信息失败!");
        } else if ("update".equals(status)) {
            Part imageFile = request.getPart("imageFile");
            String image = getTheOnlyFileName(imageFile);
            Book book = getBook(Integer.parseInt(id), name, price, image, type, imageFile);
            System.out.println(book);
            boolean flag = bookService.update(book);
            json = flag ? StatusUtil.success("修改图书信息成功!") : StatusUtil.failed("修改图书信息失败!");
        } else if ("delete".equals(status)) {
            boolean flag = bookService.delete(Integer.parseInt(id));
            json = flag ? StatusUtil.success("删除图书信息成功!") : StatusUtil.failed("删除图书信息失败!");
        } else if ("id".equals(status)) {
            books = bookService.selectById(Integer.parseInt(id));
            json = JsonUtil.objToStr(books);
        } else if ("batchDelete".equals(status)) {
            books = JsonUtil.strToObjList(ids, Book.class);
            boolean flag = bookService.batchDelete(books);
            json = flag ? StatusUtil.success("批量删除图书信息成功!") : StatusUtil.failed("批量删除图书信息失败!");
        }

        //返回json
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private Book getBook(Integer id, String name, String price, String image, String type, Part imageFile) throws IOException {
        //获取真实路径
        String path = getServletContext().getRealPath("/image");
        //设置图片url路径
        String urlPath = "image/";
        //写入文件
        writeToFile(path, image, imageFile);
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setPrice(Double.parseDouble(price));
        book.setImage(urlPath + image);
        book.setType(type);
        return book;
    }

    private void writeToFile(String path, String fileName, Part part) throws IOException {
        InputStream in = part.getInputStream();
        OutputStream out = new FileOutputStream(path + "//" + fileName);
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }

    private String getFileName(Part part) {
        String head = part.getHeader("Content-Disposition");
        return head.substring(head.indexOf("filename=\"") + 10, head.lastIndexOf("\""));
    }

    private String getTheOnlyFileName(Part part) {
        String fileName = getFileName(part);
        return System.currentTimeMillis() + new Random().nextInt(10000) + fileName;
    }

}
