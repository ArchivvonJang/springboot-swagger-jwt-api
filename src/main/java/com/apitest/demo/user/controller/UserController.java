package com.apitest.demo.user.controller;

import com.apitest.demo.response.model.ListResult;
import com.apitest.demo.response.service.ResponseService;
import com.apitest.demo.user.dto.UserDto;
import com.apitest.demo.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService uservice;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "모든 회원 조회", notes = "모든 회원 목록을 조회합니다.")
    @GetMapping("/sel")
    public ListResult<UserDto> selectUserList() {
        return responseService.getListResult(uservice.selectUserList());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "모든 회원 조회-admin", notes = "모든 회원 목록을 조회합니다.-admin")
    @GetMapping("/sel/admin")
    public ListResult<UserDto> selectAdminUserList() {
        return responseService.getListResult(uservice.selectUserList());
    }

}
