package org.raml.collectionlanguage;

import org.raml.store.IResolvableCollection;
import org.raml.store.IResolvableEntityType;

public class CollectionDescription implements IResolvableCollection{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String relativeUrl;
	protected EntityDescription range;
	protected EntityDescription parent;
	protected ApiDescription api;
	private String name;
	
	public CollectionDescription(String name,EntityDescription range, EntityDescription parent, ApiDescription api) {
		super();
		this.range = range;
		this.parent = parent;
		this.api = api;
		this.name=name;
	}
	
	public String getRelativeUrl() {
		return relativeUrl;
	}
	
	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}
	public EntityDescription getRange() {
		return range;
	}
	public void setRange(EntityDescription range) {
		this.range = range;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String url() {
		return this.relativeUrl;
	}

	@Override
	public IResolvableEntityType componentType() {
		return range;
	}
	
}
