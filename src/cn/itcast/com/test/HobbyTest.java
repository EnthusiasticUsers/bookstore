package cn.itcast.com.test;

import cn.itcast.com.domain.Hobby;
import cn.itcast.com.service.impl.HobbyServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.List;

public class HobbyTest {

    private HobbyServiceImpl hobbyService = new HobbyServiceImpl();

    @Test
    public void test01() throws JsonProcessingException {
        List<Hobby> list = hobbyService.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(list);
        System.out.println(data);
    }
}
