package org.raml.simple.language.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class StoreConfiguration {

	
	protected String id;
	
	private ArrayList<Map<String,String>>entryPoints=new ArrayList<>();

	private Map<String,ApiDto>apis=new LinkedHashMap<>();

	
	public Map<String, ApiDto> getApis() {
		return apis;
	}

	public void setApis(Map<String, ApiDto> apis) {
		this.apis = apis;
	}

	public ArrayList<Map<String, String>> getEntryPoints() {
		return entryPoints;
	}

	public void setEntryPoints(ArrayList<Map<String, String>> entryPoints) {
		this.entryPoints = entryPoints;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
