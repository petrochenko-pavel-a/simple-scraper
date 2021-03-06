package org.raml.simple.language.dto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.raml.collectionlanguage.StoreDescription;
import org.raml.store.Store;
import org.raml.store.StoreManager;
import org.yaml.snakeyaml.Yaml;

public class StoreLoader {

	
	public static void main(String[] args) {
		Yaml yaml=new Yaml();
		StoreConfiguration loadAs = yaml.loadAs(StoreLoader.class.getResourceAsStream("/model2.yaml"), StoreConfiguration.class);
		StoreDescription ds=new Loader().getStore(loadAs);
		Store store = StoreManager.getStore(ds);
		
		System.out.println(store.entities().size());
	}
}
