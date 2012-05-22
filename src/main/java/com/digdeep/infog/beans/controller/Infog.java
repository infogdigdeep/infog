package com.digdeep.infog.beans.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
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
import com.digdeep.infog.test.GetContent;
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
	
	private List<ContentSource> currentSourceList;
	
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
		currentSourceList = provider.getAllByType(reqInput);
		buildTreeNodesFromSrcList(currentSourceList);
	}

	public void updateContents(@Observes ContentInfoUpdateEvent contentInfo) throws Exception {
		ContentSource src = contentInfo.getContentSource();
		for (int i=0; i < getCurrentSourceList().size(); i++) {
			if (getCurrentSourceList().get(i).getLink().equals(src.getLink())) {
				getCurrentSourceList().set(i, src);
			}
		}
	}
	
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	
	public List<ContentSource> getCurrentSourceList() {
		return currentSourceList;
	}

	public void setCurrentSourceList(List<ContentSource> currentSourceList) {
		this.currentSourceList = currentSourceList;
	}

	
	private void buildTreeNodesFromSrcList (List<ContentSource> srcList) {
		TreeNode tmpNode = new DefaultTreeNode("root", null);
		for (ContentSource tmpSrc : srcList) {
			buildTreeNodesFromSrc(tmpSrc, tmpNode);
		}
		setRoot(tmpNode);
	}
	
	private void buildTreeNodesFromSrc (ContentSource tmpSrc, TreeNode rootNode) {
		final String NA = "-";
		Content tmpSourceContent = new Content();
		tmpSourceContent.setTitle(tmpSrc.getTitle());
		tmpSourceContent.setPubDate(null);
		tmpSourceContent.setDetailUrl(NA);
		TreeNode tmpSourceNode = new DefaultTreeNode(tmpSourceContent, rootNode);
		for (Content tmpContent : tmpSrc.getContents()) {
			TreeNode tmpContentNode = new DefaultTreeNode(tmpContent, tmpSourceNode);
		}
	}
	
}
