package com.thomas.todo.controller;

import com.thomas.todo.service.ToDoService;
import com.thomas.todo.model.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
@CrossOrigin("*")
public class ToDoController {
	private final ToDoService service;

	@GetMapping("/{userId}")
	public ArrayList<Todo> getUserToDo(@PathVariable String userId){
		System.out.println(UUID.randomUUID());
		return service.getUserToDo(userId);
	}
	@PostMapping()
	public void saveToDo(@RequestBody Todo toDo){
		service.creteToDo(toDo);
	}

	@DeleteMapping("/{todoId}")
	public void deleteToDoById(@PathVariable int todoId){
		service.deleteToDo(todoId);
	}

	@PatchMapping("/{todoId}")
	public void updateToDoById(@PathVariable int todoId, @RequestBody Todo newTodo){
		service.updateById(todoId, newTodo);
	}
}
