package com.thomas.todo.repository;

import com.thomas.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface ToDoRepository extends JpaRepository<Todo, Integer> {
	ArrayList<Todo> findByUserId(String user_id);



}