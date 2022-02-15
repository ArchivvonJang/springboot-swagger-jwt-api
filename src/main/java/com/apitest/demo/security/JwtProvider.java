package com.apitest.demo.security;

import com.apitest.demo.security.exception.CAuthenticationEntryPointException;
import com.apitest.demo.user.dao.UserDao;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider { // JWT 토큰을 생성 및 검증하는 모듈 JwtTokenProvider
    @Value("${spring.jwt.sercret") //초기화
    private String secretKey;

    private String ROLES = "roles";

    // access token의 유효시간에 대한 지정 (1시간)
    private final Long accessTokenValidTime = 60 * 60 * 1000L; // 1 hour

    private final CustomUserDetailsService userDetailsService;
    private final UserDao userDao;

    // Provider를 의존섬 주입 후 , 조기화를 수행
    // 객체 초기화, sercretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        //위에서 선언한 secretKey를 Base64로 인코딩
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Access Token 생성
    public String createAccessToken(String userid, List<String> roles) { // key값인 userid를 넘기고 role 넘긴다.
        return  this.createToken(userid, roles, accessTokenValidTime); //아래에 있는 메소드를 유효시간도 같이 넘겨준다.
    }

    // Create Token
    // Token을 분리하는 이유 : 다른 토큰이 추가될 수 있으므로 구분지어서 생성
    public String createToken(String userid, List<String> roles, long tokenVaild) {

        Claims claims = Jwts.claims().setSubject(userid); // claims 생성 및 payload 설정
        claims.put("roles", roles); // 권한설정, key와 value 쌍으로 저장

        Date date = new Date();
        return Jwts.builder()  //jwt 생성
                .setHeaderParam(Header.TYPE,  Header.JWT_TYPE)
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + tokenVaild)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, secretKey) // 해싱 알고리즘 및 키 설정
                .compact()//생성
                ;
    }

    // JWT 토큰으로 인증정보 조회
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        Claims claims = parseClaims(token); //토큰을 던지면 파싱을 한다. 파싱한 것을 클래임스에 담아서

        //권한이 null이면 예외로 보내주기
        if(claims.get(ROLES)==null) {
            throw new CAuthenticationEntryPointException();
        }

        // 사용자 디테일에 값을 담아준다.
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT에서 회원 구분 PK추출
    public String getUSerPk(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

    // HTTP Request의 Header 에서 Token Parsing -> "X-AUTH-TOKEN : jwt"
    // 헤더에 토큰을 담아서 보내는데, 어떤 키값으로 파싱할지 가져오는 역할
    public String resolveAccessToken(HttpServletRequest request) {
        if(request.getHeader("X-AUTH-TOKEN") != null)
            return request.getHeader("X-AUTH-TOKEN");

        return null;

    }

    // JWT의 유효성 및 만료일자 확인
    public boolean validdateToken(String token) {

        try {
            Jws<Claims> claimsJws =
                    Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());

            // 만료날짜가 현재보다 이전이면 false
        }catch(Exception e) {
            log.error(e.toString());
            return false;
        }
    }

    // Jwt Token 복호화해서 가져오기
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        }catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // Userid로 권한 정보 가져오기
    public List<String> getRoles(String userid){
        return userDao.selectUserRoles(userid);
    }
}
