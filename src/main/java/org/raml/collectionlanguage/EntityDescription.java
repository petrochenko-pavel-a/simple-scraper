package org.raml.collectionlanguage;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.raml.store.IResolvableEntityType;

public class EntityDescription implements IResolvableEntityType{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String term;
	protected String self;
	
	protected Map<String,CollectionDescription>collections=new LinkedHashMap<>();
	private ApiDescription api;
	
	protected HashMap<String,EntityDescription>properties=new HashMap<>();
	
	public void withProperty(String name,EntityDescription d){
		properties.put(name, d);
	}
	public ApiDescription getApi(){
		return api;
	}
	
	public EntityDescription(String name, ApiDescription api) {
		super();
		this.name = name;
		this.api = api;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTerm() {
		return term;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public String getSelf() {
		return self;
	}
	
	public void setSelf(String self) {
		this.self = self;
	}	
	
	public CollectionDescription collection(String name,EntityDescription range,String url){
		CollectionDescription ds=new CollectionDescription(name,range, this, this.api);
		ds.setRelativeUrl(url);
		this.collections.put(name, ds);
		return ds;
	}
	@Override
	public String name() {
		return this.name;
	}
	@Override
	public Collection<CollectionDescription> nested() {
		return this.collections.values();
	}

	@Override
	public IResolvableEntityType propertyType(String name) {
		return properties.get(name);
	}	
}