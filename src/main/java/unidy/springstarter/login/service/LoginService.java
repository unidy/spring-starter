package unidy.springstarter.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unidy.springstarter.login.LoginException;
import unidy.springstarter.user.model.User;
import unidy.springstarter.user.service.UserService;

@Service
public class LoginService {
	@Autowired
	private UserService userService;
	
	public User login(String name, String password) throws LoginException {
		User user = userService.findByNamePassword(name, password);
		if (user == null) {
			throw new LoginException("User not found.");
		}
		
		return user;
	}
	
	public void logout(String name) throws LoginException {
		
	}
}
