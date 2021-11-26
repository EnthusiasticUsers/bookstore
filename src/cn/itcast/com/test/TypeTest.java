package cn.itcast.com.test;

import cn.itcast.com.domain.Type;
import cn.itcast.com.service.impl.TypeServiceImpl;
import org.junit.Test;

import java.util.List;

public class TypeTest {

    private TypeServiceImpl typeService = new TypeServiceImpl();

    @Test
    public void test01(){
        List<Type> list = typeService.findAll();
        for(Type type : list)
            System.out.println(type);
    }
}
