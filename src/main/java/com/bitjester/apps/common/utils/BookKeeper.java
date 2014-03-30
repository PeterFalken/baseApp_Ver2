package com.bitjester.apps.common.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.bitjester.apps.common.BaseEntity;
import com.bitjester.apps.common.DataManager;
import com.bitjester.apps.common.login.AppSession;

@SessionScoped
public class BookKeeper implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private DataManager dt;

	@Inject
	private AppSession userSession;

	@Inject
	private Logger logger;

	// Static methods
	public static void create(BaseEntity entity, String userInfo) {
		entity.setCreateTime(new Date(System.currentTimeMillis()));
		entity.setCreateUser(userInfo);
	}

	public static void update(BaseEntity entity, String userInfo) {
		entity.setUpdateTime(new Date(System.currentTimeMillis()));
		entity.setUpdateUser(userInfo);
	}

	// Instance methods
	public void log(String message) {
		logger.info(message);
	}

	private String userInfo() {
		if (null != userSession && null != userSession.getActiveUser())
			return userSession.getActiveUser().getId() + " - " + userSession.getActiveUser().getUsername();
		return "0 - System";
	}

	public void create(BaseEntity entity) throws Exception {
		try {
			entity.setCreateTime(new Date(System.currentTimeMillis()));
			entity.setCreateUser(userInfo());
			dt.store(entity);
		} catch (Exception e) {
			log(e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public void delete(BaseEntity entity) throws Exception {
		try {
			logger.info("Delete: " + System.currentTimeMillis());
			logger.info("User: " + userInfo());
			logger.info("Deleting: " + entity);
			dt.remove(entity);
		} catch (Exception e) {
			log(e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public void update(BaseEntity entity) throws Exception {
		try {
			entity.setUpdateTime(new Date(System.currentTimeMillis()));
			entity.setUpdateUser(userInfo());
			dt.store(entity);
		} catch (Exception e) {
			log(e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

}
