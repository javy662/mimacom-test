package com.mimacom.testproject.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mimacom.testproject.domain.entity.Task;
import com.mimacom.testproject.exception.TaskNotFoundException;
import com.mimacom.testproject.service.TaskService;
 

public class TaskControllerTest {
	
    @InjectMocks
    private TaskController taskController;
    
    @Mock
    private TaskService taskService; 
                                                 
                                            
    @BeforeEach                           
    void setUp() {                               
       MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getAllTask() {
    	when(taskService.getAllTask()).thenReturn(generateListTask());
    	List<Task> tasks = taskController.getAllTask();
    	assertNotNull(tasks);
    	assertEquals(1, tasks.size());
    }
    
    @Test
    public void getTask() {
    	when(taskService.getTaskById(anyLong())).thenReturn(generateTask());
    	Task task = taskController.getTask("1");
    	assertNotNull(task);
    	assertEquals("title", task.getTitle());
    }
    
    @Test
    public void getTaskException() {
    	when(taskService.getTaskById(anyLong())).thenThrow(new TaskNotFoundException(1l));
    	try {
    	Task task = taskController.getTask("1");
    	} catch (TaskNotFoundException ex) {
    		assertEquals("No existe una tarea con el id 1 especificado", ex.getMessage());
    	}
    }
    
    @Test
    public void deleteTask() {
    	doNothing().when(taskService).deleteTask(1L);
    	taskController.deleteTask("1");
    }
    
    @Test
    public void deleteTaskException() {
    	doThrow(new TaskNotFoundException(1l)).when(taskService).deleteTask(1L);
    	try {
    		taskController.deleteTask("1");
    	} catch (TaskNotFoundException ex) {
    		assertEquals("No existe una tarea con el id 1 especificado", ex.getMessage());
    	}
    }
    
    @Test
    public void createTask() {
    	when(taskService.createTask(any(Task.class))).thenReturn(generateTask());
    	ResponseEntity<Task> task = taskController.createTask(generateTask());
    	assertNotNull(task);
    	assertEquals(HttpStatus.CREATED, task.getStatusCode());
    }
    
    @Test
    public void createTaskBadRequestTitle() {
    	Task task = generateTask();
    	task.setTitle(null);
    	ResponseEntity<Task> taskResponse = taskController.createTask(task);
    	assertEquals(HttpStatus.BAD_REQUEST, taskResponse.getStatusCode());
    }

    @Test
    public void createTaskBadRequestDescription() {
    	Task task = generateTask();
    	task.setDescription(null);
    	ResponseEntity<Task> taskResponse = taskController.createTask(task);
    	assertEquals(HttpStatus.BAD_REQUEST, taskResponse.getStatusCode());
    }
    
    
    @Test
    public void updateTask() {
    	Task task = generateTask();
    	task.setId(1L);
    	when(taskService.updateTask(any(Task.class))).thenReturn(task);
    	ResponseEntity<Task> taskResponse = taskController.updateTask(task);
    	assertNotNull(task);
    	assertEquals(HttpStatus.OK, taskResponse.getStatusCode());
    }
    
    @Test
    public void updateTaskBadRequestTitle() {
    	Task task = generateTask();
    	task.setId(1L);
    	task.setTitle("");
    	when(taskService.updateTask(any(Task.class))).thenReturn(task);
    	ResponseEntity<Task> taskResponse = taskController.updateTask(task);
    	assertEquals(HttpStatus.BAD_REQUEST, taskResponse.getStatusCode());
    }
    
    @Test
    public void updateTaskBadRequestDescription() {
    	Task task = generateTask();
    	task.setId(1L);
    	task.setDescription("");
    	when(taskService.updateTask(any(Task.class))).thenReturn(task);
    	ResponseEntity<Task> taskResponse = taskController.updateTask(task);
    	assertEquals(HttpStatus.BAD_REQUEST, taskResponse.getStatusCode());
    }
    
    @Test
    public void updateTaskException() {
    	doThrow(new TaskNotFoundException(1l)).when(taskService).updateTask(generateTask());
    	try {
    		taskController.updateTask(generateTask());
    	} catch (TaskNotFoundException ex) {
    		assertEquals("No existe una tarea con el id 1 especificado", ex.getMessage());
    	}
    }
    
    @Test
    public void updateStatusTask() {
    	Task task = generateTask();
    	task.setId(1L);
    	task.setFinish(Boolean.TRUE);
    	when(taskService.updateState(1L)).thenReturn(task);
    	ResponseEntity<Task> taskResponse = taskController.finishTask("1");
    	assertEquals(HttpStatus.OK, taskResponse.getStatusCode());
    }
    
    @Test
    public void updateStatusFailedRequestTask() {
    	Task task = generateTask();
    	task.setId(1L);
    	task.setFinish(Boolean.FALSE);
    	when(taskService.updateState(1L)).thenReturn(task);
    	ResponseEntity<Task> taskResponse = taskController.finishTask("1");
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, taskResponse.getStatusCode());
    }
    
    @Test
    public void updateStatusTaskException() {
    	doThrow(new TaskNotFoundException(1l)).when(taskService).updateState(1L);
    	try {
    		taskController.finishTask("1");
    	} catch (TaskNotFoundException ex) {
    		assertEquals("No existe una tarea con el id 1 especificado", ex.getMessage());
    	}
    }
    
	private List<Task> generateListTask() {
		ArrayList<Task> allTask = new ArrayList<Task>();
		allTask.add(generateTask());
		return allTask;
	}

	private Task generateTask() {
		return new Task(1L, "title", "description", Boolean.FALSE);
	}
}
