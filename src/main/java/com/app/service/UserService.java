package com.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.exception.InvalidUsernamePwdException;
import com.app.exception.UserExistException;
import com.app.model.User;
import com.app.model.UserResponse;
import com.app.repository.UserRepository;

@Service
public class UserService {
	
//	@Autowired
//	UserRepository userRepo;
//	
//	@Autowired
//	TokenService tokenService;
	
	private UserRepository userRepo;
	private TokenService tokenService;

	@Autowired
	public UserService(UserRepository userRepo, TokenService tokenService) {
		this.userRepo = userRepo;
		this.tokenService = tokenService;
	}
	
	Logger logger = LoggerFactory.getLogger(TweetService.class);

	public User createUser(User user) throws UserExistException {
		logger.info("Inside Service Class Create User() ");
		if(userRepo.findByUserName(user.getUserName())!=null)
			throw new UserExistException("User name already exist");
		logger.info("Registration Successful");
		User savedUser = userRepo.save(user); 
		return savedUser;
	}
	
	public UserResponse loginUser(String username, String pwd) throws InvalidUsernamePwdException
	{
		logger.debug("Inside User Service Login()");
		UserResponse userResponse = new UserResponse();
		try
		{
			User user = userRepo.findByUserName(username);
			if(user!=null)
			{
				if(user.getPwd().equals(pwd))
				{
					userResponse.setUser(user);
					userResponse.setLoginStatus("login-success");
					userResponse.setToken(tokenService.createToken(user.getId()));
					logger.info(user.getUserName()+" Logged in Succesfully");
				}
				else
				{
					throw new InvalidUsernamePwdException("Invalid PWD Exception");
				}
			}
			else
			{
				throw new InvalidUsernamePwdException("Invalid Username/PWD Exception");
			}
		}
		catch (InvalidUsernamePwdException e) {
			userResponse.setLoginStatus("login-failed");
			logger.info("Login Failed");
			e.printStackTrace();
		}
		return userResponse;
	}

	public List<User> getAllUser() {
		logger.info("Inside User Service Getting All Users");
		return userRepo.findAll();
	}

	public List<User> getUserByUserName(String username) throws InvalidUsernamePwdException {
		if(userRepo.findListByUserName(username) != null)
		{
			logger.info("Getting User by User Name ");
			return userRepo.findListByUserName(username);
		}
		else
		{
			throw new InvalidUsernamePwdException("Got Invalid UserName | Please enter valid data");
		}
	}

	public Map<String,String> forgotPassword(String userName) {
		
		logger.info("Inside User Service Frogot PWD()");
		Map<String, String> x = new HashMap<String, String>();
		User user = userRepo.findByUserName(userName);
		user.setPwd(UUID.randomUUID().toString());
		userRepo.save(user);
		x.put("newPassword", user.getPwd());
		x.put("resetStatus", "success");
		logger.info("Inside User Service Frogot PWD Ended()");
		return x;
	}
	
	
	
	
	

}
