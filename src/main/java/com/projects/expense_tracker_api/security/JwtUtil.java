package com.projects.expense_tracker_api.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	private SecretKey getSignKey() {

		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateToken(String email) {

		return Jwts.builder().subject(email).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration)).signWith(getSignKey()).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	public boolean validateToken(String token, String email) {

		return extractUsername(token).equals(email) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {

		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {

		return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload().getExpiration();
	}
}
