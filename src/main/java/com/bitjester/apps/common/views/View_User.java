package com.bitjester.apps.common.views;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.DataManager;
import com.bitjester.apps.common.entities.AppUser;
import com.bitjester.apps.common.utils.HashUtil;

@Named
@ViewScoped
public class View_User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private String appName;

	@Inject
	private DataManager dm;

	@Inject
	private EntityManager em;

	// -- Managed Objects
	private AppUser managedUser;

	// -- View methods
	@RequestScoped
	public List<String> getUserStartLetters() throws Exception {
		String query = "SELECT DISTINCT UPPER(SUBSTRING(username,1,1)) AS letter FROM User";
		query += " ORDER BY letter";
		List<String> results = em.createQuery(query, String.class).getResultList();
		results.add(0, "-");
		;
		return results;
	}

	@RequestScoped
	public List<AppUser> getUsersForLetter(String letter) throws Exception {
		String query = "FROM User WHERE username LIKE '" + letter + "%'";
		query += " ORDER BY username";
		return em.createQuery(query, AppUser.class).getResultList();
	}

	// -- Persistence & form methods
	@Named
	@Produces
	public AppUser getManagedUser() {
		return managedUser;
	}

	public void setManagedUser(AppUser managedUser) {
		this.managedUser = managedUser;
	}

	public void load(Long id) throws Exception {
		managedUser = em.find(AppUser.class, id);
		managedUser.setActiveRole(managedUser.getAppRole(appName));
	}

	public void refresh() throws Exception {
		managedUser = null;
	}

	public void remove(Long id) throws Exception {
		dm.remove(em.find(AppUser.class, id));
	}

	public void store() throws Exception {
		if (null == managedUser.getId()) {
			managedUser.setAppRole(appName, "user");
			managedUser.setPassword(HashUtil.calc_HashSHA("123456"));
		} else {
			managedUser.setAppRole(appName, managedUser.getActiveRole());
		}
		dm.store(managedUser);
		managedUser = null;
	}
}
