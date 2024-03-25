package org.hgy.todospringweb.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.hgy.todospringweb.dto.TodoDTO;
import org.hgy.todospringweb.model.TodoEntity;
import org.hgy.todospringweb.dto.ResponseDTO;
import org.hgy.todospringweb.service.MyTodoService;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("mytodo")
public class MyTodoController {
	@Autowired
	private MyTodoService service;
	
	@GetMapping("/testTodo")
	public ResponseEntity<?> testTodo() {
		String str = service.testService();
		
//		List<TodoDTO> todoMockList = new ArrayList<>();
//		todoMockList.add(new TodoDTO("id", "msg", true));
//		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(todoMockList).build();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/create")
	public ResponseEntity<?> createTodo(@RequestBody(required = false) TodoDTO todoDTO) {
		//create Todo
		if(todoDTO == null) {
			todoDTO = TodoDTO.builder().title("임시 제목").done(false).build();
		}
		
		TodoEntity todo = todoDTO.toEntity();
		service.createTodo(todo);
		
		//Found todo to return to user
		TodoEntity todoFound = service.selectTodo(todo.getId());
		TodoDTO todoDTOToSubmit = new TodoDTO(todoFound);
		List<TodoDTO> data = new ArrayList<>();
		data.add(todoDTOToSubmit);
		ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(data).build();
		
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/retrieve")
	public ResponseEntity<?> retreiveTodo(@RequestParam(name="id") String id)	{
		ResponseEntity<ResponseDTO<TodoDTO>> responseEntity;
		try {
			TodoEntity todoEntity = service.selectTodo(id);
			ResponseDTO<TodoDTO> responseDTO = TodoEntityToResponseDTO(todoEntity);
			responseEntity = ResponseEntity.ok(responseDTO);
		} catch(EntityNotFoundException ex) {
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error("해당 id로 찾지 못했습니다.").build();
			responseEntity = ResponseEntity.status(500).body(responseDTO);
		}
		return responseEntity; 
	}
	@GetMapping("/check")
	public ResponseEntity<?> updateTodo(@RequestParam(name="id") String id){
		ResponseEntity<ResponseDTO<TodoDTO>> responseEntity;		
		
		try {
			service.CheckTodo(id);
			TodoEntity todoEntity = service.selectTodo(id);
			ResponseDTO<TodoDTO> responseDTO = TodoEntityToResponseDTO(todoEntity);
			responseEntity = ResponseEntity.ok(responseDTO);
		} catch(EntityNotFoundException ex) {
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error("해당 id로 찾지 못했습니다.").build();
			responseEntity = ResponseEntity.status(500).body(responseDTO);
		}
		
		return responseEntity; 
	}
	@GetMapping("/delete")
	public ResponseEntity<?> deleteTodo(@RequestParam String id) {
		service.deleteTodo(id);
		List<String> data = new ArrayList<>();
		data.add("삭제 요청 끝");
		ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(data).build();
		return ResponseEntity.ok().body(responseDTO);
	}
	
	/* 이 아래 HELPER */
	
	private ResponseDTO<TodoDTO> TodoEntityToResponseDTO(TodoEntity todoEntity) {
		TodoDTO todoDTO = new TodoDTO(todoEntity);
		List<TodoDTO> data = new ArrayList<>();
		data.add(todoDTO);
		ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(data).build();
		
		return responseDTO;
	}

}
