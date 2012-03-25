package com.digdeep.infog.service;

import javax.enterprise.context.RequestScoped;

import com.digdeep.infog.model.input.ContentRequestInput;

@RequestScoped
public interface InfoProvider {
	public String get(ContentRequestInput input) throws Exception;
}
