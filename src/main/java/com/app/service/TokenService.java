package com.app.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {
	
	public static final String TOKEN_SECRET = "tweetapp";
	
	public String createToken(String userId) 
	{
		try
		{
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			String token = JWT.create()
						.withClaim("userId", userId.toString())
						.withClaim("createdAt", new Date())
						.sign(algorithm);
			return token;
		}
		catch(JWTCreationException e)
		{
			e.printStackTrace();
		}
		return null;		
	}
	
	public String getUserIdfromToken(String token)
	{
		try
		{
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt.getClaim("userId").asString();
		}
		catch(JWTVerificationException e)
		{
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public boolean isTokenValid(String token) throws UnsupportedEncodingException
	{
		String userId = this.getUserIdfromToken(token);
		return userId!=null;
	}
	

}
