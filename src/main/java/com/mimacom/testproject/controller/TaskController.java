package com.mimacom.testproject.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mimacom.testproject.domain.entity.Task;
import com.mimacom.testproject.exception.TaskNoDataFoundException;
import com.mimacom.testproject.exception.TaskNotFoundException;
import com.mimacom.testproject.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/mimacom/")
@Slf4j
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/task",produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<Task> getAllTask(){
		return  taskService.getAllTask();
		
	}
	
	@GetMapping(value = "/task/{id}")
	public Task getTask(@PathVariable(value = "id") String id){
		return taskService.getTaskById(Long.parseLong(id));
	}
	
	@PostMapping(value = "/task")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {		
		if (task == null || validateRequestTask(task)) {
			return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);	
		}
		task = taskService.createTask(task);
		return new ResponseEntity<Task>(task, HttpStatus.CREATED);		
	}
	
	@PutMapping(value = "/task")
	public ResponseEntity<Task> updateTask(@RequestBody Task task) {
		if (task == null || validateRequestTask(task)) {
			return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);	
		}
		task = taskService.updateTask(task);
		
		return new ResponseEntity<Task>(task, HttpStatus.OK);		
	}
	
	@PutMapping(value = "/task/{id}")
	public ResponseEntity<Task> finishTask(@PathVariable(value = "id") String id) {
		Task task = taskService.updateState(Long.parseLong(id));
		if (!task.getFinish()) {
			return new ResponseEntity<Task>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Task>(task, HttpStatus.OK);		
	}
	
	@DeleteMapping(value = "/task/{id}")
	public ResponseEntity<Task> deleteTask(@PathVariable(value = "id") String id) {
		taskService.deleteTask(Long.parseLong(id));
		return new ResponseEntity<Task>(HttpStatus.OK);		
	}
	
	private boolean validateRequestTask(Task task) {
		if (task.getTitle() == null || task.getTitle().isEmpty()) {
			return true;
		}
		if (task.getDescription() == null || task.getDescription().isEmpty()) {
			return true;
		}
		return false;
	}
}
