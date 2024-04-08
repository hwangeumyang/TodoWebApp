package org.hgy.todospringweb.controller;

import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.hgy.todospringweb.dto.TodoDTO;
import org.hgy.todospringweb.model.TodoEntity;
import org.hgy.todospringweb.dto.ResponseDTO;
import org.hgy.todospringweb.service.TodoService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("todo")
@Slf4j
public class TodoController {
	@Autowired
	private TodoService service;
	
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
		try {			
			int i = 0;
			// 1. entity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			log.info("create {}:: {}", ++i, entity.getId());
			
			// 2. id 초기화
			entity.setId(null);
			log.info("create {}:: {}", ++i, entity.getId());
			
			// 3. set user id, 추후 수정 예정
			entity.setUserId(userId);
			
			// 4. 서비스를 통해 Todo 엔티티 생성
			List<TodoEntity> entities = service.createTodo(entity);
			log.info("create {}:: {}", ++i, entity.getId());
			
			// 5. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// 6. ResponseDTO 초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// 7. ResponseDTO 리턴
			return ResponseEntity.ok(response);			
		} catch(Exception e) {
			// 8. 에러나는 경우 error 메시지 생성 후 리턴
			
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retreieveTodoList(@AuthenticationPrincipal String userId) {
		List<TodoEntity> entities = service.retrieve(userId);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
		// 1. dto -> entity
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		// 2. id 초기화, 인증, 나중에 수정
		entity.setUserId(userId);
		
		// 3. update entity and get list of entity
		List<TodoEntity> entities = service.update(entity);
		
		// 4. entity list -> dto list by java stream
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// 5. initialize ResponseDTO by using TodoDTO transformed
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
		try {
//			String temporaryUserId = "temporary-user";
			
			// 1. dto -> entity
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			// 2. set user id, 추후 수정
			entity.setUserId(userId);
			
			// 3. delete entity
			List<TodoEntity> entities = service.delete(entity);
			
			// 4. entitiy list -> dto list
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// 5. init REsponseEntity
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// 6. return
			return ResponseEntity.ok(response);
		} catch(Exception e) {
			// 7. exception
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
			
			
		}
	}

}
