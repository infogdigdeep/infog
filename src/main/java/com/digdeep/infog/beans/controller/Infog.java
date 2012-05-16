package com.digdeep.infog.beans.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.enterprise.event.Event;
import javax.faces.bean.ApplicationScoped;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.digdeep.infog.beans.InfoProviderBean;
import com.digdeep.infog.model.ContentInfo;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.User;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.model.message.ContentInfoUpdateEvent;
import com.digdeep.infog.service.data.ContentInfoService;
import com.digdeep.infog.service.data.UserService;
import com.digdeep.infog.model.Content;

@Singleton
@Named
public class Infog {

	@Inject
	private InfoProviderBean provider;

	@EJB
	private UserService userService;

	@EJB
	private ContentInfoService infoService;

	private User currentUser;
	
	private TreeNode root;	
	
	@Inject
	private Event<ContentInfoUpdateEvent> contentUpdateEvent;

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

	@PostConstruct
	public void loadContents() throws Exception {
		ContentRequestInput reqInput = new ContentRequestInput();
		reqInput.setType(ContentType.RSS.getType());
		List<ContentSource> srcList = provider.get(reqInput);
		buildTreeNodesFromSrcList(srcList);
	}

	@Schedule(minute="*/3", hour="*")
	public void updateContents() throws Exception {
		ContentInfoUpdateEvent iEvent = new ContentInfoUpdateEvent();
		iEvent.setUpdateDate(Calendar.getInstance());
		iEvent.setUpdatedContent(new ArrayList<ContentInfo>());
		contentUpdateEvent.fire(iEvent);
	}
	
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	private void buildTreeNodesFromSrcList (List<ContentSource> srcList) {
		TreeNode tmpNode = new DefaultTreeNode("root", null);
		final String NA = "-";
		for (ContentSource tmpSrc : srcList) {
			Content tmpSourceContent = new Content();
			tmpSourceContent.setTitle(tmpSrc.getTitle());
			tmpSourceContent.setPubDate(null);
			tmpSourceContent.setDetailUrl(NA);
			TreeNode tmpSourceNode = new DefaultTreeNode(tmpSourceContent, tmpNode);
			for (Content tmpContent : tmpSrc.getContents()) {
				TreeNode tmpContentNode = new DefaultTreeNode(tmpContent, tmpSourceNode);
			}
		}
	}
}
