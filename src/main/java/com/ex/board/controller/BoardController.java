package com.ex.board.controller;

import com.ex.board.mapper.BoardMapper;
import com.ex.board.mapper.ReplyMapper;
import com.ex.board.model.BoardDTO;
import com.ex.board.model.ReplyVO;
import com.ex.board.model.Search;
import com.ex.board.service.BoardService;
import com.ex.login.mapper.UserMapper;
import com.ex.login.model.UserVO;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BoardMapper boardMapper;

    @GetMapping("/getBoardList")
    public String getBoard(BoardDTO boardDTO, Model model, UserVO userVO
            , @RequestParam(required = false, defaultValue = "1") int page
            , @RequestParam(required = false, defaultValue = "1") int range
            , @RequestParam(required = false, defaultValue = "title") String searchType
            , @RequestParam(required = false) String keyword
            , HttpServletRequest request) throws Exception {

        Search search = new Search();
        search.setSearchType(searchType);
        search.setKeyword(keyword);

        log.info("page={}", page);

        int totalCnt = boardService.getBoardListCnt(search);
        log.info("cnt = {}", totalCnt);
        if (totalCnt == 0) {
            model.addAttribute("message", "등록된 글이 없습니다.");
        }

        search.pageInfo(page, range, totalCnt);

        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("listCnt", search.getBNum());
        model.addAttribute("search", searchType);
        model.addAttribute("pageInfo", search);
        model.addAttribute("boardList", boardService.selectBoard(search));
        return "board/boardList";
    }

/*    @GetMapping("/getSearchBoard")
    public String getSearchBoard() throws Exception {

    }*/

    @GetMapping("/getBoardDetail/{idx}/{page}")
    public String getBoardDetail(@PathVariable int idx, @PathVariable int page, Model model, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession(false);
        BoardDTO boardDTO = boardService.detailSelectBoard(idx);

        String hide = request.getParameter("hide");

        log.info("gide = {}", hide);


        if (session == null && boardDTO.getBoardPw() != null && hide == null) {
            return "redirect:/board/getBoardList";
        }

        log.info("page = {}", page);
        log.info("boardDetails");

        boardMapper.updateCnt(boardDTO);
        model.addAttribute("page", page);
        model.addAttribute("boardDetail", boardDTO);
        model.addAttribute("rid", replyMapper.getReplyList(idx));
        model.addAttribute("replyVO", new ReplyVO());
        //model.addAttribute("userInfo", userMapper.getUserInfo());
        return "board/boardDetail";
    }

    @GetMapping("/getBoardForm")
    public String writeBoard(@RequestParam(required = false) Integer idx, @RequestParam(required = false) String gubun, Model model) throws Exception {

        if (idx != null) {
            model.addAttribute("boardDTO", boardService.detailSelectBoard(idx));
            return "board/updateForm";
        } else if (gubun != null) {
            model.addAttribute("userVO", userMapper.getUserName(gubun));
        }

        return "board/boardForm";
    }

    @PostMapping("/updateBoard")
    public String updateBoard(BoardDTO boardDTO, Model model) throws Exception {
        model.addAttribute("boardDTO", boardService.updateBoard(boardDTO));
        return "redirect:/board/getBoardList";
    }

    @PostMapping("/insertBoard")
    public String insertBoard(BoardDTO boardDTO, Model model) throws Exception {
        model.addAttribute("boardDTO", boardService.insertBoard(boardDTO));
        return "redirect:/board/getBoardList";
    }

    @ResponseBody
    @GetMapping("/deleteBoard/{idx}")
    public Map<String, Object> deleteBoard(@PathVariable int idx, Model model) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<ReplyVO> replyList = replyMapper.getReplyList(idx);
        if (replyList.size() > 0) {
            log.info("댓글O = {}", idx);
            result.put("result", true);
        } else {
            log.info("댓글X = {}", idx);
            boardService.deleteBoard(idx);
            result.put("result", false);
        }

        return result;
    }

    @ResponseBody
    @PostMapping("/pwCheck")
    public JsonObject getPwBoard(@RequestBody BoardDTO boardDTO) throws Exception {
        log.info("jsonPw = {}", boardDTO.getBoardPw());
        JsonObject jsonObj = new JsonObject();

        BoardDTO boardPw = boardService.detailSelectBoard(boardDTO.getIdx());
        log.info("boardPw = {}", boardPw.getBoardPw());

        if (boardPw.getBoardPw() != null && boardPw.getBoardPw().equals(boardDTO.getBoardPw())) {
            log.info("성공");
            jsonObj.addProperty("pwCheckOk", true);
        } else {
            log.info("실패");
            jsonObj.addProperty("pwCheckOk", false);
        }

        return jsonObj;
    }

    @GetMapping("/boardDelete/{idx}")
    public String boardDelete(@PathVariable int idx) throws Exception {
        boardService.deleteBoard(idx);
        return "redirect:/board/getBoardList";
    }
}
