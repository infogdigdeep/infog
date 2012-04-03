package com.digdeep.infog.beans;


import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;


import com.digdeep.infog.exceptions.InvalidProviderException;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.input.ContentRequestInput;
import com.digdeep.infog.qualifiers.ContentConfig;
import com.digdeep.infog.service.InfoProvider;

@RequestScoped
public class InfoProviderBean {
	
	@Inject @Any
	private Instance<InfoProvider> providers;
	
	
	public String get(ContentRequestInput input) throws Exception {
		ContentType contentType = ContentType.getType(input.getType());
		ContentConfigQualifier ccQual = new ContentConfigQualifier(contentType);
		Instance<InfoProvider> qualProvider = providers.select(ccQual);
		if (qualProvider.isUnsatisfied() || qualProvider.isAmbiguous()) {
			throw new InvalidProviderException(input.getType());
		}
		return qualProvider.get().get(input);
	}
	
	
    class ContentConfigQualifier extends AnnotationLiteral<ContentConfig>
    implements ContentConfig {
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ContentType type;
    	
    	public ContentConfigQualifier(ContentType type) {
    		this.type = type;
    	}
    	
		@Override
		public ContentType contentType() {
			// TODO Auto-generated method stub
			return type;
		}
    	
    };

}
