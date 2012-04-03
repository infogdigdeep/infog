package com.digdeep.infog.model.input;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConfigInput {

	private ContentProvisionInput contentInput;
	
	private ControlProvisionInput controlInput;

	public ContentProvisionInput getContentInput() {
		return contentInput;
	}

	public void setContentInput(ContentProvisionInput contentInput) {
		this.contentInput = contentInput;
	}

	public ControlProvisionInput getControlInput() {
		return controlInput;
	}

	public void setControlInput(ControlProvisionInput controlInput) {
		this.controlInput = controlInput;
	}
	
	
	
}
