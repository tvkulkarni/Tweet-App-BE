package com.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByUserName(String username);
	List<User> findListByUserName(String username);

}
