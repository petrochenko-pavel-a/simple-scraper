package org.raml.collectionlanguage;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.raml.simple.language.dto.AuthDto;

public class ApiDescription  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String baseUrl;

	protected AuthDto auth;
	protected Map<String,String>extras;
	
	public Map<String, String> getExtras() {
		return extras;
	}

	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}

	public AuthDto getAuth() {
		return auth;
	}

	public void setAuth(AuthDto auth) {
		this.auth = auth;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	protected LinkedHashMap<String,EntityDescription>entities=new LinkedHashMap<>();
	
	protected LinkedHashMap<String,String>topLevel=new LinkedHashMap<>();

	public LinkedHashMap<String, String> getTopLevel() {
		return topLevel;
	}

	public void setTopLevel(LinkedHashMap<String, String> topLevel) {
		this.topLevel = topLevel;
	}

	public LinkedHashMap<String, EntityDescription> getEntities() {
		return entities;
	}
}