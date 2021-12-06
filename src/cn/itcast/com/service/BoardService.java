package cn.itcast.com.service;

import cn.itcast.com.domain.Board;

import java.util.List;

public interface BoardService {


    //插入留言
    boolean insert(Board board);

    //展示留言
    List<Board> selectByKey(String key);

    //删除留言
    boolean delete(Integer id);

    //批量删除留言
    boolean batchDelete(List<Board> boards);
}
