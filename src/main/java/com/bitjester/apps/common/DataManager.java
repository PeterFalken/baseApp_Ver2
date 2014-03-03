package com.bitjester.apps.common;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.utils.BookKeeper;

@SessionScoped
@Stateful
public class DataManager implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private BookKeeper bk;

	@Inject
	private EntityManager em;

	public void store(BaseEntity entity) throws Exception {
		try {
			if (null == entity.getId()) {
				bk.create(entity);
				em.persist(entity);
			} else {
				bk.update(entity);
				em.merge(entity);
			}
			em.flush();
		} catch (Exception e) {
			bk.log(e.getMessage());
			e.printStackTrace();
			throw e;
		}

	}

	public void remove(BaseEntity entity) throws Exception {
		try {
			if (null != entity) {
				bk.delete(entity);
				em.remove(entity);
				em.flush();
			}
		} catch (Exception e) {
			bk.log(e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
