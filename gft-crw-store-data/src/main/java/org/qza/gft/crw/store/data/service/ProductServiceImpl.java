package org.qza.gft.crw.store.data.service;

import java.util.Collection;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repo;

	@Override
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
	public void insertAll(Collection<Message> data) {
		Collection<Product> products = Message2Product.convert(data);
		repo.save(products);
	}

}
