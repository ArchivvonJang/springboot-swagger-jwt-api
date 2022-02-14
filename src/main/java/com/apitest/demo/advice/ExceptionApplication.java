package com.apitest.demo.advice;

import com.apitest.demo.response.model.CommonResult;
import com.apitest.demo.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

//모든 exception을 관리하기 위에 security와 별개로 패키지를 생성
@RequiredArgsConstructor
@RestController
public class ExceptionApplication {
    private final ResponseService responseService; //생성자 의존성 주입

    // 예외발생 시 exception 관할
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult();
    }

    //전달한 Jwt 가 정상적이지 않은 경우 발생시키는 예외
    //권한 관련 예외
/*    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntrypointException(
            HttpServletRequest request, CAuthenticationEntryPointException e){

        return responseService.getFailResult(1003, "해당 리소스에 접근하기 위한 권한이 없습니다.");
    }*/

    // 권한이 없는 리소스를 요청한 경우 발생시키는 예외
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException(
            HttpServletRequest request, AccessDeniedException e) {

        return responseService.getFailResult(1004, "Permission not accessible to this resource"); //코드번호들도 enum으로 관리가능 지금은 하드코딩
    }


}
