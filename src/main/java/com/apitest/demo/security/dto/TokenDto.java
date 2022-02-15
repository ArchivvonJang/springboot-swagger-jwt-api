package com.apitest.demo.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenDto {
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiredDate;
}
