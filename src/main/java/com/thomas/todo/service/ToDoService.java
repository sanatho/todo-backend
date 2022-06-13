package com.thomas.todo.service;

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
	 */
	public void deleteToDo(int toDoId){
		// TODO Creare eccezione se non esiste id
		log.info("ToDo {} is going to be deleted", toDoId);
		repository.deleteById(toDoId);
	}

	/**
	 * Aggiorna un ToDo
	 * @param id ToDo da aggiornare
	 * @param newToDo ToDo aggiornato
	 */
	public void updateById(int id, Todo newToDo){
		Optional<Todo> oldToDoOptional = repository.findById(id);

		if(!oldToDoOptional.isPresent()){
			// TODO eccezione
			log.error("ToDo with id {} not exist", id);
		}

		Todo oldToDo = oldToDoOptional.get();

		oldToDo.setBody(newToDo.getBody());
		oldToDo.setTitle(newToDo.getTitle());
		oldToDo.setPriority(newToDo.getPriority());
		repository.save(oldToDo);

		log.info("ToDo with id {} updated", id);
	}
}
