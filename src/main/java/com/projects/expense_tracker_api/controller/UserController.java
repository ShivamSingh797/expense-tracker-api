package com.projects.expense_tracker_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.expense_tracker_api.dto.UserRequestDto;
import com.projects.expense_tracker_api.dto.UserResponseDto;
import com.projects.expense_tracker_api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Get all users")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Admin access required") })
	@GetMapping
	public List<UserResponseDto> getAllUsers() {
		return userService.getAllUser();
	}

	@Operation(summary = "Get current logged-in user")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Current user retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized") })
	@GetMapping("/me")
	public UserResponseDto getCurrentUser() {
		return userService.getCurrentUser();
	}

	@Operation(summary = "Get user by ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "User found"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	@GetMapping("/{id}")
	public UserResponseDto getUserById(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@Operation(summary = "Update a user")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "User updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	@PutMapping("/{id}")
	public UserResponseDto updateUserEntry(@PathVariable Long id, @RequestBody UserRequestDto user) {
		return userService.updateUser(id, user);
	}

	@Operation(summary = "Delete a user")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "User deleted successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
