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
	public List<Product> fetchAll(Page page) {
		return repo.fetchAll(null, page.getNumber(), page.getSize());
	}

	@Override
	public List<Product> findByName(String name, Page page) {
		return repo.findByAttribute("name", name, page.getNumber(), page.getSize());
	}

	@Override
	public void updateAll(int[] ids, String key, Object value) {
		repo.batchUpdate(ids, key, value);
		
	}

	@Override
	public void deleteAll(int[] ids) {
		repo.delete(ids);
	}

}
