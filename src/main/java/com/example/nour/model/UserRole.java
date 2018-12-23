package com.example.nour.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
	CEO,
	ACCOUNTING,
	FINANCE,
	MANAG;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.name();
	}

}
