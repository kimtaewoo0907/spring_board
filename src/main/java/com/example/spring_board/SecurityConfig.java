package com.example.spring_board;

import com.example.spring_board.author.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//EnableWebSecurity 어노테이션은 spring security를 custumizing 할 수 있는 기능 활성화
@EnableWebSecurity
public class SecurityConfig {
    // 스프링에서 빈을 만드는 방법 2가지(싱글톤)
    // 방법 1. Component 방식
    // 개발자가 직접 컨트롤이 가능한 내부 클래스에서 사용

    // 방법 2. Configuration + Bean 방식
    // 개발자가 컨트롤이 불가능한 외부 라이브러리 적용시 사용
    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                // 보안공격에 대한 설정은 별도로 하지 않겠다라는 뜻
                .csrf().disable()
                .authorizeHttpRequests()
                // login authenticated에서 제외대한 대상 url 지정
                .antMatchers("/author/login", "/", "authors/new")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/author/login")
                // spring의 doLogin 기능 그대로 활용
                    .loginProcessingUrl("/doLogin")
                    .usernameParameter("email")
                    .passwordParameter("password")
                // 로그인 성공시 이후 로직 LoginSuccessHandler에서 구현
                    .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
                // spring의 doLogout 기능 그대로 활용
                .logoutUrl("/doLogout")
                .and().build();
    }
}
