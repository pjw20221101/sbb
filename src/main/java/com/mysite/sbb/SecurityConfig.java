package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration			//Security 의 설정을 적용하는 어노테이션 
@EnableWebSecurity		//http의 URL 접근을 제어하는 어노테이션 
@EnableMethodSecurity(prePostEnabled = true)	//2월 16일 수정 , 
	//SecurityConfig에 적용한@EnableMethodSecurity 애너테이션의 prePostEnabled = true 설정은 
	//QuestionController와 AnswerController에서 로그인 여부를 판별하기 위해 사용했던 
	//@PreAuthorize 애너테이션을 사용하기 위해 반드시 필요하다.
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        http.authorizeHttpRequests().requestMatchers(
        		
        		
        	 //Spring Security 에서 모든 URL에서 인증 없이 접근 할 수 있도록 허용함
             new AntPathRequestMatcher("/**")).permitAll()
        
        
        	// H2 DataBase는 http로 통신하는 DataBase이므로 csrf 적용되지 않도록 설정 
        	.and()
        		.csrf().ignoringRequestMatchers(
        				new AntPathRequestMatcher("/h2-console/**"))
        	.and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                
             // Spring Security 로그인 처리부분   
            .and()
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/")			//로그인 성공시 세션에 로그인 정보를 담고 / 페이지로이동
                
            // Spring Security 로그아웃 처리부분     
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)    //세션의 담긴 모든 값을 삭제하라
                
        		;
        	
        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    /*
     AuthenticationManager 빈을 생성했다. 
     AuthenticationManager는 스프링 시큐리티의 인증을 담당한다. 
     AuthenticationManager 빈 생성시 스프링의 내부 동작으로 인해 위에서 작성한 
     UserSecurityService와 PasswordEncoder가 자동으로 설정된다.
     */
    
}