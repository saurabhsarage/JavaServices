package com.saurabh.login;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

	@Autowired
	UserRepository ur;
	
	@GetMapping("/all")
	public List<User> getAll(){
		List<User> users = ur.findAll();
		System.out.println(users);
		return users;
	}
	
	@PostMapping("/new")
	public User insert(User usr) {
		ur.save(usr);
		return usr;
	}
	
	@PostMapping("one")
	public User find(@RequestParam("username") String username) {
		User user = ur.findByUsername(username);
		return user;
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("username") String user, @RequestParam("password") String pass) {
		//Optional<User> usr = Optional.of(ur.findByUsername(user));
		User usr = ur.findByUsername(user);
		String d = ((User) usr).getPassword();
		
		if(d.equals(pass)) {
			
			return "Login Successfully";
		}
		else {
			return "User Password Does not Match";
		}
		
		//return "Username:- "+ user +"\nPassWord :- "+ pass+"\nOriginal :- "+d;
	}
	
	@GetMapping("/firstname/{fname}")
	public List<User> byFname(@PathVariable("fname") String name) {
		
		return ur.findAllByFnameIgnoreCase(name);
		
	}
}
