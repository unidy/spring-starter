package unidy.springstarter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import unidy.springstarter.model.User;

public interface UserDao extends JpaRepository<User, Long> {

	@Query("select u from User u where u.name = :name and u.password = :password")
	List<User> findByNamePassword(@Param("name") String name, @Param("password") String password);

}
