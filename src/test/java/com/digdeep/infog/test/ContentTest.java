package com.digdeep.infog.test;

import javax.inject.Inject;

import junit.framework.Assert;

import net.sf.json.JSONObject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.digdeep.infog.service.JSONProvider;
import com.digdeep.infog.utils.ContentUtil;

@RunWith(Arquillian.class)
public class ContentTest {
	
	@Deployment
	public static JavaArchive createDeployment() {
		/*
		       return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClasses(Member.class, MemberRegistration.class, Resources.class)
            .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		 */
		return ShrinkWrap.create(JavaArchive.class).addClasses(JSONProvider.class,ContentUtil.class)
		.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	JSONProvider jsonProv;
	
	@Test
	public void testJsonProvider() {
		JSONObject obj = jsonProv.get("http://openexchangerates.org/latest.json");
		Assert.assertNotNull(obj);
	}
	

}
