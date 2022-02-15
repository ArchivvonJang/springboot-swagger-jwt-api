package com.apitest.demo.user.service;

import com.apitest.demo.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> selectUserList();

    Long signup(UserDto user);
}
