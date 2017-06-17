package unidy.springstarter.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import unidy.springstarter.dao.UserDao;
import unidy.springstarter.model.User;

@Component
public class UserService {

	private final static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

//	@Transactional
	public User add(String name, String email, String password) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		
		userDao.save(user);
		
		logger.info(user.toString());
		
		return user;
	}
	
	public ArrayList<User> findAll() {
		return (ArrayList<User>)this.userDao.findAll();
	}
	
	public User findByNamePassword(String name, String password) {
		return userDao.findByNamePassword(name, password);
	}
}
