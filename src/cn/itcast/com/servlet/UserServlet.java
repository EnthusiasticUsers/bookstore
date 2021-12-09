package cn.itcast.com.servlet;

import cn.itcast.com.domain.Hobby;
import cn.itcast.com.domain.User;
import cn.itcast.com.service.impl.UserServiceImpl;
import cn.itcast.com.util.JsonUtil;
import cn.itcast.com.util.StatusUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserServiceImpl userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String status = request.getParameter("status");
        status = new String(Base64.getDecoder().decode(status));
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //返回json
        String json = "";
        if(status.equals("login")){
            User user = userService.login(username, password);
            if(user != null){
                request.getSession().setAttribute("user", user);
                json = StatusUtil.success("登录成功");
            }else{
                json = StatusUtil.failed("登陆失败:用户名或密码错误!");
            }
        }else if(status.equals("register")){
            String sex = request.getParameter("sex");
            String degree = request.getParameter("degree");
            String hobbies = request.getParameter("hobbies");
            List<Hobby> hobbies_List = JsonUtil.strToObjList(hobbies, Hobby.class);
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setSex(Integer.parseInt(sex));
            user.setDegree(Integer.parseInt(degree));
            user.setHobbies(hobbies_List);
            boolean flag = false;
            try {
                flag = userService.register(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(flag){
                json = StatusUtil.success("注册成功");
            }else{
                json = StatusUtil.failed("注册失败");
            }
        }else if(status.equals("session")){
            User user = (User) request.getSession().getAttribute("user");
            response.setContentType("application/json;charset=utf-8");
            json = JsonUtil.objToStr(user);
        }

        //设置返回响应头
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
