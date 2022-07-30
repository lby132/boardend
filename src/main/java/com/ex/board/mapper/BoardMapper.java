package com.ex.board.mapper;

import com.ex.board.model.BoardDTO;
import com.ex.board.model.PageInfo;
import com.ex.board.model.Search;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardDTO> selectBoard(Search search) throws Exception;

    BoardDTO detailSelectBoard(int idx) throws Exception;

    int insertBoard(BoardDTO boardDTO) throws Exception;

    int updateBoard(BoardDTO boardDTO) throws Exception;

    int deleteBoard(int idx) throws Exception;

    int getBoardListCnt(Search search) throws Exception;

    int updateCnt(BoardDTO boardDTO) throws Exception;
}
