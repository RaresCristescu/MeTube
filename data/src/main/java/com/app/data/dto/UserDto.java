package com.app.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
	private String login;
	private String email;
	private String password;
}
