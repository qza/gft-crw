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
	
	void updateAll(int[] ids, String key, Object value);
	
	void deleteAll(int[] ids);

}
