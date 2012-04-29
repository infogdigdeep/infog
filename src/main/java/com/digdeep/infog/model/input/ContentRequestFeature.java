package com.digdeep.infog.model.input;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContentRequestFeature {
	private boolean needWoeId;

	public boolean isNeedWoeId() {
		return needWoeId;
	}

	public void setNeedWoeId(boolean needWoeId) {
		this.needWoeId = needWoeId;
	}
	
	
}
