package com.mimacom.testproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mimacom.testproject.domain.entity.Task;
import com.mimacom.testproject.exception.TaskNoDataFoundException;
import com.mimacom.testproject.exception.TaskNotFoundException;
import com.mimacom.testproject.repository.TaskRepository;

public class TaskServiceTest {

	@InjectMocks
	private TaskService taskService;

	@Mock
	private TaskRepository taskRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@DisplayName("Test for get All Taks")
	@Test
	public void returnAllTaskTest() {
		when(taskRepository.findAll()).thenReturn(generateAllTask());
		List<Task> getAllTask = taskService.getAllTask();
		assertNotNull(getAllTask);
		assertEquals(1, getAllTask.size());
	}
	
	@DisplayName("Test exception for get All Taks")
	@Test
	public void returnExceptionAllTaskTest() {
		when(taskRepository.findAll()).thenReturn(null);
		try {
			List<Task> getAllTask = taskService.getAllTask();
		} catch (TaskNoDataFoundException ex) {
			assertEquals("No existen tareas en este momento", ex.getMessage());
		}
	}

	@DisplayName("Test for get Only One Taks")
	@Test
	public void returnTaskTest() {
		when(taskRepository.findById(1L)).thenReturn(Optional.of(generateTask()));
		Task task = taskService.getTaskById(1l);
		assertNotNull(task);
		assertEquals("title", task.getTitle());
	}

	@DisplayName("Test exception finding task")
	@Test
	public void exceptionGetTaskTest() {
		when(taskRepository.findById(1L)).thenReturn(Optional.empty());
		try {
			Task task = taskService.getTaskById(1l);
		} catch (TaskNotFoundException ex) {
			assertEquals("No existe una tarea con el id 1 especificado", ex.getMessage());
		}
	}

	@DisplayName("Create Task")
	@Test
	public void createTaskTest() {
		when(taskRepository.save(any())).thenReturn(generateTask());
		Task task = taskService.createTask(generateTask());
		assertNotNull(task);
		assertEquals("title", task.getTitle());
	}

	@DisplayName("Update Task")
	@Test
	public void updateTaskTest() {
		Task task = generateTask();
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		task.setTitle("Title2");
		when(taskRepository.save(any())).thenReturn(task);
		Task task1 = taskService.updateTask(task);
		assertNotNull(task1);
		assertEquals("Title2", task1.getTitle());
	}

	@DisplayName("Update Not found Task")
	@Test
	public void updateNotFoundTaskTest() {
		Task task = generateTask();
		when(taskRepository.findById(1L)).thenReturn(Optional.empty());
		try {
			Task task1 = taskService.updateTask(task);
		} catch (TaskNotFoundException ex) {
			assertEquals("No existe una tarea con el id 1 especificado", ex.getMessage());
		}
	}
	
	@DisplayName("Update Status Task")
	@Test
	public void updateStatusTaskTest() {
		Task task = generateTask();
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		task.setFinish(Boolean.TRUE);
		when(taskRepository.save(any())).thenReturn(task);
		Task task1 = taskService.updateState(task.getId());
		assertNotNull(task1);
		assertEquals(Boolean.TRUE, task1.getFinish());
	}

	@DisplayName("Update Not found Task")
	@Test
	public void updateStatusNotFoundTaskTest() {
		Task task = generateTask();
		when(taskRepository.findById(1L)).thenReturn(Optional.empty());
		try {
			Task task1 = taskService.updateState(task.getId());
		} catch (TaskNotFoundException ex) {
			assertEquals("No existe una tarea con el id 1 especificado", ex.getMessage());
		}
	}
	
	
	@DisplayName("Delete Task")
	@Test
	public void deleteTaskTest() {
		Task task = generateTask();
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		taskService.deleteTask(task.getId());
		
	}

	@DisplayName("Update Not found Task")
	@Test
	public void deleteNotFoundTaskTest() {
		Task task = generateTask();
		when(taskRepository.findById(1L)).thenReturn(Optional.empty());
		try {
			taskService.deleteTask(task.getId());
		} catch (TaskNotFoundException ex) {
			assertEquals("No existe una tarea con el id 1 especificado", ex.getMessage());
		}
	}


	private List<Task> generateAllTask() {
		ArrayList<Task> allTask = new ArrayList<Task>();
		allTask.add(generateTask());
		return allTask;
	}

	private Task generateTask() {
		return new Task(1L, "title", "description", Boolean.FALSE);
	}
}
