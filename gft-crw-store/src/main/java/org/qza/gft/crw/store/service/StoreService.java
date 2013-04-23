package org.qza.gft.crw.store.service;

import java.util.List;

public interface StoreService<C> {

	C persist(C data);
	
	List<C> findByName(String name);

	List<C> fetchAll();

}
