package com.bitjester.apps.common.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import javax.faces.bean.NoneScoped;
import javax.inject.Inject;

import com.bitjester.apps.common.BaseEntity;
import com.bitjester.apps.common.login.AppSession;

@NoneScoped
public class BookKeeper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AppSession userSession;
	
	@Inject
	private static Logger logger;
	
	public static void log(String message){
		logger.info(message);
	}
	
	private String userInfo() {
		if (null != userSession && null != userSession.getActiveUser())
			return userSession.getActiveUser().getId() + " - " + userSession.getActiveUser().getUsername();
		return "0 - System";
	}

	public void create(BaseEntity entity) {
		entity.setCreateTime(new Date(System.currentTimeMillis()));
		entity.setCreateUser(userInfo());
	}

	public static void create(BaseEntity entity, String userInfo) {
		entity.setCreateTime(new Date(System.currentTimeMillis()));
		entity.setCreateUser(userInfo);
	}
	
	public void delete(BaseEntity entity) {
		logger.info("Delete: " + System.currentTimeMillis());
		logger.info("User: " + userInfo());
		logger.info("Deleting: " + entity);
	}

	public static void delete(BaseEntity entity, String userInfo) {
		logger.info("Delete: " + System.currentTimeMillis());
		logger.info("User: " + userInfo);
		logger.info("Deleting: " + entity);
	}

	public void update(BaseEntity entity) {
		entity.setUpdateTime(new Date(System.currentTimeMillis()));
		entity.setUpdateUser(userInfo());
	}

	public static void update(BaseEntity entity, String userInfo) {
		entity.setUpdateTime(new Date(System.currentTimeMillis()));
		entity.setUpdateUser(userInfo);
	}
}
