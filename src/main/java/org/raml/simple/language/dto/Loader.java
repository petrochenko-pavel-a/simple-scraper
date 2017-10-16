package org.raml.simple.language.dto;

import org.raml.collectionlanguage.ApiDescription;
import org.raml.collectionlanguage.EntityDescription;
import org.raml.collectionlanguage.EntryPointDescription;
import org.raml.collectionlanguage.StoreDescription;

public class Loader {

	private ApiDescription createApi(ApiDto d){
		ApiDescription api=new ApiDescription();
		api.setBaseUrl(d.getBase());
		d.getEntities().entrySet().forEach(e->{
			api.getEntities().put(e.getKey(),new EntityDescription(e.getKey(), api));
		});
		d.getToplevelCollections().forEach( (k,v)->{
			api.getTopLevel().put(k, v);
		});
		d.getEntities().forEach( (k,v)->{
			fillEntity(api.getEntities().get(k),v);
		});
		api.setAuth(d.getAuth());
		api.setExtras(d.getExtras());
		return api;
	}
	
	private void fillEntity(EntityDescription entityDescription, EntityDto v) {
		if (v!=null){
		v.getLinks().forEach( (k,l)->{
			entityDescription.withProperty(k, entityDescription.getApi().getEntities().get(l));
		});
		v.getCollections().forEach((k,c)->{
			entityDescription.collection(k, entityDescription.getApi().getEntities().get(c), "/"+k);
		});
		}
	}

	public StoreDescription getStore(StoreConfiguration cfg){
		StoreDescription ds=new StoreDescription();
		ds.setId(cfg.getId());
		cfg.getApis().forEach((k,v)->{
			ds.getApis().put(k,createApi(v));
		});
		cfg.getEntryPoints().forEach(
				e->e.forEach(
				(k,v)->{
			String api=k.substring(0,k.indexOf('.'));
			String collection=k.substring(k.indexOf('.')+1);
			ApiDescription realApi = ds.getApis().get(api);
			String entityType=realApi.getTopLevel().get(collection);
			EntityDescription ed=realApi.getEntities().get(entityType);
			EntryPointDescription epd=new EntryPointDescription(realApi,collection+"/"+v,ed);
			ds.getEntryPoints().add(epd);
		}));
		return ds;
	}
}
