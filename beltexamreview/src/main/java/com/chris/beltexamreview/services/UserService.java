package com.chris.beltexamreview.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.chris.beltexamreview.models.LoginUser;
import com.chris.beltexamreview.models.User;
import com.chris.beltexamreview.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    public User register(User newUser, BindingResult result) {
    	
    	Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
    	
    	
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    		result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
    	}
    	
    	if (potentialUser.isPresent()) {
    		result.rejectValue("email", "Matches", "Email must be unique");
    	}

    	if(result.hasErrors()) {
    		return null;
    	}
    	
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
    	userRepo.save(newUser);
    	return newUser;  
    }
    
    	
    public User login(LoginUser newLogin, BindingResult result) {
                
    	Optional<User> potentialUser = userRepo.findByEmail(newLogin.getEmail());
    	if (!potentialUser.isPresent()) {
    		result.rejectValue("email","Matches", "Email is not found");
    		return null;
    	}
    	
    	User user = potentialUser.get();  
    	
        if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
    	    result.rejectValue("password", "Matches", "Invalid Password");
    	}
    
    	if(result.hasErrors()) {
    		return null;
    	}
    	    	    	
        return user;
    }
    
    public User findById(Long id) {
    	return userRepo.findById(id).get();
    }
}