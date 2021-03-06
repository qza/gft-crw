package org.qza.gft.crw.store.data.service.product;

import java.util.Collection;
import java.util.Set;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.model.Stats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author gft
 */
public interface ProductService {
	
	void insertAll(Collection<Message> data);

	Product persist(Product data);
	
	void update(Long[] ids, String column, boolean value);

	Product findByName(String name);

	Page<Product> findAll(Pageable page);
	
	Page<Product> findByCategory(String category, Pageable page);
	
	Page<Product> findByVisited(boolean visited, Pageable page);
	
	Set<String> collected();
	
	Set<String> visited(int limit, int offset);
	
	Stats stats();
	
	long total();

}
