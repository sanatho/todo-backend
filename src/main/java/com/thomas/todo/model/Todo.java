package com.thomas.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "todo")
public class Todo {
	@Id
	private int id;

	private String userId;

	private String title;
	private String body;

	private Priority priority;
}
