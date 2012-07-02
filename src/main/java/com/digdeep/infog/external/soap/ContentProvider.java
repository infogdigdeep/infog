package com.digdeep.infog.external.soap;

import java.util.List;

import javax.jws.WebService;

import com.digdeep.infog.model.ContentSource;
import com.digdeep.infog.model.input.ContentRequestInput;

@WebService
public interface ContentProvider {

	public List<ContentSource> getInfo(ContentRequestInput input) throws Exception;

}
