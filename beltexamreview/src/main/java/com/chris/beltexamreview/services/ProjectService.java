package com.chris.beltexamreview.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.beltexamreview.models.Project;
import com.chris.beltexamreview.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projRepo;
	
	public Project saveProject(Project p) {
		return projRepo.save(p);
	}
	
	public Project updateProject(Project p) {
		return projRepo.save(p);
	}
	
	public List<Project> findAllProjects(){
		return (List<Project>) projRepo.findAll();
	}
	
	public Project findById(Long id) {
		return projRepo.findById(id).get();
	}
	
	public void deleteProject(Project p) {
		projRepo.delete(p);
	}
}
