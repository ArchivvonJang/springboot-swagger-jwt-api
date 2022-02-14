package com.apitest.demo.dbtest.dao;

import com.apitest.demo.dbtest.dto.TestDto;

import java.util.List;

public interface TestDao {

    TestDto getTestData(String param);

    List<TestDto> getTestDataList();

}
