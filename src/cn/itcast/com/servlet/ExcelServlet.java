package cn.itcast.com.servlet;

import cn.itcast.com.domain.Book;
import cn.itcast.com.service.impl.BookServiceImpl;
import cn.itcast.com.util.DownUtil;
import cn.itcast.com.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/excel")
public class ExcelServlet extends HttpServlet {
    private BookServiceImpl bookService = new BookServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置字符编码
        response.setContentType("text/html;charset=utf-8");
        List<Book> books = bookService.selectByKey(null);

        String[] title = {"ID", "书名", "价格", "图片路径", "书籍类型"};
        String filename = "books.xls";
        String sheetName = new String("sheet1".getBytes(), "utf-8");
        String[][] content = new String[books.size()][title.length];
        for(int i = 0; i < content.length; i++){
            Book book = books.get(i);
            content[i][0] = book.getId() + "";
            content[i][1] = book.getName();
            content[i][2] = book.getPrice() + "";
            content[i][3] = book.getImage();
            content[i][4] = book.getType();
        }

        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        String agent = request.getHeader("user-agent");
        String type = getServletContext().getMimeType(filename);
        response.setHeader("content-type", type);
        response.setHeader("content-disposition", "attachment;filename=" + DownUtil.getFilename(agent, filename));
        //导出至网页
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
