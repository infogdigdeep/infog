package com.digdeep.infog.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.digdeep.infog.model.Content;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.input.ContentRequestInput;

@RequestScoped
public interface InfoProvider {
	public ContentSource get(ContentRequestInput input) throws Exception;
}
