package com.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.app.model.Tweet;

public interface TweetRepository extends MongoRepository<Tweet, String> {

	List<Tweet> findByUserUserName(String uname);
	@Query("{id:?0}")
	List <Tweet> findByTweetId(String id);
}
