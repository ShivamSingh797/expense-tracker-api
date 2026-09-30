package com.projects.expense_tracker_api.mapper;

import org.springframework.stereotype.Component;

import com.projects.expense_tracker_api.dto.UserRequestDto;
import com.projects.expense_tracker_api.dto.UserResponseDto;
import com.projects.expense_tracker_api.entity.User;

@Component
public class UserMapper {

	public User convertRequestToUser(UserRequestDto request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());

		return user;
	}

	public UserResponseDto convertUserToResponse(User user) {
		UserResponseDto dto = new UserResponseDto();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());

		return dto;

	}
}
