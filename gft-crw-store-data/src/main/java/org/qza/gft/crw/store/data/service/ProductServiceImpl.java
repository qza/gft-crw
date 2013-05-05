package org.qza.gft.crw.store.data.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.ProductRepository;
import org.qza.gft.crw.store.data.repo.model.Stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
@Component
public class ProductServiceImpl implements ProductService {

	final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository repo;

	@Override
	@Transactional
	public Product persist(Product data) {
		return repo.save(data);
	}

	@Override
	public Product findByName(String name) {
		return repo.findByName(name);
	}

	@Override
	public Page<Product> findByCategory(String category, Pageable page) {
		return repo.findByCategory(category, page);
	}

	@Override
	public Page<Product> findByVisited(boolean visited, Pageable page) {
		return repo.findByVisited(visited, page);
	}

	@Override
	public void insertAll(Collection<Message> data) {
		Collection<Product> products = Message2Product.convert(data);
		for (Iterator<Product> iterator = products.iterator(); iterator
				.hasNext();) {
			Product product = iterator.next();
			try {
				persist(product);
			} catch (DataIntegrityViolationException ex) {
				log.warn(ex.getMessage());
			}
		}
	}

	@Override
	public Stats stats() {
		return repo.stats();
	}

	@Override
	public long total() {
		return repo.count();
	}

	@Override
	public Set<String> collected() {
		return repo.collected();
	}

	@Override
	public Set<String> visited() {
		return repo.visited();
	}

	@Override
	@Transactional
	public void update(Long[] ids, String column, boolean value) {
		if(ids != null && ids.length > 0) {
			repo.update(ids, column, value);
		}
	}

}
