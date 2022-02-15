package com.apitest.demo.user.dao;

import com.apitest.demo.login.dto.LoginDto;
import com.apitest.demo.user.dto.UserDto;

import java.util.List;

public interface UserDao {
    Long signup(UserDto user);

    void insertUserRoles(UserDto user);

    LoginDto getUser(UserDto user);

    List<String> selectUserRoles(String userid);

    LoginDto getUserByUserid(String userid);

    List<UserDto> selectUserList();
}
