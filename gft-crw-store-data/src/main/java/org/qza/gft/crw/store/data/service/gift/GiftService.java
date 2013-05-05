package org.qza.gft.crw.store.data.service.gift;

import java.util.Collection;

import org.qza.gft.crw.store.data.entity.Gift;
import org.qza.gft.crw.store.data.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author gft
 */
public interface GiftService {

	void insertAll(Long[] productsIds);
	
	void insertAll(Collection<Product> data);

	Page<Gift> findAll(Pageable page);

}
