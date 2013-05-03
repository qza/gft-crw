package org.qza.gft.crw.store.data.repo;

import java.util.Collection;

import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.model.Stats;

public interface ProductRepositoryMain {
	
	Stats stats();
	
	void saveAll(Collection<Product> products);

}
