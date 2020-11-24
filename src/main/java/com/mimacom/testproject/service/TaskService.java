package com.mimacom.testproject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimacom.testproject.domain.entity.Task;
import com.mimacom.testproject.exception.TaskNoDataFoundException;
import com.mimacom.testproject.exception.TaskNotFoundException;
import com.mimacom.testproject.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	

	public List<Task> getAllTask() {
		List<Task> allTask = taskRepository.findAll();
		if (allTask == null || allTask.isEmpty()) {
			throw new TaskNoDataFoundException();
		}
		return allTask;
	}


	public Task getTaskById(Long id) {
		return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id) );
	}


	public Task createTask(Task task) {
		task.setCreateDate(LocalDateTime.now());
		task.setFinish(Boolean.FALSE);
		return taskRepository.save(task);
	}


	public Task updateTask(Task updatedTask) {
		Task task = taskRepository.findById(updatedTask.getId()).orElseThrow(() -> new TaskNotFoundException(updatedTask.getId()) );
		task.setTitle(updatedTask.getTitle());
		task.setDescription(updatedTask.getDescription());
		task.setUpdateDate(LocalDateTime.now());
		task = taskRepository.save(task);
		return task;
	}


	public Task updateState(long id) {
		Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id) );
		task.setFinish(Boolean.TRUE);
		task.setUpdateDate(LocalDateTime.now());
		task = taskRepository.save(task);
		return task;
	}


	public void deleteTask(long id) {
		Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id) );
		taskRepository.delete(task);
	}
	
	
	
	

}
