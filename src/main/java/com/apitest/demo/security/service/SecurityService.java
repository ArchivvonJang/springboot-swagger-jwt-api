package com.apitest.demo.security.service;

import com.apitest.demo.login.dto.LoginDto;
import com.apitest.demo.user.dto.UserDto;

public interface SecurityService {
    public LoginDto getUser(UserDto user);
}
