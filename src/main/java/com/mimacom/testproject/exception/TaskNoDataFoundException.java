package com.mimacom.testproject.exception;

public class TaskNoDataFoundException extends RuntimeException{

	public TaskNoDataFoundException() {
		super("No existen tareas en este momento");
	}
}
