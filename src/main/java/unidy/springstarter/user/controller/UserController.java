package unidy.springstarter.user.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unidy.springstarter.common.log.Loggable;
import unidy.springstarter.user.model.User;
import unidy.springstarter.user.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/user")
@Loggable(level = LogLevel.INFO)
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "/add")
	public User add(String name, String email, String password) {
		return userService.add(name, email, password);
	}

	@PostMapping(path = "/create")
	public User create(@RequestBody User user) {
		return userService.add(user.getName(), user.getEmail(), user.getPassword());
	}

	@GetMapping(path = "users")
	public Iterable<User> listAll() {
		return userService.findAll();
	}
}
