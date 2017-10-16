package org.raml.collectionlanguage;

public class EntryPointDescription {

	protected final ApiDescription api;
	public ApiDescription getApi() {
		return api;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public EntityDescription getType() {
		return type;
	}

	protected final String relativeUrl;
	protected final EntityDescription type;

	public EntryPointDescription(ApiDescription api, String relativeUrl, EntityDescription type) {
		super();
		this.api = api;
		this.relativeUrl = relativeUrl;
		this.type = type;
	}
}
