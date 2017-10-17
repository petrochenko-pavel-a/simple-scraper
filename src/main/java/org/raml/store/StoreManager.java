package org.raml.store;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.raml.collectionlanguage.StoreDescription;
import org.raml.simple.language.dto.AuthDto;

public class StoreManager {

	static File root;
	
	public static File getRoot() {
		return root;
	}


	public static void setRoot(File root) {
		StoreManager.root = root;
	}


	static boolean cacheOn=true;
	
	static {
		try {
			root=File.createTempFile("asa", "asas").getParentFile();
		} catch (IOException e) {
			throw new LinkageError();
		}
	}	
	
	
	public static Store getStore(StoreDescription ds){
		HttpCache c=new HttpCache();
		ds.getApis().values().forEach(a->{
			AuthDto ad=a.getAuth();
			if (ad!=null&&ad.getBasic()!=null){
				c.addBasicAuth(a.getBaseUrl(), System.getenv(ad.getBasic().getPassword()),System.getenv(ad.getBasic().getUser()));
			}
			Map<String, String> extras = a.getExtras();
			if (extras!=null){
				c.addExtra(a.getBaseUrl(), extras);
			}
		});
		String url=c.escape(ds.getId());
		Path resolve = root.toPath().resolve(url+".store");
		File file = resolve.toFile();
		if (file.exists()&&cacheOn) {
			try {
				try(ObjectInputStream objectInputStream= new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
				return (Store) objectInputStream.readObject();
				}
			} catch ( IOException e) {
				throw new IllegalStateException(e);
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException(e);
			}
		}
		Store st=new Store();
		ds.getEntryPoints().forEach(e->{
			String localUrl=e.getApi().getBaseUrl()+e.getRelativeUrl();
			st.entity(localUrl, e.getType()).resolve(c,true, true);
		});
		try {
			Files.write(resolve, st.write());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return st;		
	}
}
