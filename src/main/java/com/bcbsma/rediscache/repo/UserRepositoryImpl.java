package com.bcbsma.rediscache.repo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bcbsma.rediscache.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private static final String KEY = "Users";

	private RedisTemplate<String, User> redisTemplate;
	private HashOperations hashOperations; // to access Redis cache

	@Autowired
	public UserRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(User user) {
		 hashOperations.put(KEY, user.getId(), user);
		//redisTemplate.opsForList().leftPush(KEY, user);

	}

	@Override
	public Map<String, User> findAll() {
		// return redisTemplate.opsForList().leftPop(KEY);
		return hashOperations.entries(KEY);

	}

	@Override
	public User findById(String id) {
		return (User) hashOperations.get(KEY, id);
	}

	@Override
	public void update(User user) {
		save(user);
	}

	@Override
	public void delete(User user) {
		hashOperations.delete(user.getId());
	}
}
