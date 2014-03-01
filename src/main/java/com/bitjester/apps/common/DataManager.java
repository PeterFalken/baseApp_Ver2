package com.bitjester.apps.common;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.faces.bean.NoneScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.bitjester.apps.common.utils.BookKeeper;

@NoneScoped
@Stateless
public class DataManager implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BookKeeper bk;
	
	@Inject
	private EntityManager em;
	
	public void store(BaseEntity entity) throws Exception{
		if (null == entity.getId()) {
			bk.create(entity);
			em.persist(entity);
		} else {
			bk.update(entity);
			em.merge(entity);
		}
		em.flush();
	}
	
	public void remove(BaseEntity entity) throws Exception {
		if (null != entity) {
			bk.delete(entity);
			em.remove(entity);
			em.flush();
		}
	}

}
