package org.raml.store;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class Store implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected HashMap<String,StoreEntity>entities=new HashMap<>();
	
	
	public StoreEntity entity(String url,IResolvableEntityType tp){
		if (entities.containsKey(url)){
			return entities.get(url);
		}
		StoreEntity value = new StoreEntity(this, url,tp);
		entities.put(url, value);
		return value;
	}
	
	public int size(){
		return this.entities.size();
	}
	
	public  byte[] write(){
		ByteArrayOutputStream bs=new ByteArrayOutputStream();
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(bs);
			os.writeObject(this);
			os.close();
			return bs.toByteArray();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}		
	}
	
	public Collection<StoreEntity>entities(){
		return this.entities.values();
	}
}
