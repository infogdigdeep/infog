package com.digdeep.infog.service.data;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.digdeep.infog.dao.ContentInfoDao;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentProvisionInput;

@Stateless
public class ContentInfoService {

	@PersistenceContext(unitName="gf")
	private EntityManager em;
	
	public void save(ContentProvisionInput input) {
		ContentInfo contentInfo = new ContentInfo();
		contentInfo.setType(ContentType.getType(input.getType()));
		contentInfo.setUrl(input.getUrl());
		contentInfo.setDescriptions(input.getDescription());
		em.persist(contentInfo);
	}
}
