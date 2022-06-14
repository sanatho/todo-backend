package com.thomas.todo.service;

import com.thomas.todo.exception.ApiRequestException;
import com.thomas.todo.model.Todo;
import com.thomas.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
/**
 * Classe che si occupa del CRUD del ToDo
 * @author Thomas Sanavia
 */
public class ToDoService {

	private final ToDoRepository repository;

	/**
	 * Ottieni ToDo dell'utente
	 * @param user User dell'utente
	 * @return ToDo dell'utente
	 */
	public ArrayList<Todo> getUserToDo(String user){
		log.info("User {} is retrieving his todo", user);
		ArrayList<Todo> toDoByUserId = repository.findByUserId(user);
		return toDoByUserId;
	}

	/**
	 * Crea un ToDo
	 * @param toDo ToDo da creare
	 */
	public void creteToDo(Todo toDo){
		log.info("User {} create a ToDo", toDo.getUserId());
		repository.save(toDo);
	}

	/**
	 * Metodo che cancella un ToDo
	 * @param toDoId ID del ToDo da cancellare
	 * @throws ApiRequestException ToDo inesistente
	 */
	public void deleteToDo(int toDoId){

		Optional<Todo> todoOptional = repository.findById(toDoId);

		if(todoOptional.isPresent()){
			log.info("ToDo {} is going to be deleted", toDoId);
			repository.deleteById(toDoId);
		}else {
			log.error("ToDo with id {} not exist", toDoId);
			throw new ApiRequestException("ToDo " + toDoId +"ID not exist");
		}


	}

	/**
	 * Aggiorna un ToDo
	 * @param toDoId ToDo da aggiornare
	 * @param newToDo ToDo aggiornato
	 * @throws ApiRequestException ToDo insistente
	 */
	public void updateById(int toDoId, Todo newToDo){
		Optional<Todo> oldToDoOptional = repository.findById(toDoId);

		if(!oldToDoOptional.isPresent()){
			log.error("ToDo with toDoId {} not exist", toDoId);
			throw new ApiRequestException("ToDo " + toDoId +"ID not exist");
		}

		Todo oldToDo = oldToDoOptional.get();

		oldToDo.setBody(newToDo.getBody());
		oldToDo.setTitle(newToDo.getTitle());
		oldToDo.setPriority(newToDo.getPriority());
		repository.save(oldToDo);

		log.info("ToDo with toDoId {} updated", toDoId);
	}
}
