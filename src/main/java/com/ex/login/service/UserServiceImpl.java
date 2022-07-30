package com.ex.login.service;

import com.ex.login.mapper.UserMapper;
import com.ex.login.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int saveInfo(UserVO userVO) throws Exception {
        return userMapper.saveInfo(userVO);
    }

    @Override
    public UserVO getUserInfo(String userInfo) throws Exception {
        return userMapper.getUserInfo(userInfo);
    }

    @Override
    public UserVO getUserName(String userName) throws Exception {
        return userMapper.getUserName(userName);
    }
}
