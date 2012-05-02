package com.digdeep.infog.beans.controller;

import java.security.Principal;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.digdeep.infog.model.User;
import com.digdeep.infog.service.data.UserService;


@ManagedBean
@SessionScoped
public class SessionController {

	
	@Inject
	private UserService userService;
	private User user;
	
	public User getUser() throws Exception {
		if (user == null) {
			Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
			String username = principal.getName();
			user = userService.find(username);
		}
		return user;
	}

	
	
	
}
