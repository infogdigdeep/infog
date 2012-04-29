package com.digdeep.infog.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.digdeep.infog.model.Content;
import com.digdeep.infog.model.input.ContentRequestInput;

@RequestScoped
public interface InfoProvider {
	public List<Content> get(ContentRequestInput input) throws Exception;
}
