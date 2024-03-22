package org.hgy.todospringweb.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.hgy.todospringweb.model.TodoEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{ //<Entity, Key>
	
	List<TodoEntity> findByUserId(String userId);
	
//	@Query("select * from TodoEntity t where t.userId = ?1")
//	List<TodoEntity> findByUserIdQuery(String userId);
}
