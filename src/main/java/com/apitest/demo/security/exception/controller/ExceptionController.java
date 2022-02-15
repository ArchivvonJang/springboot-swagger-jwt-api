package com.apitest.demo.security.exception.controller;

import com.apitest.demo.response.model.CommonResult;
import com.apitest.demo.security.exception.CAuthenticationEntryPointException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.AccessDeniedException;



@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {
    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping(value = "/access-denied")
    public CommonResult accessDeniedException() {
        throw new AccessDeniedException("");
    }

}
