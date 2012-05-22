package com.digdeep.infog.service;

import javax.enterprise.context.RequestScoped;

import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.input.ContentRequestInput;

@RequestScoped
public interface InfoProvider {
	public ContentSource get(ContentRequestInput input) throws Exception;
}
