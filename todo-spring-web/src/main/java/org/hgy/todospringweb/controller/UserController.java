package org.hgy.todospringweb.controller;

import org.hgy.todospringweb.dto.ResponseDTO;
import org.hgy.todospringweb.dto.UserDTO;
import org.hgy.todospringweb.model.UserEntity;
import org.hgy.todospringweb.service.UserService;
import org.hgy.todospringweb.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		// 아이디 패스워드로 새로 객체 생성한 다음에 변환해서 전부 보내는 건 낭비같음.
		// 성공 실패 여부만 반환하고, 요청 정보는 어차피 프론트가 가지고 있을 테니 재활용하면 되지 않을까?
		try {
			if(userDTO == null || userDTO.getPassword() == null) {
				throw new RuntimeException("invalid password value");
			}
			UserEntity user = UserEntity.builder()
					.username(userDTO.getUsername())
					.password(userDTO.getPassword())
					.build();
			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDTO = UserDTO.builder()
					.id(registeredUser.getId())
					.username(registeredUser.getUsername())
					.build();
			
			return ResponseEntity.ok(responseUserDTO); // User는 항상 하나이므로 UserDTO 리턴
			
		} catch(Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
		UserEntity user = userService.getByCredentials(
				userDTO.getUsername(),
				userDTO.getPassword());
		
		if(user != null) {
			final String token = tokenProvider.create(user);
			final UserDTO responseUserDTO = UserDTO.builder()
					.username(user.getUsername())
					.id(user.getId())
					.token(token)
					.build();
			return ResponseEntity.ok().body(responseUserDTO);
		} else {
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("Login failed")
					.build();
			return ResponseEntity
					.badRequest()
					.body(responseDTO);
		}
	}
	
	
	
}
