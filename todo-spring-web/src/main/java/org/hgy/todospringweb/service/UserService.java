package org.hgy.todospringweb.service;

import org.hgy.todospringweb.model.UserEntity;
import org.hgy.todospringweb.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getUsername() == null) {
			throw new RuntimeException("invalid arguemnts");
		}
		final String username = userEntity.getUsername();
		if(userRepository.existsByUsername(username)) {
			log.warn("username arleady exissts {}", username);
			throw new RuntimeException("username already exists");
		}
		
		return userRepository.save(userEntity);
	}
	
	public UserEntity getByCredentials(final String username
			, final String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByUsername(username);
		
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		return null;
	}
	// db가 없어서 유저 생성됐는 지 체크용.
	public java.util.List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

}
