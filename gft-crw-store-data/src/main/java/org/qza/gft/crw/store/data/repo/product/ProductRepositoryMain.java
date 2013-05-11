package org.qza.gft.crw.store.data.repo.product;

import java.util.Set;

import org.qza.gft.crw.store.data.repo.model.Stats;

public interface ProductRepositoryMain {

	Stats stats();
	
	Set<String> collected();
	
	Set<String> visited(int limit, int offset);
	
	void update(Long[] ids, String column, boolean value);
	
}
