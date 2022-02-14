package com.apitest.demo.dbtest.dto;

import com.apitest.demo.dbtest.dao.TestDao;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
public class TestDto {

    String title;
    String name;

    // lombok annotation 설정하기 전에 getter setter 만들어서 확인
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // lombok내에 Getter, Setter 어노테이션을 붙여주므로서 getter setter를 일일히 설정해주지 않아도 된다.

}
