package com.mimacom.testproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mimacom.testproject.domain.entity.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

	Optional<Task> findById(Long id);

	List<Task> findAll();
}
