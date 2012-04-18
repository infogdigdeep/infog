package com.digdeep.infog.service.data;

import java.util.Collections;

import javax.ejb.Stateless;

import com.digdeep.infog.dao.GenericDao;
import com.digdeep.infog.model.Group;
import com.digdeep.infog.model.User;
import com.digdeep.infog.model.input.ControlProvisionInput;

@Stateless
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

}
