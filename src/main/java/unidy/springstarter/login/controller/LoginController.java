package unidy.springstarter.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import unidy.springstarter.common.log.Loggable;
import unidy.springstarter.login.LoginException;
import unidy.springstarter.login.service.LoginService;
import unidy.springstarter.user.model.User;

@RestController
@Loggable(level=LogLevel.INFO)
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	@GetMapping(path="/login")
	public User Login(String name, String password) {
		try {
			return loginService.login(name, password);
		} catch(LoginException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@GetMapping(path="/logout")
	public String Logout(String name) {
		try {
			loginService.logout(name);
			
			return "Logout successed.";
		} catch (LoginException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
