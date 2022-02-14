package com.apitest.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntrypoint customAuthenticationEntrypoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtProvider jwtProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean(); //바로 위에 생성자 메소드를 상속받아서 이어가기 위해 super
    }

    //websecurity
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");  //v2 : swagger에서 제공하는 문서 버전
    }

    // httpsecurity
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .httpBasic().disable()
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/*/login", "/*/signup" ,"/api/hello").permitAll()
                .antMatchers("/exception/**").permitAll()
                .antMatchers("/*/user/sel/admin").hasRole("ADMIN")
                .anyRequest().hasRole("USER") // 사용자에게만 노출

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntrypoint)
                .accessDeniedHandler(customAccessDeniedHandler)

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다.
        // 기본적으로 Filter로 수행되는 것은 Form기반의 아이디와 비밀번호로 진행되는 UsernamePasswordAuthenticatioFilter가 수행된다.
        // 하지만 JWT 인증을 위해서는 새로운 필터를 만들어 UsernamePasswordAuthenticationFilter보다 먼저 실행되게 설정해야한다.

        /*
         * .authorizeRequests() .antMatchers("/api/hello/**").permitAll() // 허용한 주소만 접근
         * 가능한 권한 설정 .anyRequest().authenticated()
         */
        ;
    }
}
