package com.bitjester.apps.common.login;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class LoginManager {
	@Inject
	EntityManager em;
	
	public Boolean checkCredentials(String user, String Password){
		return Boolean.TRUE;
	}
	
	public void changePassword(){
		//em.find(arg0, arg1)
	}

}
