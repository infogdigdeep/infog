package com.digdeep.infog.service.data;

import java.util.Collections;

import javax.ejb.ApplicationException;
import javax.ejb.Stateless;

import com.digdeep.infog.dao.GenericDao;
import com.digdeep.infog.model.Group;
import com.digdeep.infog.model.User;
import com.digdeep.infog.model.input.ControlProvisionInput;

@Stateless(mappedName="userbean")
@ApplicationException(rollback=true)
public class UserService extends GenericDao<User> {

	public UserService() {
		super(User.class);
	}
	
	public void save(ControlProvisionInput input) {
		User user = new User();
		user.setUsername(input.getUsername());
		user.setPassword(input.getPassword());
		user.setEmail(input.getEmail());
		user.setZipCode(input.getZipCode());
		Group group = new Group();
		group.setGroupname(input.getGroupname());
		user.setGroup(Collections.singletonList(group));
		save(user);
	}
	
	public void storeStaticUserInfo() {
		ControlProvisionInput ctlInput = new ControlProvisionInput();
		ctlInput.setUsername("admin");
		ctlInput.setEmail("admin@infog.com");
		
		ctlInput.setPassword("admin");
		ctlInput.setGroupname("ADMIN");
		ctlInput.setZipCode("L3R0A9");
		save(ctlInput);
	}

}
