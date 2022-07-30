package com.ex.board;

import com.ex.board.mapper.BoardMapper;
import com.ex.board.mapper.ReplyMapper;
import com.ex.board.model.BoardDTO;
import com.ex.board.model.PageInfo;
import com.ex.board.model.ReplyVO;
import com.ex.board.model.Search;
import com.ex.login.mapper.UserMapper;
import com.ex.login.model.UserVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MybatisTest {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectBoardTest() throws Exception {
        Search search = new Search();
        search.setStartList(1);
        search.setListSize(100);
        List<BoardDTO> result = boardMapper.selectBoard(search);
        System.out.println("== 게시글 리스트 index ==");
        for (BoardDTO boardList : result) {
            if (result.size() > 1) {
                System.out.println(boardList.getIdx());
            } else {
                System.out.println("등록된 게시글이 없습니다.");
            }
        }
    }

    @Test
    void getListCntTest() throws Exception {
        Search search = new Search();
        int boardListCnt = boardMapper.getBoardListCnt(search);
        if (boardListCnt > 0) {
            System.out.println("성공");
            System.out.println("리스트 수 = " + boardListCnt);
        } else {
            System.out.println("실패");
        }
    }

    @Test
    void detailBoardTest() throws Exception{
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setIdx(2);
        boardDTO.setTitle("2번 게시글 제목");
        boardDTO.setContent("2번 게시글 내용");
        boardDTO.setWriter("2번 작성자");

        int result = boardMapper.insertBoard(boardDTO);

        BoardDTO boardOne = boardMapper.detailSelectBoard(boardDTO.getIdx());

        if (boardOne != null) {
            System.out.println("게시글상세");
            System.out.println("boardOne.getContent() = " + boardOne.getContent());
        }
    }

    @Test
    void insertBoardTest() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        for (int i = 147; i < 300; i++) {
            boardDTO.setTitle(i + "번 게시글 제목");
            boardDTO.setContent(i + "번 게시글 내용");
            boardDTO.setWriter(i + "번 작성자");
            int result = boardMapper.insertBoard(boardDTO);
        }
    }

    @Test
    void updateBoard() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setIdx(2);
        boardDTO.setTitle("2번 게시글 제목 수정!");
        boardDTO.setContent("2번 게시글 내용 수정!");
        boardDTO.setViewCnt(3);
        boardDTO.setWriter("2번 작성자 수정!");

        int result = boardMapper.updateBoard(boardDTO);

        if (result > 0) {
            System.out.println("수정 성공");
        } else {
            System.out.println("수정 실패");
        }
    }

    @Test
    void deleteBoard() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setIdx(3);
        boardDTO.setTitle("1번 게시글 제목");
        boardDTO.setContent("1번 게시글 내용");
        boardDTO.setViewCnt(1);
        boardDTO.setWriter("1번 작성자");

        int insertBoards = boardMapper.insertBoard(boardDTO);

        int result = boardMapper.deleteBoard(boardDTO.getIdx());

        if (result > 0) {
            System.out.println("글 삭제 성공");
        } else {
            System.out.println("글 삭제 실패");
        }

    }

    @Test
    void 목록() throws Exception {
        int i = 4;
        List<ReplyVO> result = replyMapper.getReplyList(i);
        for (ReplyVO replyVO : result) {
            System.out.println(replyVO.getBid() + " = reg");
        }
        assertThat(result.size()).isNotNull();
    }

    @Test
    void 저장() throws Exception {
        ReplyVO replyVO = new ReplyVO();
        replyVO.setBid(4);
        replyVO.setContent("댓글입니다.");


        int result = replyMapper.saveReply(replyVO);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void 수정() throws Exception {
        ReplyVO replyVO = new ReplyVO();
        replyVO.setRid(3);
        replyVO.setContent("수정 댓글");

        int result = replyMapper.updateReply(replyVO);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void 삭제() throws Exception {
        ReplyVO replyVO = new ReplyVO();
        replyVO.setRid(3);

        int result = replyMapper.deleteReply(replyVO);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void 유저정보() throws Exception {
        UserVO userVO = new UserVO();
        userVO.setUserId("abc");
        UserVO result = userMapper.getUserInfo(userVO.getUserId());

        System.out.println("result = " + result.getUserId());
    }

 /*   @Test
    void getReplyRid() throws Exception {
        ReplyVO replyRid = replyMapper.getReplyRid(4);
        System.out.println("replyRid = " + replyRid);
    }*/
}
