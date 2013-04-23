package org.qza.gft.crw.store.service;

import java.util.List;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.repo.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductStoreService implements StoreService<Product> {

	@Autowired
	private ProductRepo repo;

	@Override
	public Product persist(Product data) {
		return repo.save(data);
	}

	@Override
	public List<Product> fetchAll() {
		return repo.findAll();
	}

	@Override
	public List<Product> findByName(String name) {
		return repo.findByName(name);
	}

}
