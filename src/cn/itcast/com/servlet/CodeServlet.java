package cn.itcast.com.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/code")
public class CodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建图像
        int width = 80;
        int height = 40;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //美化图片
        Graphics g = image.getGraphics();
        g.setColor(Color.pink);
        g.fillRect(0,0,width,height);
        g.setColor(Color.blue);
        g.drawRect(0,0,width-1,height-1);

        //画字符
        String str = "ABCDEFGHIJKLMNOPQRSTUVMXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        //获取验证码
        String code = "";
        for(int i = 1; i <= 4; i++){
            int index = random.nextInt(str.length());
            code = code + str.charAt(index);
            g.drawString( str.charAt(index) + "",width/5*i,height/2);
        }
        //将验证码存储session
        req.getSession().setAttribute("session_code",code);

        //画线
        g.setColor(Color.green);
        for(int i = 1; i <= 10; i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(width);
            int x2 = random.nextInt(height);
            int y2 = random.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }

        //输出图片
        ImageIO.write(image,"jpg",resp.getOutputStream());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
