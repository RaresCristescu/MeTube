package com.app.data.enums;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public enum Role {
	ROLE_ADMIN,
	ROLE_USER
	
//	USER(Collections.emptySet()),
//	ADMIN(Set.of(Permission.ADMIN_READ, Permission.ADMIN_CREATE, Permission.ADMIN_UPDATE, Permission.ADMIN_DELETE,
//			Permission.MANAGER_READ, Permission.MANAGER_CREATE, Permission.MANAGER_UPDATE, Permission.MANAGER_DELETE)),
//	MANAGER(Set.of(Permission.MANAGER_READ, Permission.MANAGER_CREATE, Permission.MANAGER_UPDATE,
//			Permission.MANAGER_DELETE));
//
//	private final Set<Permission> permissions;
//
//	Role(Set<Permission> permissions) {
//		this.permissions = permissions;
//	}
//
//	public Set<Permission> getPermissions() {
//		return this.permissions;
//	}
//
//	public List<SimpleGrantedAuthority> getAuthorities() {
//		var authorities = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.name()))
//				.collect(Collectors.toList());
//		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//		return authorities;
//	}
}
