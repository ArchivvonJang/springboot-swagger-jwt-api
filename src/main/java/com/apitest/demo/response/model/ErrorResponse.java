package com.apitest.demo.response.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final boolean success;
    private final int httpStatus;
    private final String httpStatusCode;
    private final int value;
    private final String code;
    private final String msg;

    //실패 응답 처리할 때 가져다 쓸 코드, 메시지 등등..
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus()) //httpStatus에 해당하는 각각의 권한에 대한 코드가져오기
                .body(ErrorResponse.builder()
                        .success(false)
                        .httpStatus(errorCode.getHttpStatus().value())
                        .httpStatusCode(errorCode.getHttpStatus().name())
                        .value(errorCode.getValue())
                        .code(errorCode.name())
                        .msg(errorCode.getDetail())
                        .build()
                );
    }
}
