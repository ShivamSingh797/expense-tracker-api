package com.projects.expense_tracker_api.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.projects.expense_tracker_api.dto.LoginRequestDto;
import com.projects.expense_tracker_api.dto.LoginResponseDto;
import com.projects.expense_tracker_api.dto.UserRequestDto;
import com.projects.expense_tracker_api.dto.UserResponseDto;
import com.projects.expense_tracker_api.entity.Expense;
import com.projects.expense_tracker_api.entity.User;
import com.projects.expense_tracker_api.exception.EmailAlreadyExistsException;
import com.projects.expense_tracker_api.exception.ResourceNotFoundException;
import com.projects.expense_tracker_api.mapper.UserMapper;
import com.projects.expense_tracker_api.repository.ExpenseRepository;
import com.projects.expense_tracker_api.repository.UserRepository;
import com.projects.expense_tracker_api.security.JwtUtil;

import jakarta.validation.Valid;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final ExpenseRepository expenseRepository;

	public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtUtil jwtUtil, ExpenseRepository expenseRepository) {

		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.expenseRepository = expenseRepository;
	}

//	public UserResponseDto saveUser(UserRequestDto dto) {
//
//		User user_1=userMapper.convertRequestToUser(dto);
//		User savedUser=userRepository.save(user_1);
//		
//		return userMapper.convertUserToResponse(savedUser);
//	}

	public List<UserResponseDto> getAllUser() {

		return userRepository.findAll().stream().map(userMapper::convertUserToResponse).toList();
	}

	public UserResponseDto getUser(Long id) {

		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		return userMapper.convertUserToResponse(user);
	}

	public UserResponseDto updateUser(Long id, UserRequestDto updatedUser) {
		User user = userRepository.findById(id).orElseThrow();

		user.setName(updatedUser.getName());
		user.setEmail(updatedUser.getEmail());
		user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

		User user_1 = userRepository.save(user);
		return userMapper.convertUserToResponse(user_1);
	}

	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<Expense> expenses = expenseRepository.findByUser(user);
		expenseRepository.deleteAll(expenses);
		userRepository.delete(user);
	}

	public Long totalUserEntry() {
		return userRepository.count();
	}

	public UserResponseDto register(UserRequestDto user) {
		User user_1 = userMapper.convertRequestToUser(user);

		user_1.setRole("USER");

		if (userRepository.existsByEmail(user_1.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists");
		}
		user_1.setPassword(passwordEncoder.encode(user_1.getPassword()));
		User user_2 = userRepository.save(user_1);

		return userMapper.convertUserToResponse(user_2);
	}

	public LoginResponseDto login(@Valid @RequestBody LoginRequestDto dto) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

		String token = jwtUtil.generateToken(dto.getEmail());

		return new LoginResponseDto(token);
	}

	public UserResponseDto getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		return userMapper.convertUserToResponse(user);
	}
}
