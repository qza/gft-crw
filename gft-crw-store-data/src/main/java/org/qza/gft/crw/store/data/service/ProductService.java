package org.qza.gft.crw.store.data.service;

import java.util.Collection;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.store.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author gft
 */
public interface ProductService {
	
	void insertAll(Collection<Message> data);

	Product persist(Product data);

	Product findByName(String name);

	Page<Product> findByCategory(String category, Pageable page);

}
