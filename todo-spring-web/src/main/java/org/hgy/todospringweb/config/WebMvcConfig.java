package org.hgy.todospringweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // regist as spring bean
public class WebMvcConfig implements WebMvcConfigurer {
	private final long MAX_AGE_SECS = 3600;
	public static final String[] ALLOWED_ORIGINS = { "http://localhost:3000",
											"http://app.goldensheep.net/",
											"https://app.goldensheep.net/"};
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//모든 경로
		registry.addMapping("/**")
			.allowedOrigins(ALLOWED_ORIGINS) // origin 허용
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true) //모든 헤더, 인증에 관한 정보 허용
			.maxAge(MAX_AGE_SECS);
	}
}
