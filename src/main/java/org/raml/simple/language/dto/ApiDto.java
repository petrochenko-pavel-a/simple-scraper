package org.raml.simple.language.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApiDto {

	private String base;
	
	private Map<String,EntityDto>entities=new LinkedHashMap<>();	
	
	private Map<String,String>extras=new LinkedHashMap<>();	
	
	public Map<String, String> getExtras() {
		return extras;
	}

	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}

	private Map<String,String> toplevelCollections=new LinkedHashMap<>();
	private AuthDto auth;
	
	public AuthDto getAuth() {
		return auth;
	}

	public void setAuth(AuthDto auth) {
		this.auth = auth;
	}

	public Map<String, String> getToplevelCollections() {
		return toplevelCollections;
	}

	public void setToplevelCollections(Map<String, String> toplevelCollections) {
		this.toplevelCollections = toplevelCollections;
	}

	public Map<String, EntityDto> getEntities() {
		return entities;
	}

	public void setEntities(Map<String, EntityDto> entities) {
		this.entities = entities;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}
}
