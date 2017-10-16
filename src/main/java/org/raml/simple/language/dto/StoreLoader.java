package org.raml.simple.language.dto;

import org.raml.collectionlanguage.StoreDescription;
import org.raml.store.Store;
import org.raml.store.StoreManager;
import org.yaml.snakeyaml.Yaml;

public class StoreLoader {

	
	public static void main(String[] args) {
		Yaml yaml=new Yaml();
		StoreConfiguration loadAs = yaml.loadAs(StoreLoader.class.getResourceAsStream("/model.yaml"), StoreConfiguration.class);
		StoreDescription ds=new Loader().getStore(loadAs);
		Store store = StoreManager.getStore(ds);
		System.out.println(store);
	}
}
