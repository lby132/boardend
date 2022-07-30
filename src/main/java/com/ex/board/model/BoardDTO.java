package com.ex.board.model;

import com.ex.login.model.UserVO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;


@Getter
@Setter
public class BoardDTO extends UserVO {

    private Integer idx;
    private Integer rn; // ROWNUM 컬럼
    private String title;
    private String content;
    private int viewCnt;
    private String writer;
    private String deleteBd;
    private Date regDate;
    private String boardPw;
    private String bloginYn;

}
