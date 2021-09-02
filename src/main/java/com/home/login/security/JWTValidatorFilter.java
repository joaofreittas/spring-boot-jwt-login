package com.home.login.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTValidatorFilter extends BasicAuthenticationFilter {

	private final String HEADER_ATRIBUTO = "Authorization";
	private final String TOKEN_PREFIXO = "Bearer ";

	public JWTValidatorFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain chain) throws IOException, ServletException {

		String atributo = request.getHeader(HEADER_ATRIBUTO);

		if(atributo == null || !atributo.startsWith(TOKEN_PREFIXO)) {
			chain.doFilter(request, response);
			return;
		}

		String token = atributo.replace(TOKEN_PREFIXO, "");
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthenticationToken(token);

		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){
		String usuarioLogin = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.TOKEN_SENHA))
				.build()
				.verify(token)
				.getSubject();

		if(usuarioLogin != null) {
			return new UsernamePasswordAuthenticationToken(usuarioLogin, null, new ArrayList<>());
		}

		return null;
	}
}
