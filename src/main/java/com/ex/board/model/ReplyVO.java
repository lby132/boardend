package com.ex.board.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReplyVO {

    private int rid;
    private int bid;
    private String content;
    private String writer;
    private Timestamp regDt;
    private Timestamp editDt;
    private int parent;
    private int groupNo;
    private int depth;
    private String loginMem; // board writer

}
