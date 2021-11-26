package cn.itcast.com.servlet;

import cn.itcast.com.domain.Type;
import cn.itcast.com.service.impl.TypeServiceImpl;
import cn.itcast.com.service.impl.UserServiceImpl;
import cn.itcast.com.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/book/type/show")
public class TypeServlet extends HttpServlet {

    private TypeServiceImpl typeService = new TypeServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Type> list = typeService.findAll();
        String data = JsonUtil.objToStr(list);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(data);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
