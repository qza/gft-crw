package org.qza.gft.crw.store.repo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.qza.gft.crw.store.service.model.Stats;

import com.mongodb.DBObject;

/**
 * @author gft
 */
public interface Repository<C> {

	List<C> findByAttribute(String name, String value, int page, int perpage);

	List<C> fetchAll(Map<String, Object> condition, int page, int perpage);

	String save(C product);

	boolean updateAll(String[] ids, String key, Object value);

	boolean updateAll(int page, int size, String key, Object value, DBObject criteria);

	boolean delete(C entity);

	boolean deleteAll(String[] ids);

	boolean deleteAll(String key, String val);
	
	Stats stats();
	
	void insertAll(Collection<String> data);

}
