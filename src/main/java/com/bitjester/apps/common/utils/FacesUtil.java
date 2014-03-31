package com.bitjester.apps.common.utils;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class FacesUtil {
	public static void addMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}

	private static ExternalContext getEC() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	private static String getCPath() {
		// Returns String the represents the URI of the deployed app's ROOT.
		return getEC().getRequestContextPath() + "/";
	}

	public static void invalidateSession() {
		getEC().invalidateSession();
	}

	public static void navTo(String url) {
		try {
			getEC().redirect(getCPath() + url);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void navToHome() {
		navTo("index.xhtml");
	}
}
