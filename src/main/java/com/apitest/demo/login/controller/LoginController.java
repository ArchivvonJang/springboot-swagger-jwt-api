package com.apitest.demo.login.controller;

import com.apitest.demo.login.dto.LoginDto;
import com.apitest.demo.response.model.SingleResult;
import com.apitest.demo.response.service.ResponseService;
import com.apitest.demo.security.JwtProvider;
import com.apitest.demo.security.service.SecurityService;
import com.apitest.demo.user.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
@Slf4j
@Api(tags = {"3. Login"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final JwtProvider jwtProvider;
    private final SecurityService securitySercice;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @GetMapping("/login")
    public SingleResult<LoginDto> login(
            @ApiParam(value = "로그인 아이디 : 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "로그인 비밀번호", required = true) @RequestParam String password,
            HttpServletResponse response) {
        UserDto user = new UserDto();

        user.setEmail(email);
        user.setPassword(password);

        LoginDto loginUser = securitySercice.getUser(user);

        String accessToken = jwtProvider.createAccessToken(String.valueOf(loginUser.getUserid()), loginUser.getRoles());

        loginUser.setAccessToken(accessToken);

        log.info("loginUser : {}", loginUser);

        return responseService.getSingleResult(loginUser);
    }
}
