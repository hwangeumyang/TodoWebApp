package org.hgy.todospringweb.security;

import org.hgy.todospringweb.model.UserEntity;
import org.hgy.todospringweb.persistence.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OAuthUserServiceImpl extends DefaultOAuth2UserService {
	@Autowired
	private UserRepository userRepository;
	
	public OAuthUserServiceImpl() {
		super();
	}
	
	@SuppressWarnings("unused")
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// DefaultOAuth2UserSerivice의 기존 loadUser를 호출
		// user-info-uri를 이용해 사용자 정보 가져옴
		final OAuth2User oAuth2User = super.loadUser(userRequest);
		try {
			//디벅깅용 사용자 정보 로깅. 테스트 시에만 사용할 것.
			if(false) //코드는 남기고 싶지만, 사용하게 하고 싶지 않아서 임시처리
			log.info("OAuth2User attributges {} ", new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//login 필드 가져오기
		final String username = (String) oAuth2User.getAttributes().get("login");
		final String authProvider = userRequest.getClientRegistration().getClientName();
		
		UserEntity userEntity = null;
		
		//유저가 없으면 새로 생성
		if(!userRepository.existsByUsername(username)) {
			userEntity = UserEntity.builder()
					.username(username)
					.authProvider(authProvider)
					.build();
			userEntity = userRepository.save(userEntity);
		}
		else {
			userEntity = userRepository.findByUsername(username);
		}
		
		log.info("Sucessfully pulled user info username {} authProvider {}",
				username,
				authProvider);
		
//		return oAuth2User;
		return new ApplicationOAuth2User(userEntity.getId(), oAuth2User.getAttributes());
		
	}

}
