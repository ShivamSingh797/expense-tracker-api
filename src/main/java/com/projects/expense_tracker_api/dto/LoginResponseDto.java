package com.projects.expense_tracker_api.dto;

public class LoginResponseDto {

	private String token;

	public LoginResponseDto() {
		super();
	}

	public LoginResponseDto(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponseDto [token=" + token + "]";
	}
}
