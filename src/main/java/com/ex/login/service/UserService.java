package com.ex.login.service;

import com.ex.login.model.UserVO;

public interface UserService {

    int saveInfo(UserVO userVO) throws Exception;
    UserVO getUserInfo(String userInfo) throws Exception;

    UserVO getUserName(String userName) throws Exception;
}


