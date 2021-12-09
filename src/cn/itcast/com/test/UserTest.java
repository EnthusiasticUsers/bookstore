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
        String password = "12";
        String user_info = null;
        User user = userService.login(username, password);
        String string = JsonUtil.objToStr(user);
        System.out.println(string);

    }

    @Test
    public void test2() throws JsonProcessingException {
        String username = "admin";
        String password = "1234";
        User user = new User();
        String str = JsonUtil.objToStr(user);
        System.out.println(str);
    }
}
