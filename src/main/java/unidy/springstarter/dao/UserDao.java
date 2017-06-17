package unidy.springstarter.dao;

import org.springframework.data.repository.CrudRepository;

import unidy.springstarter.model.User;

public interface UserDao extends CrudRepository<User, Long> {
	User findByNamePassword(String name, String password);
}
