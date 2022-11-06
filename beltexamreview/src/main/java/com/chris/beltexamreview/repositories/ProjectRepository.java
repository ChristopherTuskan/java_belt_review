package com.chris.beltexamreview.repositories;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chris.beltexamreview.models.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    
    

}
