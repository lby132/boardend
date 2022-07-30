package com.ex.board.service;

import com.ex.board.model.BoardDTO;
import com.ex.board.model.PageInfo;
import com.ex.board.model.ReplyVO;
import com.ex.board.model.Search;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BoardService {

    int insertBoard(BoardDTO boardDTO) throws Exception;

    List<BoardDTO> selectBoard(Search search) throws Exception;

    BoardDTO detailSelectBoard(int idx) throws Exception;

    int updateBoard(BoardDTO boardDTO) throws Exception;

    int deleteBoard(int idx) throws Exception;

    int getBoardListCnt(Search search) throws Exception;
}
