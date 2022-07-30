package com.ex.board.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Search extends PageInfo {

    private String searchType;
    private String keyword;
    private List<Integer> bNum;



}

