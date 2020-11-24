package com.mimacom.testproject.exception;

public class TaskNotFoundException extends RuntimeException{
	
	public TaskNotFoundException(Long id) {
		super(String.format("No existe una tarea con el id %d especificado", id));
	}

}
