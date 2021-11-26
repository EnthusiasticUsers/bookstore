package cn.itcast.com.test;

import cn.itcast.com.domain.User;
import cn.itcast.com.service.impl.UserServiceImpl;
import cn.itcast.com.util.JsonUtil;
import cn.itcast.com.util.StatusUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class UserTest {
    private UserServiceImpl userService = new UserServiceImpl();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test1() throws JsonProcessingException {
        String username = "admin";
        String password = "123";
        String user_info = null;
        boolean res = userService.login(username, password);
        if (res) {
            user_info = StatusUtil.success("登录成功");
        } else {
            user_info = StatusUtil.failed("登录失败");
        }

        String string = objectMapper.writeValueAsString(user_info);
        System.out.println(string);

    }

    @Test
    public void test2() throws JsonProcessingException {
        String username = "admin";
        String password = "1234";
        User user = new User();
        //user.setUsername(username);
        //user.setPassword(password);
        String str = JsonUtil.objToStr(user);
        System.out.println(str);
    }
}
