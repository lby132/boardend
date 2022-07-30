package com.ex.board.mapper;

import com.ex.board.model.ReplyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    List<ReplyVO> getReplyList(int bid) throws Exception;
    int saveReply(ReplyVO replyVO) throws Exception;

    int updateReply(ReplyVO replyVO) throws Exception;

    int deleteReply(ReplyVO replyVO) throws Exception;

    //ReplyVO getReplyRid(int bid) throws Exception;

    int saveReReply(ReplyVO replyVO) throws Exception;

    List<ReplyVO> getReReply(int rid) throws Exception;

    int reReplyDelete(ReplyVO replyVO) throws Exception;

    int reReplyAllDelete(int bid) throws Exception;

}
