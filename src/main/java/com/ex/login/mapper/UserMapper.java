package com.ex.login.mapper;

import com.ex.login.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int saveInfo(UserVO userVO) throws Exception;
    UserVO getUserInfo(String userId) throws Exception;

    UserVO getUserName(String userName) throws Exception;
}
