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

	boolean delete(C entity);

}
