package com.legalfriend.security;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	static final long EXPIRATIONTIME = 1800000; // 10 days
	static final String SECRET = "XY7kmzoNzl100";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static void addAuthentication(HttpServletResponse res, String username, Long clientId) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(TOKEN_PREFIX + " " + JWT);
		authenticationToken.setClientId(clientId.toString());
		res.setContentType("application/json");
		res.getWriter().write(mapper.writeValueAsString(authenticationToken));
		res.getWriter().flush();
		res.getWriter().close();
	}

	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "").trim())
					.getBody().getSubject();

			return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
		}
		return null;
	}

	public static void throwAuthError(HttpServletResponse res) throws JsonProcessingException, IOException {
		JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken("");
		ObjectMapper mapper = new ObjectMapper();
		res.setContentType("application/json");
		res.getWriter().write(mapper.writeValueAsString(authenticationToken));
		res.getWriter().flush();
		res.getWriter().close();
	}

}
