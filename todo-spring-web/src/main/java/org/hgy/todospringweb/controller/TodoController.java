package org.hgy.todospringweb.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.hgy.todospringweb.dto.TodoDTO;
import org.hgy.todospringweb.dto.ResponseDTO;
import org.hgy.todospringweb.service.TodoService;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("todo")
public class TodoController {
	@Autowired
	private TodoService service;
	
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
//	@GetMapping("/create")
//	public String createTodo(@RequestBody TodoDTO todoDTO) {
//		return todoDTO.getTitle();
//	}
	

}
