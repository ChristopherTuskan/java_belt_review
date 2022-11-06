package com.chris.beltexamreview.controllers;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.chris.beltexamreview.models.LoginUser;
import com.chris.beltexamreview.models.Project;
import com.chris.beltexamreview.models.Task;
import com.chris.beltexamreview.models.User;
import com.chris.beltexamreview.services.ProjectService;
import com.chris.beltexamreview.services.TaskService;
//import com.chris.beltexamreview.services.TaskService;
import com.chris.beltexamreview.services.UserService;


@Controller
public class MainController {
	
	@Autowired
	private UserService userServ;
	
	@Autowired
	private ProjectService projServ;
	
	@Autowired
	private TaskService taskServ;
	    
    
    @GetMapping("/")
    public String index(Model model) {
    
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "loginandregister.jsp";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, 
            BindingResult result, Model model, HttpSession session) {
    	
    	User registeredUser = userServ.register(newUser, result);
        
    	if(result.hasErrors()) {
    		model.addAttribute("newLogin", new LoginUser());
    		return "loginandregister.jsp";
    	}
        
        
    	session.setAttribute("userId", registeredUser.getId());
    
        return "redirect:/dashboard";
    }
    
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
            BindingResult result, Model model, HttpSession session) {
            
    	User loginUser = userServ.login(newLogin, result);
    	
        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "loginandregister.jsp";
        }
            
        
        session.setAttribute("userId", loginUser.getId());
    
        return "redirect:/dashboard";
    
    }   
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
    	if(session.getAttribute("userId") == null) {
    		return "redirect:/";
    	}
    	User user = userServ.findById((Long) session.getAttribute("userId"));
    	List<Project> projects = projServ.findAllProjects();
    	model.addAttribute("user", user);
    	model.addAttribute("projects", projects);
    	
    	return "dashboard.jsp";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "redirect:/";
    }
    
    @GetMapping("/project/new")
    public String newProject(Model model) {
    	
    	model.addAttribute("project", new Project());
    	return "newProject.jsp";
    }
    
    @PostMapping("/project/create")
    public String createProject(@Valid @ModelAttribute("project") Project project, 
            BindingResult result, Model model, HttpSession session) throws ParseException {
    	
    	LocalDate dueDate = LocalDate.parse(project.getDueDate());
    	LocalDate today = LocalDate.now();
    	
    	int dateResult = dueDate.compareTo(today);
    	if (dateResult < 0) {
    		result.rejectValue("dueDate", "Matches", "Due Date needs to be after today");
    	}
    	if(session.getAttribute("userId") == null) {
    		return "redirect:/";
    	}
    	if(result.hasErrors()) {
            return "newProject.jsp";
        }
    	
    	
    	
    	User user = userServ.findById((Long) session.getAttribute("userId"));
    	List<User> users = new ArrayList<User>();
    	users.add(user);
    	project.setLeadUser(user.getUsername());
    	project.setUsers(users);
    	projServ.saveProject(project);
    	
    	return "redirect:/dashboard";
    }
    
    @GetMapping("/project/join/{id}")
    public String joinProject(@PathVariable("id") Long id, HttpSession session) {
    	User user = userServ.findById((Long) session.getAttribute("userId"));
    	Project project = projServ.findById(id);
    	project.getUsers().add(user);
    	projServ.saveProject(project);
    	
    	return "redirect:/dashboard";
    }
    
    @GetMapping("/project/leave/{id}")
    public String leaveProject(@PathVariable("id") Long id, HttpSession session) {
    	User user = userServ.findById((Long) session.getAttribute("userId"));
    	Project project = projServ.findById(id);
    	int userPos = project.getUsers().indexOf(user);
    	project.getUsers().remove(userPos);
    	projServ.saveProject(project);
    	
    	return "redirect:/dashboard";
    }
    
    @GetMapping("/project/edit/{id}")
    public String editProject(@PathVariable("id") Long id, Model model) {
    	
    	Project project = projServ.findById(id);
    	model.addAttribute(project);
    	
    	return "editProject.jsp";
    }
    
    @PostMapping("/project/update/{id}")
    public String updateProject(@Valid @PathVariable("id") Long id, @ModelAttribute("project") Project project, 
            BindingResult result, Model model, HttpSession session) throws ParseException {
    	
    	LocalDate dueDate = LocalDate.parse(project.getDueDate());
    	LocalDate today = LocalDate.now();
    	
    	int dateResult = dueDate.compareTo(today);
    	if (dateResult < 0) {
    		result.rejectValue("dueDate", "Matches", "Due Date needs to be after today");
    	}
    	if(session.getAttribute("userId") == null) {
    		return "redirect:/";
    	}
    	if(result.hasErrors()) {
            return "editProject.jsp";
        }
    	
    	User user = userServ.findById((Long) session.getAttribute("userId"));
    	Project alteredProj = projServ.findById(id);
    	project.setUsers(alteredProj.getUsers());
    	project.setLeadUser(user.getUsername());
    	project.setId(id);
    	
    	projServ.updateProject(project);
    	
    	return "redirect:/dashboard";
    }
    
    @GetMapping("project/show/{id}")
    public String showProject(@PathVariable("id") Long id, Model model, HttpSession session) {
    	
    	Project project = projServ.findById(id);
    	User user = userServ.findById((Long) session.getAttribute("userId"));
    	model.addAttribute("project", project);
    	model.addAttribute("user", user);
    	   	
    	return "showProject.jsp";
    }
    
    @GetMapping("/project/destroy/{id}")
    public String destroyProject(@PathVariable("id") Long id) {
    	
    	Project project = projServ.findById(id);
    	projServ.deleteProject(project);
    	
    	return "redirect:/dashboard";
    }
    
    @GetMapping("/project/{id}/tasks")
    public String newTask(@PathVariable("id") Long id, Model model, @ModelAttribute("task") Task task) {
    	
    	Project project = projServ.findById(id);
    	model.addAttribute("project",project);
    	model.addAttribute("tasks", taskServ.projectTasks(id));
 	
    	return "taskPage.jsp";
    }
    
    @PostMapping("/project/{id}/tasks/create")
    public String createTask(@Valid @PathVariable("id") Long id, @ModelAttribute("task") Task task, Model model, HttpSession session, BindingResult result) {
    	
    	if(session.getAttribute("userId") == null) {
    		return "redirect:/";
    	}
    	if(result.hasErrors()) {
            return "taskPage.jsp";
        }
    	Project project = projServ.findById(id);
    	User user = userServ.findById((Long) session.getAttribute("userId"));
    	
    	task.setProject(project);
    	task.setUser(user);
    	taskServ.save(task);
    	
//    	if (project.getTasks() == null) {
//    		List<Task> tasks = new ArrayList<Task>();
//    		tasks.add(task);
//    		project.setTasks(tasks);
//    	}
//    	else {
//    		project.getTasks().add(task);
//    	}
//    	projServ.saveProject(project);
//    	
    	
    	
    	return "redirect:/project/"+ id +"/tasks";
    }
}
