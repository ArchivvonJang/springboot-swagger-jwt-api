package com.apitest.demo.exception;

import com.apitest.demo.response.model.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException { //런타임예외 발생시 관리
    private final ErrorCode errorcode;
}
