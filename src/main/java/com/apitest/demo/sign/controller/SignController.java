package com.apitest.demo.sign.controller;

import com.apitest.demo.response.model.SingleResult;
import com.apitest.demo.response.service.ResponseService;
import com.apitest.demo.user.dto.UserDto;
import com.apitest.demo.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags= {"2.sign"}) // swagger api이름 지정
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SignController {

    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value="회원가입", notes="회원가입을 합니다.")
    @PostMapping("/signup")
    public SingleResult<Long> signup(
            @ApiParam(value = "회원 가입 아이디 : 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 가입 비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "회원 가입 이름", required = true ) @RequestParam String username,
            @ApiParam(value = "회원 가입 닉네임", required = true) @RequestParam String nickName  // DTO에서 처리해되 되는 부분이지만 swagger에서 확인하기 위해 생성해준다.
    ){

        UserDto user = new UserDto();

        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        user.setNickname(nickName);

        Long userId = userService.signup(user);

        return responseService.getSingleResult(userId);
    }
}
