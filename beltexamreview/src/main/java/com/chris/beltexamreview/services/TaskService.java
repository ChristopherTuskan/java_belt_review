package com.chris.beltexamreview.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.beltexamreview.models.Task;
import com.chris.beltexamreview.repositories.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepo;
	
	public Task save(Task t) {
		return taskRepo.save(t);
	}
	
	public List<Task> projectTasks(Long id){
		return taskRepo.findByProjectIdIs(id);
	}
	
}
