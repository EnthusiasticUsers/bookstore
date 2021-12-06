package cn.itcast.com.servlet;

import cn.itcast.com.domain.Board;
import cn.itcast.com.service.impl.BoardServiceImpl;
import cn.itcast.com.util.JsonUtil;
import cn.itcast.com.util.StatusUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@MultipartConfig(location = "/")
@WebServlet("/board")
public class BoardServlet extends HttpServlet {

    private BoardServiceImpl boardService = new BoardServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //返回json
        String json = "";
        //状态
        String key = request.getParameter("key");
        String status = request.getParameter("status");
        status = new String(Base64.getDecoder().decode(status));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String content = request.getParameter("content");


        if("insert".equals(status)){
            Board board = new Board();
            board.setTitle(title);
            board.setAuthor(author);
            board.setContent(content);
            boolean flag = boardService.insert(board);
            json = flag ? StatusUtil.success("插入留言成功!") : StatusUtil.failed("插入留言失败!");
        }else if("show".equals(status)){
            List<Board> boards = boardService.selectByKey(null);
            json = JsonUtil.objToStr(boards);
        } else{
            System.out.println("error:" + status);
        }


        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
