package cn.itcast.com.test;

import cn.itcast.com.dao.impl.DegreeDaoImpl;
import cn.itcast.com.domain.Degree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.List;

public class DegreeTest {

    private DegreeDaoImpl degreeDao = new DegreeDaoImpl();

    @Test
    public void test01() throws JsonProcessingException {
        List<Degree> list = degreeDao.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(list);
        System.out.println(data);
    }
}
