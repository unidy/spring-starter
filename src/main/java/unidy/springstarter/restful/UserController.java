package unidy.springstarter.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import unidy.springstarter.model.User;
import unidy.springstarter.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/mysql")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/add")
	public @ResponseBody User addNewUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
		return userService.add(name, email, password);
	}
	
	@PostMapping(path = "/create")
	public @ResponseBody User addNewUser(@RequestBody  User user) {
		return userService.add(user.getName(), user.getEmail(), user.getPassword());
	}
	
	@GetMapping(path="all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userService.findAll();
	}
	
	@GetMapping(path="login")
	public @ResponseBody User login(@RequestParam String name, @RequestParam String password) {
		User user = userService.findByNamePassword(name, password);
		if (user != null) {
			user.setPassword("encripted");
		}
		return user;
	}
}
