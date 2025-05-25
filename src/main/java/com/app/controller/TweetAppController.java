package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.IncorrectTweetException;
import com.app.exception.InvalidUsernamePwdException;
import com.app.exception.UserExistException;
import com.app.model.Reply;
import com.app.model.Tweet;
import com.app.model.User;
import com.app.model.UserResponse;
import com.app.service.TweetService;
import com.app.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/app/v1.0/tweets")
@SecurityRequirement(name="BearerAuth")
public class TweetAppController {

	@Autowired
	UserService userService;
	
	@Autowired
	TweetService tweetService;
	
	Logger logger = LoggerFactory.getLogger(TweetAppController.class);
	
	@GetMapping("/check")
	public String demoPage()
	{
		return "Running..!";
	}
	
//	*********************** API'S FOR USER START *******************
	
	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody User user) throws UserExistException
	{
		logger.info("User Created Successfully");
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(Model model, @RequestBody User user, HttpServletRequest request) throws InvalidUsernamePwdException
	{
		logger.debug("Inside Controller Login()");
		try
		{
			UserResponse authUser = userService.loginUser(user.getUserName(),user.getPwd());
			if(authUser == null)
			{
				
				throw new InvalidUsernamePwdException("Recieved Null data for Username and pwd");
				
			}
			else
			{
				request.getSession().setAttribute("user", user.getUserName());
				return new ResponseEntity<UserResponse>(authUser, HttpStatus.OK);
			}
		}
		catch(InvalidUsernamePwdException e)
		{
			throw new InvalidUsernamePwdException("Invalid Username and pwd");
		}
		
	}
	
	@GetMapping("/users/all")
	public ResponseEntity<List<User>> getAllUsers()
	{
		logger.info("Inside GetAllUsers()");
		return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
	}
	
	@GetMapping("/user/search/{username}")
	public ResponseEntity<List<User>> searchUser(@PathVariable ("username") String username) throws InvalidUsernamePwdException
	{
		logger.info("Inside Seearch User By Username()");
		List<User> userList = userService.getUserByUserName(username);
		if(!userList.isEmpty())
		{
			return new ResponseEntity<>(userList,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
	}

	@ResponseBody	
	@GetMapping("/{username}/forgot")
	public Map<String, String> forgotPassword(@PathVariable("username") String username)
	{
		logger.info("Inside Forgot Password()");
		return new HashMap<String, String>(userService.forgotPassword(username));
	}
	
//	*********************** API'S FOR USER END *******************
	
//	*********************** API'S FOR TWEETS START *******************
	
	@PostMapping("/{username}/add")
	public ResponseEntity<Tweet> postTweetByUserName(@PathVariable("username") String username, @RequestBody Tweet tweet)
	{
		logger.info("Inside Post Tweet()");
		return new ResponseEntity<>(tweetService.postTweetByUserName(tweet, username),HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}/delete/{id}")
	public ResponseEntity<String> deleteTweeet(@PathVariable("username") String username, @PathVariable("id") String id)
	{
		logger.info("Inside Delete Tweet()");
		tweetService.deleteTweetById(id);
		return new ResponseEntity<>("Tweet Deleted Successfully",HttpStatus.OK);
	}
	
	@PutMapping("/{username}/like/{id}")
	public ResponseEntity<?> likeTweet(@PathVariable("username") String username, @PathVariable("id") String id)
	{
		logger.info("Inside Like Tweet()");
		tweetService.likeTweetById(id);
		return new ResponseEntity<>("Tweet Liked Successfully",HttpStatus.OK);
		
	}
	
	@PutMapping("/{username}/update/{id}")
	public ResponseEntity<?> updateTweetByUserId(@PathVariable("username") String username, @PathVariable("id") String id, @RequestBody Tweet tweetData)
	{
		logger.info("Inside Update Tweet()");
		tweetService.editTweet(id,tweetData);
		return new ResponseEntity<>("Tweet Updated Successfully", HttpStatus.OK);
	}
	
	
	@GetMapping("/getTweetById/{id}")
	public ResponseEntity<List<Tweet>> getTweetById(@PathVariable("id") String id) throws IncorrectTweetException
	{
		logger.info("Inside Get Tweet By ID");
		return new ResponseEntity<>(tweetService.getUserdTweetByID(id),HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Tweet>> getAllTweets()
	{
		logger.info("Inside Get All Tweets()");
		return new ResponseEntity<>(tweetService.getAllTweets(),HttpStatus.OK);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<List<Tweet>> getAllTweetsByUser(@PathVariable ("username") String username)
	{
		logger.info("Inside Get All Tweets By Username()");
		return new ResponseEntity<>(tweetService.getAllTweetsByUserName(username),HttpStatus.OK);
	}
	
	@PostMapping("/{username}/reply/{id}")
	@ResponseBody
	public ResponseEntity<?> replyTweetByUser(@PathVariable("username") String username, @PathVariable("id") String id, @RequestBody Reply replyTweet)
	{
		System.out.println("Inside Reply Tweet");
		logger.info("Inside Reply Tweet()");
		try
		{
			tweetService.replyTweetById(replyTweet, id);
			return new ResponseEntity<>("Replied ", HttpStatus.OK);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new Tweet(), HttpStatus.OK);
		}
	}
	
}
