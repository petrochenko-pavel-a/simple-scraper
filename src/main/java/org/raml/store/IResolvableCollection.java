package org.raml.store;

import java.io.Serializable;

public interface IResolvableCollection extends Serializable{

	String name();
	
	String url();
	
	IResolvableEntityType componentType();
}
