package com.bitjester.apps.common.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class FacesUtil {
	public static void addMessage(String message) throws Exception {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}

	private static ExternalContext getEC() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	private static String getCPath() {
		// Returns String the represents the URI of the deployed app's ROOT.
		return getEC().getRequestContextPath() + "/";
	}

	public static void invalidateSession() throws Exception {
		getEC().invalidateSession();
	}

	public static void navTo(String url) throws Exception {
		getEC().redirect(getCPath() + url);
	}
}
