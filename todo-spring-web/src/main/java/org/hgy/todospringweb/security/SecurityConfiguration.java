package org.hgy.todospringweb.security;

import org.hgy.todospringweb.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//        	.cors() //webmvcconfig에서 설정한 기본 cors 설정
//        		.and()
//    		.csrf(csrfConfig -> csrfConfig.disable())
//        	.cors(AbstractHttpConfigurer::disable) //webmvcconfig에서 설정, 기본 cors 설정, 
        	.csrf(AbstractHttpConfigurer::disable) // csrf는 현재 미사용
        	.httpBasic(AbstractHttpConfigurer::disable) // token을 사용하므로 미사용
        	.sessionManagement( // 세션기반 아님
        			(sessionManagement) -> sessionManagement
        								.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	)
        	.authorizeHttpRequests((authorizeRequests) -> // /, /auth/**는 인증안함
        							authorizeRequests
        							.requestMatchers("/", "/auth/**").permitAll()
        							.anyRequest().authenticated())
        	.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
            ;

        //필터 등록, 매 요청마다 corsfilter 실행 후 jwtauthenticationfilter 실행
//        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
            
        return http.build();
    }
}
