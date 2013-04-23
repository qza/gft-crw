package org.qza.gft.crw.store.service;

import java.util.List;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.repo.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gft
 */
@Service
public class ProductStoreService implements StoreService<Product> {

	@Autowired
	private ProductRepository repo;

	@Override
	public void persist(Product data) {
		repo.save(data);
	}

	@Override
	public List<Product> fetchAll() {
		return repo.fetchAll(null, 1, 1000000); // TODO
	}

	@Override
	public List<Product> findByName(String name) {
		return repo.findByAttribute("name", name, 1, 1000000); // TODO
	}

}
