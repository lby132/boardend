package com.ex.board.service;

import com.ex.board.mapper.BoardMapper;
import com.ex.board.model.BoardDTO;
import com.ex.board.model.PageInfo;
import com.ex.board.model.Search;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServiceImpl implements BoardService {

    @Autowired
    private BoardMapper mapper;

    @Override
    public int insertBoard(BoardDTO boardDTO) throws Exception {
        return mapper.insertBoard(boardDTO);
    }

    @Override
    public List<BoardDTO> selectBoard(Search search) throws Exception {
        return mapper.selectBoard(search);
    }

    @Override
    public BoardDTO detailSelectBoard(int idx) throws Exception {
        return mapper.detailSelectBoard(idx);
    }

    @Override
    public int updateBoard(BoardDTO boardDTO) throws Exception {
        return mapper.updateBoard(boardDTO);
    }

    @Override
    public int deleteBoard(int idx) throws Exception {
        return mapper.deleteBoard(idx);
    }

    @Override
    public int getBoardListCnt(Search search) throws Exception {
        return mapper.getBoardListCnt(search);
    }
}
