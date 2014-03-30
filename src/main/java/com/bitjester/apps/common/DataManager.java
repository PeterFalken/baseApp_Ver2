package com.bitjester.apps.common;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class DataManager implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public void store(BaseEntity entity) throws Exception {
		if (null == entity.getId()) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		em.flush();
	}

	public void remove(BaseEntity entity) throws Exception {
		if (null != entity) {
			em.remove(entity);
			em.flush();
		}
	}
}
