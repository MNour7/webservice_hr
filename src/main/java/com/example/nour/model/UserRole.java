package com.example.nour.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
	ROLE_CEO,
	ROLE_FIN_AC,
	ROLE_SALES;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.name();
	}

}
