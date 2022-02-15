package com.apitest.demo.user.service.impl;

import com.apitest.demo.user.dao.UserDao;
import com.apitest.demo.user.dto.UserDto;
import com.apitest.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public List<UserDto> selectUserList() {

        return userDao.selectUserList();
    }

    @Override
    @Transactional //제대로 회원가입이 안될 경우, rollback 되도록
    public Long signup(UserDto user) {
        user.setRoles(Collections.singletonList("ROLE_USER")); //싱글톤?

        userDao.signup(user);
        userDao.insertUserRoles(user);

        return user.getUserid();
    }
}
