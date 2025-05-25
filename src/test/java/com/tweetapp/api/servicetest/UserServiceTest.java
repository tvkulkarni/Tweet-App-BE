package com.tweetapp.api.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import com.app.model.User;
import com.app.model.UserResponse;
import com.app.repository.TweetRepository;
import com.app.repository.UserRepository;
import com.app.service.TokenService;
import com.app.service.UserService;
public class UserServiceTest {
	private MockMvc mockMvc;
	
	@Mock
	private UserRepository userrepo;



	@Mock
	private TweetRepository tweetRepo;
	
	
	@Mock
	private TokenService tokenService;



	@InjectMocks
	private UserService userServiceMock = new UserService(userrepo, tokenService);
	
	@BeforeEach
	public void setup() {



	MockitoAnnotations.initMocks(this);
	}



	@Test
	public void registerPositiveTest() throws Exception {
	User registerDTO = new User();
	//registerDTO.setId("id");
	registerDTO.setEmail("fse@gmail.com");
	registerDTO.setfName("admin");
	registerDTO.setlName("admin");
	registerDTO.setPwd("admin");
	registerDTO.setUserName("admin");





	when(userServiceMock.createUser(registerDTO)).thenReturn(registerDTO);
	 User actualresp = userServiceMock.createUser(registerDTO);



	assertEquals(registerDTO, actualresp);
	}

//	@Test
//	public void signUpPostiveTest() throws Exception{
//	User user = new User();
//	//user.setId("id");
//	user.setEmail("fse@gmail.com");
//	user.setfName("admin");
//	user.setlName("admin");
//	user.setPwd("admin");
//	user.setUserName("admin");
//	UserResponse loginRequestDTO=new UserResponse();
//	loginRequestDTO.setUser(user);
//	loginRequestDTO.setLoginStatus("success");
//
//
//	when(userrepo.findListByUserName("admin")).thenReturn((User) user);
//
//	UserResponse actual=userServiceMock.loginUser(user.getUserName(),user.getPwd());
//	assertEquals("success",actual.getLoginStatus());
//	}
	@Test
	public void getAllUsersPositiveTest() throws Exception{

	List<User> registerList = new ArrayList<>();
	User register = new User();
	//register.setId("id");
	register.setEmail("fse@gmail.com");
	register.setfName("admin");
	register.setlName("admin");
	register.setPwd("admin");
	register.setUserName("admin");

	registerList.add(register);

	List<User> expectedList = new ArrayList<>();

	User userDTO = new User();
	//userDTO.setId("id");
	userDTO.setEmail("fse@gmail.com");
	userDTO.setfName("admin");
	userDTO.setlName("admin");
	userDTO.setPwd("admin");
	userDTO.setUserName("admin");

	expectedList.add(userDTO);


	when(userrepo.findAll()).thenReturn(registerList);

	List<User> actual=userServiceMock.getAllUser();
	assertEquals(registerList,actual);
	}

	
}

