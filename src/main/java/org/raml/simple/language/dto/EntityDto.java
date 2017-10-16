package org.raml.simple.language.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityDto {

	private Map<String,String>links=new LinkedHashMap<>();
	private Map<String,String>collections=new LinkedHashMap<>();
	
	public Map<String, String> getLinks() {
		return links;
	}
	public void setLinks(Map<String, String> links) {
		this.links = links;
	}
	public Map<String, String> getCollections() {
		return collections;
	}
	public void setCollections(Map<String, String> collections) {
		this.collections = collections;
	}	
}
