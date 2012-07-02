package com.digdeep.infog.test;

import java.io.File;

import javax.inject.Inject;

import junit.framework.Assert;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolver;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.EffectivePomMavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.impl.maven.MavenDependencyResolverImpl;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.digdeep.infog.service.JSONProvider;
import com.digdeep.infog.utils.ContentUtil;

@RunWith(Arquillian.class)
public class ContentTest {

	@Deployment
	public static WebArchive createDeployment() {
		/*
		 * return ShrinkWrap.create(WebArchive.class, "test.war")
		 * .addClasses(Member.class, MemberRegistration.class, Resources.class)
		 * .addAsResource("META-INF/persistence.xml",
		 * "META-INF/persistence.xml") .addAsWebInfResource(EmptyAsset.INSTANCE,
		 * "beans.xml");
		 */ 
		EffectivePomMavenDependencyResolver resolver = DependencyResolvers
				.use(MavenDependencyResolver.class)
				.loadSettings("src/test/resource/settings.xml")
				.loadEffectivePom("pom.xml");

		//org.apache.commons:commons-io:1.3.2
		//net.sf.json-lib:json-lib:1.0
		/*
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-io</artifactId>
			<version>1.3.2</version>
			.addAsLibraries(
						resolver.artifact("net.sf.json-lib:json-lib:1.0:jdk15")
								.resolveAsFiles())
		 */
		return ShrinkWrap
				.create(WebArchive.class)
				.addClasses(JSONProvider.class, ContentUtil.class)
				.addAsLibrary(new File("target/test-libs/json-lib-jdk15.jar"))
				.addAsLibrary(new File("target/test-libs/commons-lang.jar"))
				.addAsLibrary(new File("target/test-libs/commons-beanutils.jar"))
				.addAsLibrary(new File("target/test-libs/ezmorph.jar"))
				.addAsLibraries(resolver.artifacts("commons-httpclient:commons-httpclient:3.0","org.apache.commons:commons-io:1.3.2").resolveAsFiles())
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	JSONProvider jsonProv;

	@Test
	public void testJsonProvider() {
		JSONObject obj = jsonProv
				.get("http://openexchangerates.org/latest.json");
		Assert.assertNotNull(obj);
	}

}
