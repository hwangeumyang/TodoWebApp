package org.hgy.todospringweb.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.hgy.todospringweb.persistence.TodoRepository;
import org.hgy.todospringweb.model.TodoEntity;

@Slf4j
@Service
public class TodoService {
	@Autowired
	TodoRepository repository;
	
	public List<TodoEntity> createTodo(final TodoEntity entity) {
		validate(entity);
		log.info("Entity Id: {} is saved.", entity.getId());
		repository.save(entity);
		log.info("Entity Id: {} is saved.", entity.getId());
		return repository.findByUserId(entity.getUserId());
	}
	
	public List<TodoEntity> retrieve(final String userId) {
		log.info("find by userID {}", userId);
		return repository.findByUserId(userId);
	}
	
	public List<TodoEntity> update(final TodoEntity entity) {
		validate(entity);
		
		log.info("update {}", entity.toString());
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		original.ifPresent(todo -> {
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			repository.save(todo);
		});
		
		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete(final TodoEntity entity) {
		// 1. validation
		validate(entity);
		
		try {
			// 2. try to delete
			repository.delete(entity);
		} catch(Exception e) {
			//3 if exception occured, logging id
			log.error("error deleting entitty", entity.getId(), e);
			
			//4. throw exception to controller, 새로운 exception 오브젝트를 리턴해서 db 내부로직을 캡슐화
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		// 5. return new Todo List
		return retrieve(entity.getUserId());
	}

	private void validate(TodoEntity entity) {
		if(entity == null) {
			log.warn("entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
		if(entity.getUserId() == null) {
			log.warn("unknown user.");
			throw new RuntimeException("unknwon user");
		}
	}
}
