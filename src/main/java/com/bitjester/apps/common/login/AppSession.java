package com.bitjester.apps.common.login;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.bitjester.apps.common.annotations.ActiveUser;
import com.bitjester.apps.common.annotations.SystemUser;
import com.bitjester.apps.common.entities.AppUser;
import com.bitjester.apps.common.utils.FacesUtil;
import com.bitjester.apps.common.utils.HashUtil;

@Named
@SessionScoped
public class AppSession implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private String appName;

	@Inject
	private Credentials creds;

	@Inject
	LoginManager lm;

	private AppUser activeUser;
	private AppUser systemUser;

	// -- System User and Active User.
	// -- Useful for Impersonation
	@SystemUser
	@Named
	@Produces
	public AppUser getSystemUser() {
		return systemUser;
	}

	@ActiveUser
	@Named
	@Produces
	public AppUser getActiveUser() {
		if (null == activeUser)
			return systemUser;
		return activeUser;
	}

	// Impersonate user identified by Long id - logs current user name.
	public void impersonate(Long id) throws Exception {
		AppUser user = lm.getUserForImpersonation(id);
		if (null != user) {
			activeUser = user;
			activeUser.setActiveRole(activeUser.getAppRole(appName));
		}
	}

	// -- Login + Credentials methods

	public void checkCredentials() throws Exception {
		AppUser user = lm.checkCredentials(creds.getUsername(), creds.getPassword());
		if (user != null) {
			this.systemUser = user;
			// Check if user must change the password.
			if (systemUser.getMustChangePassword())
				FacesUtil.navTo("/aforms/password.xhtml");
			// If user has already changed the password.
			systemUser.setActiveRole(systemUser.getAppRole(appName));
			FacesUtil.addMessage("Bienvenido, " + systemUser.getName());
		} else
			FacesUtil.addMessage("Credenciales incorrectas.");
	}

	public void changePassword() throws Exception {
		// Verify if current password matches the one on the database
		if (!systemUser.getPassword().equals(HashUtil.calc_HashSHA(creds.getPassword()))) {
			FacesUtil.addMessage("La contraseña actual no es correcta.");
			return;
		}

		// Verify if newPassword1 is the same as newPassword2
		if (!creds.getNewPassword1().equals(creds.getNewPassword2())) {
			FacesUtil.addMessage("Ambos campos de la nueva contraseña deben coincidir.");
			return;
		}

		// Verify if new password is different from current password
		if (creds.getPassword().equals(creds.getNewPassword1())) {
			FacesUtil.addMessage("La contraseña nueva no puede ser la misma que la anterior.");
			return;
		}

		lm.changePassword(systemUser, creds.getNewPassword1());
		systemUser = null;
		// FacesUtil.invalidateSession();
		FacesUtil.navTo("error/pchanged.xhtml");
	}

	public void logout() throws Exception {
		if (null != systemUser) {
			lm.logOutUser(systemUser);
			FacesUtil.addMessage("Adios, " + systemUser.getName());
		}
		systemUser = null;
		activeUser = null;
		FacesUtil.invalidateSession();
		FacesUtil.navTo("index.html");
	}

	@PreDestroy
	public void cleanUp() {
		try {
			if (null != systemUser)
				lm.logOutUser(systemUser);
			systemUser = null;
			activeUser = null;
			if (null != FacesContext.getCurrentInstance())
				FacesUtil.invalidateSession();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
