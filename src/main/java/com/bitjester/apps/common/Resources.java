package com.bitjester.apps.common;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {
	// Defines the application name.
	@Named
	@Produces
	private String appName = "base2App";

	// Expose an entity manager using the resource producer pattern
	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
