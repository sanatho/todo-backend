package com.thomas.todo.service;

import com.thomas.todo.exception.ApiException;
import com.thomas.todo.exception.ApiRequestException;
import com.thomas.todo.model.Priority;
import com.thomas.todo.model.Todo;
import com.thomas.todo.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Profile("testing")
@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

	@Mock
	private ToDoRepository repository;

	@Autowired
	private ToDoService service;

	Todo todo1 = new Todo(1, "1", "ciao", "fare compiti", Priority.LOW);
	Todo todo2 = new Todo(2, "1", "test", "studiare", Priority.NORMAL);

	@BeforeEach
	void setUp() {


		service = new ToDoService(repository);


	}

	@Test
	@DisplayName("Get ToDo for user that exist")
	void getUserToDoExist() {

		given(repository.findByUserId("1")).willReturn(null);
		service.getUserToDo("1");

		verify(repository).findByUserId("1");
	}


	@Test
	@DisplayName("Save ToDo")
	void creteToDo() {
		service.creteToDo(todo1);

		ArgumentCaptor<Todo> argumentCaptor = ArgumentCaptor.forClass(Todo.class);

		verify(repository).save(argumentCaptor.capture());

		Todo captured = argumentCaptor.getValue();
		assertThat(captured).isEqualTo(todo1);
	}

	@Test
	@DisplayName("Delete ToDo that exist")
	void deleteToDo() {
		int id = 1;

		given(repository.findById(id)).willReturn(Optional.of(todo1));

		service.deleteToDo(id);

		verify(repository).deleteById(id);
	}

	@Test
	@DisplayName("Delete ToDo that not exist")
	void deleteToDoThatNotExist() {
		int id = 1;

		given(repository.findById(id)).willReturn(Optional.empty());

		assertThatThrownBy(() -> service.deleteToDo(id))
				.isInstanceOf(ApiRequestException.class)
				.hasMessageContaining("ID not exist");
	}

	@Test
	@DisplayName("Update ToDo that exist")
	void updateToDoThatExist() {
		int id = 1;

		given(repository.findById(id)).willReturn(Optional.of(todo1));

		Todo newToDo = new Todo(1, "1", "Test", "ciao", Priority.LOW);

		service.updateById(id, newToDo);

		ArgumentCaptor<Todo> toDoUpdated = ArgumentCaptor.forClass(Todo.class);

		verify(repository).save(toDoUpdated.capture());

		assertThat(todo1).isEqualTo(newToDo);
	}

	@Test
	@DisplayName("Update ToDo that not exist")
	void updateToDoThatNotExist() {
		int id = 1;

		Todo newToDo = todo2;

		given(repository.findById(id)).willReturn(Optional.empty());

		assertThatThrownBy(() -> service.updateById(id, newToDo))
				.isInstanceOf(ApiRequestException.class)
				.hasMessageContaining("ID not exist");
	}


}