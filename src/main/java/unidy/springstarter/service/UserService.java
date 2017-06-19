package unidy.springstarter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import unidy.springstarter.dao.UserDao;
import unidy.springstarter.model.User;

@Component
public class UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	public User add(String name, String email, String password) {
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);

		return userDao.save(user);
	}

	public List<User> findAll() {
		return (List<User>) this.userDao.findAll();
	}

	public User findByNamePassword(String name, String password) {
		List<User> users = userDao.findByNamePassword(name, password);

		return users.size() > 0 ? users.get(0) : null;
	}
}
