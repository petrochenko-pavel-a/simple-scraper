package org.raml.store;

import java.util.List;

public interface IStore {

	
	List<IEntity> allInstances(IResolvableEntityType entity);
	
}
