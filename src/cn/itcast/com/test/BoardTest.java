package cn.itcast.com.test;

import cn.itcast.com.domain.Board;
import cn.itcast.com.service.impl.BoardServiceImpl;
import org.junit.Test;

import java.util.List;

public class BoardTest {

    private BoardServiceImpl boardService = new BoardServiceImpl();

    @Test
    public void test01(){
        List<Board> boards = boardService.selectByKey(null);

        for(Board board : boards){
            System.out.println(board);
        }
    }
}
