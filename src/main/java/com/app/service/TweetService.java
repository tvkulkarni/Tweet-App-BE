package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exception.IncorrectTweetException;
//import com.app.kafka.ProducerService;
import com.app.model.Reply;
import com.app.model.Tweet;
import com.app.model.User;
import com.app.repository.TweetRepository;
import com.app.repository.UserRepository;

@Service
public class TweetService {
	
	@Autowired
	private TweetRepository tweetRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	//@Autowired
	//private ProducerService producerService;
	
	Logger logger = LoggerFactory.getLogger(TweetService.class);

	public Tweet postTweetByUserName(Tweet tweet, String username) {
		logger.info("Inside Tweet Service");
		User user = userRepo.findByUserName(username);
		tweet.setUser(user);
		//producerService.sendMessage("Tweet Posted By the User:"+username+"\n"+tweet.toString());
		logger.info("Tweet Posted Successfully");
		return tweetRepo.save(tweet);
	}
	
	public List<Tweet> getAllTweets()
	{
		//producerService.sendMessage("Recieved Request to fetch all Tweet Data");
		logger.info("Getting All Tweets");
		return tweetRepo.findAll();
	}
	
	public List<Tweet> getAllTweetsByUserName(String uname)
	{
		logger.info("Getting All Tweets of  Username:"+uname);
		return tweetRepo.findByUserUserName(uname);
	}

	public void deleteTweetById(String id) {
		logger.info("Inside Tweet Service");
		logger.info("Deleting Tweet of "+id);
		tweetRepo.deleteById(id);
	}

	public void likeTweetById(String id) {
		logger.info("Inside Tweet Service");
		logger.info("Like Tweet By ID "+id);
		Optional<Tweet> tweet = tweetRepo.findById(id);
		if(tweet.isPresent())
		{
			tweet.get().setLikes(tweet.get().getLikes()+1);
			tweetRepo.save(tweet.get());
		}
		
	}

	public void editTweet(String id,Tweet tweetText) {
		logger.info("Inside Tweet Service");
		logger.info("Editing Tweet");
		Optional<Tweet> parentTweet = tweetRepo.findById(id);
		if(parentTweet.isPresent())
		{
			Tweet tweet = parentTweet.get();
			String newTweetData=tweetText.getTweetName();
			tweet.setTweetName(newTweetData);
			tweetRepo.save(tweet);
			logger.info("Tweet Updated Successfully");
		}
		
	}
	
	public void replyTweetById(Reply replyTweet, String parentTweetId) throws IncorrectTweetException 
	{
		logger.info("Inside Tweet Service");
		logger.info("Reply Tweet By ID "+parentTweetId);
		Optional<Tweet> parentTweet = tweetRepo.findById(parentTweetId);
		if(parentTweet.isPresent())
		{
			
			Tweet tweet = parentTweet.get();
			if(tweet.getReplies()==null)
			{
				List<Reply> x=new ArrayList();
				x.add(replyTweet);
				tweet.setReplies(x);
			}
			else
			{
				tweet.getReplies().add(replyTweet);
			}
			
			//producerService.sendMessage("Replied Success "+replyTweet.getTweetReply());
			tweetRepo.save(tweet);
			logger.info("Replied to Tweet Successfully");
		}
		else
		{
			throw new IncorrectTweetException("Got Incorrect or Deleted Tweet ID");
		}
	}

	public List<Tweet> getUserdTweetByID(String id) throws IncorrectTweetException {
		if(!(id.isEmpty()) )
		{
			logger.info("Inside Get Tweet By ID Service Class");
			List<Tweet> tweetData = tweetRepo.findByTweetId(id);
			return tweetData;
		}
		else
		{
			throw new IncorrectTweetException("Got Incorrect Tweet ID");
		}
		
	}
	
	

}
