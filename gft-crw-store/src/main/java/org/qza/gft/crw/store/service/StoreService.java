package org.qza.gft.crw.store.service;

import java.util.List;

/**
 * @author gft
 * 
 * @param <C>
 *            Entity class
 */
public interface StoreService<C> {

	void persist(C data);

	List<C> fetchAll(Page page);
	
	List<C> findByName(String name, Page page);
	
	void updateAll(String[] ids, String key, Object value);
	
	void updateAll(Page page, String key, Object value);
	
	void deleteAll(String[] ids);

}
