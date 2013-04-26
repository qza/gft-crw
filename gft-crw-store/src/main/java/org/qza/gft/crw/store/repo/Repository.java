package org.qza.gft.crw.store.repo;

import java.util.List;
import java.util.Map;

/**
 * @author gft
 */
public interface Repository<C> {

	List<C> findByAttribute(String name, String value, int page, int perpage);

	List<C> fetchAll(Map<String, Object> condition, int page, int perpage);

	void save(C product);
	
	boolean updateAll(String[] ids, String key, Object value);
	
	boolean updateAll(int page, int size, String key, Object value);

	boolean delete(C entity);
	
	boolean delete(String[] ids);

}
