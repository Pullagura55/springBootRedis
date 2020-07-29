package com.bcbsma.rediscache.repo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcbsma.rediscache.model.User;

@Repository
public interface UserRepository {
	void save(User user);

	Map<String, User> findAll();

	User findById(String id);

	void update(User user);

	void delete(User user);
}