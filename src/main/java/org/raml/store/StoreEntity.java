package org.raml.store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class StoreEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final Store parent;
	protected final String url;
	protected boolean isShortRepresentation = true;
	protected final IResolvableEntityType type;

	protected HashMap<String, Object> properties = new LinkedHashMap<>();

	public Set<Entry<String, Object>> entrySet() {
		return properties.entrySet();
	}

	protected HashMap<String, ArrayList<StoreEntity>> children = new LinkedHashMap<>();

	public StoreEntity(Store parent, String url, IResolvableEntityType resolvable) {
		super();
		this.parent = parent;
		this.url = url;
		this.type = resolvable;
	}

	protected void adapt(HttpCache c,JSONObject obj) {
		obj.keys().forEachRemaining(k -> {
			Object object = proceed(c,obj, k);
			properties.put(k.intern(), object);

		});
	}
	public Object property(String name){
		return this.properties.get(name);
	}
	
	

	private Object proceed(HttpCache c,JSONObject obj, String k) {
		Object object = obj.get(k);
		object = proceedObject(c,k, object);
		return object;
	}

	private Object proceedObject(HttpCache cache,String k, Object object) {
		if (object instanceof JSONObject) {
			JSONObject jo = (JSONObject) object;
			String url = getUrl(jo);
			if (url != null) {
				IResolvableEntityType propertyType = this.type.propertyType(k);
				if (propertyType != null) {
					StoreEntity entity = parent.entity(url, propertyType);
					entity.resolve(cache,true, false);
					object = entity;
				} else {
					System.out.println(url + ":" + k + ":" + this.type.name());
				}
			} else {
				HashMap<String, Object> vls = new LinkedHashMap<>();
				jo.keys().forEachRemaining(k1 -> {
					vls.put(k1, proceed(cache,jo, k1));
				});
				object = vls;
			}
		}
		if (object instanceof JSONArray) {
			JSONArray arr = (JSONArray) object;
			ArrayList<Object> result = new ArrayList<>();
			arr.forEach(v -> result.add(proceedObject(cache,k, v)));
			object=result;
		}
		if (object == JSONObject.NULL){
			object=null;
		}
		return object;
	}

	boolean resolved;

	public void resolve(HttpCache cache,boolean readSel, boolean readRelated) {
		if (this.resolved) {
			return;
		}
		this.resolved = true;
		if (readSel) {
			JSONObject object =cache.getObject(this.url);
			this.adapt(cache,object);
		}
		if (readRelated) {
			this.isShortRepresentation=false;
			this.type.nested().forEach(c -> {
				JSONArray readArray =cache.readArray(this.url + c.url());
				ArrayList<StoreEntity> children = new ArrayList<>();
				this.children.put(c.name(), children);
				readArray.forEach(a -> {
					JSONObject no = (JSONObject) a;
					String url = getUrl(no);
					StoreEntity entity = parent.entity(url, c.componentType());
					entity.adapt(cache,no);
					children.add(entity);
					entity.resolve(cache,true, readRelated);
				});
			});
		}
	}

	private String getUrl(JSONObject no) {
		String url = no.optString("url", null);
		return url;
	}

	public IResolvableEntityType type() {
		return this.type;
	}
	
	@Override
	public String toString() {
		if (this.properties.containsKey("name")){
			return this.properties.get("name").toString()+"("+this.type.name()+")";
		}
		if (this.properties.containsKey("login")){
			return this.properties.get("login").toString()+"("+this.type.name()+")";
		}
		if (this.properties.containsKey("url")){
			return this.properties.get("url").toString()+"("+this.type.name()+")";
		}
		
		return "<>("+this.type.name()+")";
	}
}
