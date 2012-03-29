package com.digdeep.infog.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.digdeep.infog.model.ContentInfo;

public class ContentInfoDao {

	@Inject
	private EntityManager eMgr;
	
	public void save(ContentInfo info) {
		eMgr.persist(info);
	}
}
