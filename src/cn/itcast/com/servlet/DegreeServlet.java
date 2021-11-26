package cn.itcast.com.servlet;

import cn.itcast.com.dao.impl.DegreeDaoImpl;
import cn.itcast.com.domain.Degree;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/degree")
public class DegreeServlet extends HttpServlet {

    private DegreeDaoImpl degreeDao = new DegreeDaoImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //声明返回字符编码
        response.setCharacterEncoding("utf-8");
        //获取学历
        List<Degree> degree_list = degreeDao.findAll();
        //转换json
        if(degree_list == null || degree_list.size() == 0){
            response.getWriter().write("500");
            System.out.println("获取失败");
        }else{
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(degree_list);
            //声明返回类型
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(data);

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
