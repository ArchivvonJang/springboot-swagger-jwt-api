package com.apitest.demo.user.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long userid;
    private String email;
    private String password;
    private String username;
    private String nickname;

    private List<String> roles;
    private LocalDateTime createdDate;
}
