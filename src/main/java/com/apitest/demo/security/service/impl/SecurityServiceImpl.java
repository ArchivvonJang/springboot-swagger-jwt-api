package com.apitest.demo.security.service.impl;

import com.apitest.demo.login.dto.LoginDto;
import com.apitest.demo.security.service.SecurityService;
import com.apitest.demo.user.dto.UserDto;
import com.apitest.demo.user.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserDao userDao;

    @Override
    public LoginDto getUser(UserDto user) {

        LoginDto loginUser = userDao.getUser(user);

        loginUser.setRoles(userDao.selectUserRoles(String.valueOf(loginUser.getUserid())));

        return loginUser;
    }
}
