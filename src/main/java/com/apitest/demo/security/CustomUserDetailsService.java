package com.apitest.demo.security;

import com.apitest.demo.login.dto.LoginDto;
import com.apitest.demo.user.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        LoginDto loginUser = userDao.getUserByUserid(userid); // userid 받은 것을 담아준다.
        loginUser.setRoles(userDao.selectUserRoles(userid)); // 권한을 담아주는 리스트를 만들고  selectUserRoles라고 사용자가 갖고 있는 모든 권한을 담아준다.

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : loginUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        loginUser.setAuthorities(authorities);

        return loginUser;
    }
}
