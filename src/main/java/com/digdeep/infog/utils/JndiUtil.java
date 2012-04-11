package com.digdeep.infog.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

public class JndiUtil {

	public Context getInitialContext() throws Exception {
		Context cxt = new InitialContext();
		return cxt;
	}
	
	public UserTransaction getUserTransaction () throws Exception {
		UserTransaction result = (UserTransaction) getInitialContext().lookup("java:comp/UserTransaction");
		return result;
	}
	
	public EntityManager getEntityManager (String pu) throws Exception {
		EntityManager em = (EntityManager) getInitialContext().lookup("java:comp/env/" + pu);
		return em;
	}
}
