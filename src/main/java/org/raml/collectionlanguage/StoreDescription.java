package org.raml.collectionlanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class StoreDescription {

	protected String id;
	protected HashMap<String,ApiDescription>apis=new LinkedHashMap<>();
	public HashMap<String, ApiDescription> getApis() {
		return apis;
	}

	public void setApis(HashMap<String, ApiDescription> apis) {
		this.apis = apis;
	}

	protected List<EntryPointDescription>entryPoints=new ArrayList<>();
	
	public List<EntryPointDescription> getEntryPoints() {
		return entryPoints;
	}

	public void setEntryPoints(List<EntryPointDescription> entryPoints) {
		this.entryPoints = entryPoints;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
