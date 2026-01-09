package com.app.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserAuthenticationDto {
	private String username;
	private String password;
}
