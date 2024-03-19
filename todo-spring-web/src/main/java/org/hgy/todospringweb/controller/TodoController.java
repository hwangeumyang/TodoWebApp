package org.hgy.todospringweb.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.hgy.todospringweb.dto.TodoDTO;
import org.hgy.todospringweb.dto.ResponseDTO;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("todo")
public class TodoController {
	
	@GetMapping("/testTodo")
	public ResponseEntity<?> testTodo() {
		List<TodoDTO> todoMockList = new ArrayList<>();
		todoMockList.add(new TodoDTO("id", "msg", true));
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(todoMockList).build();
		
		return ResponseEntity.ok().body(response);
	}
//	@GetMapping("/create")
//	public String createTodo(@RequestBody TodoDTO todoDTO) {
//		return todoDTO.getTitle();
//	}
	

}
