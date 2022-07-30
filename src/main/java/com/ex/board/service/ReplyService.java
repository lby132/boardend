package com.ex.board.service;

import com.ex.board.model.ReplyVO;

import java.util.List;

public interface ReplyService {

    List<ReplyVO> getReplyList(int bid) throws Exception;

    int saveReply(ReplyVO replyVO) throws Exception;

    int updateReply(ReplyVO replyVO) throws Exception;

    int deleteReply(ReplyVO replyVO) throws Exception;

    int saveReReply(ReplyVO replyVO) throws Exception;

    List<ReplyVO> getReReply(int rid) throws Exception;

    int reReplyDelete(ReplyVO replyVO) throws Exception;

    int reReplyAllDelete(int bid) throws Exception;
}
