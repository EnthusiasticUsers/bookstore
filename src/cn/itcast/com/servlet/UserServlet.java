package cn.itcast.com.servlet;

import cn.itcast.com.domain.Hobby;
import cn.itcast.com.domain.User;
import cn.itcast.com.service.impl.UserServiceImpl;
import cn.itcast.com.util.JsonUtil;
import cn.itcast.com.util.StatusUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserServiceImpl userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String status = request.getParameter("status");
        status = new String(Base64.getDecoder().decode(status));
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String power = request.getParameter("power");
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        String session_code = (String) session.getAttribute("session_code");

        //返回json
        String json = "";
        if("login".equals(status)){
            Object obj = session.getAttribute("user");
            if(obj != null || session_code != null && session_code.equalsIgnoreCase(code)){
                session.setAttribute("session_code", null);//销毁验证码避免一个多次使用
                User user = userService.login(username, password);
                if(user != null){
                    session.setAttribute("user", user);
                    json = StatusUtil.success("登录成功");
                }else{
                    json = StatusUtil.failed("用户名或密码错误!");
                }
            }else{
                json = StatusUtil.failed("验证码错误!");
            }
        }else if("register".equals(status)){
            int pic = new Random().nextInt(15) + 1;
            String sex = request.getParameter("sex");
            String degree = request.getParameter("degree");
            String hobbies = request.getParameter("hobbies");
            List<Hobby> hobbies_List = JsonUtil.strToObjList(hobbies, Hobby.class);
            String portrait = "image/tx/" + pic + ".jpg";
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setSex(Integer.parseInt(sex));
            user.setDegree(Integer.parseInt(degree));
            user.setHobbies(hobbies_List);
            user.setPortrait(portrait);
            boolean flag = false;
            try {
                flag = userService.register(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            json = flag ? StatusUtil.success("注册成功") : StatusUtil.failed("注册失败");;
        } else if("session".equals(status)){
            User user = (User) request.getSession().getAttribute("user");
            json = JsonUtil.objToStr(user);
        }else if("id".equals(status)){
            User user = userService.selectById(Integer.parseInt(id));
            json = JsonUtil.objToStr(user);
        } else if("show".equals(status)){
            List<User> users = userService.show();
            json = JsonUtil.objToStr(users);
        }else if("update".equals(status)){
            User user = new User();
            user.setId(Integer.parseInt(id));
            user.setUsername(username);
            user.setPassword(password);
            if(power != null) user.setPower(Integer.parseInt(power));
            boolean flag = userService.update(user);
            json = flag ? StatusUtil.success("修改用户成功!") : StatusUtil.failed("修改用户失败!");
        }else if("delete".equals(status)){
            boolean flag = userService.delete(Integer.parseInt(id));
            json = flag ? StatusUtil.success("删除用户成功!") : StatusUtil.failed("删除用户失败!");
        }else if("cancel".equals(status)){
            session.setAttribute("user", null);
            json = StatusUtil.success("获取成功!");
        }

        //设置返回响应头
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }

}
