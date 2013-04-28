package org.qza.gft.crw.store.service;

import java.util.List;

import org.qza.gft.crw.store.service.model.Page;
import org.qza.gft.crw.store.service.model.Stats;

import com.mongodb.DBObject;

/**
 * @author gft
 * 
 * @param <C>
 *            Entity class
 */
public interface StoreService<C> {

	String persist(C data);

	List<C> fetchAll(Page page, DBObject criteria);

	List<C> findByName(String name, Page page);

	void updateAll(String[] ids, String key, Object value);

	void updateAll(Page page, String key, Object value, DBObject criteria);

	void deleteAll(String[] ids);

	void deleteAll(String key, String val);
	
	Stats stats();

}
