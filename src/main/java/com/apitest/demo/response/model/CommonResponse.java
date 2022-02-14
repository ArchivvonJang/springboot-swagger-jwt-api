package com.apitest.demo.response.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor        //(하단) 모든 인자값 만드는 생성자를 만들어주는 어노테이션
public enum CommonResponse {

    SUCCESS(0, "성공하였습니다."),
    FAIL(-1, "실패하였습니다.");

    private int code;
    private String msg;

    /*
     * private CommonResponse(int code, String msg) { this.code = code; this msg =
     * msg; }
     */

}
