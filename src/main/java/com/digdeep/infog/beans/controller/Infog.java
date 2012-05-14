package com.digdeep.infog.beans.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.digdeep.infog.beans.InfoProviderBean;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.User;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.service.data.ContentInfoService;
import com.digdeep.infog.service.data.UserService;
import com.digdeep.infog.model.Content;

@ManagedBean
@ViewScoped
@Named
public class Infog {

	@Inject
	private InfoProviderBean provider;

	@EJB
	private UserService userService;

	@EJB
	private ContentInfoService infoService;

	private User currentUser;

	public User getCurrentUser() {
		if (getCurrentUser() == null) {
			Principal principal = FacesContext.getCurrentInstance()
					.getExternalContext().getUserPrincipal();
			if (principal != null) {
				setCurrentUser(userService.find(principal.getName()));
			}
		}
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	private List<Content> currentContent;



	public List<Content> getCurrentContent() {
		return currentContent;
	}

	public void setCurrentContent(List<Content> currentContent) {
		this.currentContent = currentContent;
	}

	public void loadContents() throws Exception {
		ContentRequestInput reqInput = new ContentRequestInput();
		reqInput.setType(ContentType.RSS.getType());
		setCurrentContent(provider.get(reqInput));
	}
}
