package com.ex.board.controller;

import com.ex.board.mapper.ReplyMapper;
import com.ex.board.model.PageInfo;
import com.ex.board.model.ReplyVO;
import com.ex.board.service.ReplyService;
import com.ex.login.service.UserService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/replyBoard")
public class ReplyBoardController {

    private static int sequence = 0;

    private static int depth = 0;

    @Autowired
    private ReplyService replyService;

    @GetMapping("/getReplyList")
    public List<ReplyVO> getReplyList(int bid) throws Exception {
        return replyService.getReplyList(bid);
    }

    @PostMapping("/saveReply")
    public int saveReplyVO(@RequestBody ReplyVO replyVO) throws Exception {
        replyVO.setGroupNo(++sequence);
        return replyService.saveReReply(replyVO);
    }

    @PostMapping("/replyUpdate")
    public Map<String, String> replyUpdate(@RequestBody ReplyVO replyVO, HttpServletResponse response) {
        Map<String, String> result = new HashMap<>();

        try {
            int update = replyService.updateReply(replyVO);
            if (update > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                result.put("Status", "OK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping("/replyDelete")
    public Map<String, Object> replyDelete(@RequestBody ReplyVO replyVO) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<ReplyVO> replyList = replyService.getReReply(replyVO.getRid());
        if (replyList.size() > 0) {
            log.info("대댓글O = {}", replyVO.getRid());
            result.put("result", true);
        } else {
            log.info("대댓글X = {}", replyVO.getRid());
            result.put("result", false);
        }
        return result;
    }

    @PostMapping("/saveReReply")
    public JsonObject saveReReply(@RequestBody ReplyVO replyVO) throws Exception {
        JsonObject jsonObject = new JsonObject();

        replyVO.setDepth(++depth);

        if (depth == 2) {
            log.info("depth1 = {}", depth);
            depth = 0;
            log.info("depth2 = {}", depth);
        }
        log.info("else = {}", depth);
        replyService.saveReReply(replyVO);

        jsonObject.addProperty("depth", depth);

        return jsonObject;
    }

    @GetMapping("/getReReply")
    public List<ReplyVO> getReReply(@RequestParam int rid) throws Exception {
        return replyService.getReReply(rid);
    }

    @PostMapping("/reReplyDelete")
    public Map<String, Object> reReplyDelete(@RequestBody ReplyVO replyVO) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<ReplyVO> replyList = replyService.getReReply(replyVO.getRid());
        if (replyList.size() > 0) {
            log.info("자식대댓글O = {}", replyVO.getRid());
            result.put("result", true);
        } else {
            log.info("자식대댓글X = {}", replyVO.getRid());
            result.put("result", false);
        }

        return result;
    }

    @PostMapping("/reDelete")
    public int reDelete(@RequestBody ReplyVO replyVO) throws Exception {
        return replyService.deleteReply(replyVO);
    }

    @PostMapping("/reReDelete")
    public int reReDelete(@RequestBody ReplyVO replyVO) throws Exception {
        log.info("reReplyDelete");
        return replyService.reReplyDelete(replyVO);
    }

}
