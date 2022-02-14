package com.apitest.demo.dbtest.controller;

import com.apitest.demo.dbtest.dao.TestDao;
import com.apitest.demo.dbtest.dto.TestDto;
import com.apitest.demo.response.model.ListResult;
import com.apitest.demo.response.model.SingleResult;
import com.apitest.demo.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor //생성자주입방식을 위해서 추가
@RestController //rest 형식으로 할것이기 때문에 RestController Annotation 호출, 이 컨트롤러 안에 Controller와 ResponseBody가 포함되어있어서 두가지 역할 다 해준다
@RequestMapping("/api")
public class DbTestController {

    //responseService 주입
    //생성자 주입 방식
    private final ResponseService responseService; //롬복 어노테이션을 사용하지 않고 사용하려면 final을 지워준다

    @Autowired
    private TestDao testDAO;

    //공통 응답처리
    @GetMapping("/hello")
    public SingleResult<TestDto> HelloWorld(){
        String param = "TEST1";
        return responseService.getSingleResult(testDAO.getTestData(param));
    }

    //공통 응답처리
    @GetMapping("/hello/list")
    public ListResult<TestDto> HelloWorldList(){
        return responseService.getListResult(testDAO.getTestDataList());
    }

}
