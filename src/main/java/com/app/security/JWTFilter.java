package com.app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import com.app.service.TokenService;

import org.springframework.util.StringUtils;

@Configuration
public class JWTFilter extends GenericFilterBean{

	private TokenService tokenService;
	
	JWTFilter() {
		this.tokenService=new TokenService();
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
		response.setHeader("Access-Control-Allow-Methods","GET,POST,PATCH,DELETE,PUT,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers","*");
		response.setHeader("Access-Control-Max-Age","86400");
		
		String token = request.getHeader("Authorization");
		if(StringUtils.hasText(token) && token.startsWith("Bearer"))
		{
			token=token.substring(7,token.length());
		}
		
		if("OPTIONS".equalsIgnoreCase(request.getMethod()))
		{
			response.sendError(HttpServletResponse.SC_OK,"success");
			return;
		}
		
		if(allowRequestWithoutToken(request))
		{
			response.setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(req,res);
		}
		else
		{
			if(token == null || !tokenService.isTokenValid(token)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			else
			{
				ObjectId userId = new ObjectId(tokenService.getUserIdfromToken(token));
				request.setAttribute("userId", userId);
				filterChain.doFilter(req, res);
			}
		}
	}
	
	public boolean allowRequestWithoutToken(HttpServletRequest request)
	{
		System.out.println(request.getRequestURI());
		if(request.getRequestURI().contains("/swagger-ui")||request.getRequestURI().contains("/login") || request.getRequestURI().contains("/register")
				||  request.getRequestURI().contains("/swagger-ui/index.html") || request.getRequestURI().contains("/v3/api-docs")
				|| request.getRequestURI().contains("/favicon.ico")
				)
		{
			return true;
		}
		return false;
	}

}
