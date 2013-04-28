package org.qza.gft.crw.store.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.repo.ProductRepository;
import org.qza.gft.crw.store.service.model.Page;
import org.qza.gft.crw.store.service.model.Stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;

/**
 * @author gft
 */
@Service
public class ProductStoreService implements StoreService<Product> {

	@Autowired
	private ProductRepository repo;

	@Override
	public String persist(Product data) {
		return repo.save(data);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> fetchAll(Page page, DBObject criteria) {
		Map<String, Object> data = criteria != null ? criteria.toMap() : null;
		return repo.fetchAll(data, page.getNumber(), page.getSize());
	}

	@Override
	public List<Product> findByName(String name, Page page) {
		return repo.findByAttribute("name", name, page.getNumber(),
				page.getSize());
	}

	@Override
	public void updateAll(String[] ids, String key, Object value) {
		repo.updateAll(ids, key, value);
	}

	@Override
	public void deleteAll(String[] ids) {
		repo.deleteAll(ids);
	}
	
	@Override
	public void deleteAll(String key, String val) {
		repo.deleteAll(key, val);
	}

	@Override
	public void updateAll(Page page, String key, Object value, DBObject criteria) {
		repo.updateAll(page.getNumber(), page.getSize(), key, value, criteria);
	}

	@Override
	public Stats stats() {
		return repo.stats();
	}

	@Override
	public void insertAll(Collection<String> data) {
		repo.insertAll(data);
	}

}
