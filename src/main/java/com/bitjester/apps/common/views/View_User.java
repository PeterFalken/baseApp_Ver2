package com.bitjester.apps.common.views;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ViewScoped
public class View_User {
	@Inject
	EntityManager em;

}
