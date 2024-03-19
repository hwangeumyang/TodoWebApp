package org.hgy.todospringweb.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.hgy.todospringweb.dto.TestRequestBodyDTO;
import org.hgy.todospringweb.dto.ResponseDTO;

@RestController
@RequestMapping("test") // 리소스
public class TestController {
	//1. 요청
	//1.1. 단순매핑
	@GetMapping
	public String testController() {
		return "Hello World!";
	}
	
	//1.2. Path 추가
	@GetMapping("/testGetMapping")
	public String testControllerWithPath() {
		return "Hello World! testGetMapping with URI";
	}
	
	//1.3. PathVariable
	//@GetMapping(value={"/{id_on_path}", "/"})
	@GetMapping({"/{id_on_path}", "/"}) // /test/{id}
	public String testControllerWithPathVariables(@PathVariable(name = "id_on_path", required = false)Integer id) // int는 값이 없으면 error
	{
		return "Hello World, " + id;
	}
	
	//1.4. RequestParam
	@GetMapping("/testRequestParam")
	public String testControllerWithRequestParam(@RequestParam(name="id", required = false) Integer id) {
		return "hello world, " + id;
	}
	
	//1.5. RequestBody
	@GetMapping("/testRequestBody")
	public String testControllerWithRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO)
	{
		return "hello wolrd!" + testRequestBodyDTO.getId()
		+ " msg: " + testRequestBodyDTO.getMessage();
	}
	
	//2. 응답
	//2.1. ResponseBody, String이 아닌 객체 반환
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		for(int i=0; i<5; ++i) {
			list.add(String.format("hello world, I'm ResponseDTO in %d index", i));	
		}
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}
	//2.2. ResponseEntity
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("hello wolrd!, I'm ResponseEntity and U got 400");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.badRequest().body(response);
//		return ResponseEntity.ok().body(response);
		
	}
		
}
