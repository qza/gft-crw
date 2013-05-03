package org.qza.gft.crw.store.data.repo;

import org.qza.gft.crw.store.data.entity.Product;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository extends CrudRepository<Product, Long>,
		ProductRepositoryMain {

	Product findByName(String name);

	Page<Product> findByCategory(String category, Pageable page);
	
	Page<Product> findByVisited(boolean visited, Pageable page);
	
}