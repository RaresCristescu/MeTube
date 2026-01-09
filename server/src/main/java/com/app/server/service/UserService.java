package com.app.server.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.data.dto.UserDetailsDto;
import com.app.data.dto.UserDto;
import com.app.data.entity.Role;
import com.app.data.entity.User;
import com.app.data.entity.UserRole;
import com.app.data.enums.RoleEnum;
import com.app.data.repo.RoleRepo;
import com.app.data.repo.UserRepo;
import com.app.security.utils.PasswordUtils;

@Service
public class UserService {

	private final UserRepo userRepo;
	private final RoleRepo roleEepo;
	
	private final SecurityService securityService;

	public UserService(UserRepo userRepo, RoleRepo roleEepo, SecurityService securityService) {
		this.userRepo = userRepo;
		this.roleEepo = roleEepo;
		this.securityService = securityService;
	}

	public UserDetailsDto getAccountDetails(final UUID id) {
		User u = userRepo.findById(id).orElseThrow(NoSuchElementException::new);
		UserDetailsDto ud = UserDetailsDto.builder().login(u.getLogin()).email(u.getEmail())
				.role(u.getRole().iterator().next().getRole().getCode().name()).build();
		return ud;
	}

	public void registerUser(UserDto userDto) {
		String hashPwd = PasswordUtils.encode(userDto.getPassword());

		User newUser = new User();
		newUser.setLogin(userDto.getLogin());
		newUser.setEmail(userDto.getEmail());
		newUser.setPassword(hashPwd);
		newUser.setDisabled(false);
		final User dbUser = userRepo.save(newUser);

		Set<UserRole> userRoles = dbUser.getRole();
		List<Role> rList = roleEepo.findByCodes(userDto.getRoles()!=null && !userDto.getRoles().isEmpty()//TODO remove thius check after UI sends the roles
				? userDto.getRoles().stream().map(rDto -> RoleEnum.valueOf(rDto)).collect(Collectors.toList())
				: List.of(RoleEnum.ROLE_USER));
		rList.forEach(r -> userRoles.add(new UserRole(dbUser, r)));
		dbUser.setRole(userRoles);

		userRepo.save(dbUser);
	}
}
