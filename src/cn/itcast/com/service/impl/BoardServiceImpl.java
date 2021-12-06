package cn.itcast.com.service.impl;


import cn.itcast.com.dao.impl.BoardDaoImpl;
import cn.itcast.com.domain.Board;
import cn.itcast.com.service.BoardService;

import java.util.List;

public class BoardServiceImpl implements BoardService {
    private BoardDaoImpl boardDao = new BoardDaoImpl();

    @Override
    public boolean insert(Board board) {
        return boardDao.insert(board);
    }

    @Override
    public List<Board> selectByKey(String key) {
        return boardDao.selectByKey(key);
    }

    @Override
    public boolean delete(Integer id) {
        return boardDao.delete(id);
    }

    @Override
    public boolean batchDelete(List<Board> boards) {
        return boardDao.batchDelete(boards);
    }
}
