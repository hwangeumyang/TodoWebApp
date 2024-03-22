package org.hgy.todospringweb.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.hgy.todospringweb.persistence.TodoRepository;

import java.util.List;

import org.hgy.todospringweb.model.TodoEntity;


@Service
public class TodoService {
	@Autowired
	private TodoRepository repository;
	
//	private JpaRepository<TodoEntity, String> repository;
	
	public String testService() {
		TodoEntity entity = TodoEntity.builder().title("My first Todo item").build();
		repository.save(entity);
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		
		return String.format("%s %s %d", savedEntity.getId(), savedEntity.getTitle(), repository.count());
	}
	public void createTodo(TodoEntity todo) {
		repository.save(todo);
	}
	public TodoEntity selectTodo(String id) {
		return repository.getReferenceById(id);
	}
	public List<TodoEntity> retreiveTodoByUserId(String userId) {
		return repository.findByUserId(userId);
	}
	public void UpdateTodo(String id, String nextTitle) {
		TodoEntity entity = repository.getReferenceById(id);
		entity.setTitle(nextTitle);
		repository.save(entity);
	}
	public void CheckTodo(String id) {
		TodoEntity entity = repository.getReferenceById(id);
		entity.setDone(!entity.isDone());
		repository.save(entity);
	}
	public void deleteTodo(String id) {
		repository.deleteById(id);
	}
}
