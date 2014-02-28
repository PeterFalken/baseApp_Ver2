package com.bitjester.apps.common.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.bitjester.apps.common.entities.AppUser;

@SessionScoped
public class AppSession implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	LoginManager lm;
	
	private AppUser activeUser;
	private AppUser systemUser;
	
	public AppUser getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(AppUser activeUser) {
		this.activeUser = activeUser;
	}
	
	

}
