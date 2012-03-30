package com.digdeep.infog.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.digdeep.infog.model.ContentInfo;

public class ContentInfoDao {

	@PersistenceContext(unitName="gf")
	private EntityManager eMgr;
	
	public void save(ContentInfo info) {
		eMgr.persist(info);
	}
}
