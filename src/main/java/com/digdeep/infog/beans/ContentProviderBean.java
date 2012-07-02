package com.digdeep.infog.beans;

import java.util.List;

import com.digdeep.infog.exceptions.InvalidProviderException;
import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.input.ContentRequestInput;

public interface ContentProviderBean {

	public List<ContentSource> getInfo(ContentRequestInput input) throws InvalidProviderException, Exception;

}
