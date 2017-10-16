package org.raml.store;

import java.io.Serializable;
import java.util.Collection;

public interface IResolvableEntityType extends Serializable{

	String name();
	
	Collection<? extends IResolvableCollection>nested();

	IResolvableEntityType propertyType(String name);
}
