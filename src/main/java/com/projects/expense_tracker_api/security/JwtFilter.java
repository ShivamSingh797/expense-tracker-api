package com.projects.expense_tracker_api.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;

	public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {

		this.jwtUtil = jwtUtil;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authReader = request.getHeader("Authorization");
		System.out.println("AUTH HEADER = " + authReader);

		if (authReader == null || !authReader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authReader.substring(7);

		try {

			String email = jwtUtil.extractUsername(token);

			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

				System.out.println("🔥 JWT VALIDATING");
				if (jwtUtil.validateToken(token, userDetails.getUsername())) {

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(authentication);
					System.out.println("🔥 JWT AUTHENTICATED");
				}
			}

		} catch (ExpiredJwtException e) {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Token expired");
			return;

		} catch (JwtException e) {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid token");
			return;
		}
		filterChain.doFilter(request, response);
	}
}
