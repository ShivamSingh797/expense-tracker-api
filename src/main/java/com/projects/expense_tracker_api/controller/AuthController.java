package com.projects.expense_tracker_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.expense_tracker_api.dto.LoginRequestDto;
import com.projects.expense_tracker_api.dto.LoginResponseDto;
import com.projects.expense_tracker_api.dto.UserRequestDto;
import com.projects.expense_tracker_api.dto.UserResponseDto;
import com.projects.expense_tracker_api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Operation(summary = "Register a new user")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "User registered successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user data"),
			@ApiResponse(responseCode = "409", description = "Email already exists") })
	@PostMapping("/register")
	public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto user) {
		return userService.register(user);
	}

	@Operation(summary = "Login and generate JWT")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Login successful"),
			@ApiResponse(responseCode = "400", description = "Invalid login data"),
			@ApiResponse(responseCode = "401", description = "Invalid credentials") })
	@PostMapping("/login")
	public LoginResponseDto loginUser(@RequestBody LoginRequestDto user) {
		return userService.login(user);
	}
}
