package com.digdeep.infog.beans.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.digdeep.infog.beans.InfoProviderBean;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.User;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.service.data.ContentInfoService;
import com.digdeep.infog.service.data.UserService;

@ManagedBean
@ViewScoped
@Model
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
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                setCurrentUser(userService.find(principal.getName()));
            }
        }
        return currentUser;
    }

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	private HashMap<ContentType, String> currentContent;

	public HashMap<ContentType, String> getCurrentContent() {
		return currentContent;
	}

	public void setCurrentContent(HashMap<ContentType, String> currentContent) {
		this.currentContent = currentContent;
	}
	
	public void loadContents () throws Exception {
		List<ContentInfo> infoList = infoService.findAll();
		HashMap<ContentType, String> currentContent = new HashMap<ContentType, String>();
		for (ContentInfo tmpInfo : infoList) {
			String url = tmpInfo.getUrl();
			ContentRequestInput tmpInput = new ContentRequestInput();
			tmpInput.setUrl(tmpInfo.getUrl());
			tmpInput.setType(tmpInfo.getType().getType());
			String content = provider.get(tmpInput);
			currentContent.put(tmpInfo.getType(), content);
		}	
	}
}
