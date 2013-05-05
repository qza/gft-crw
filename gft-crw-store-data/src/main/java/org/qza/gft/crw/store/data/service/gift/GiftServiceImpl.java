package org.qza.gft.crw.store.data.service.gift;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.qza.gft.crw.ValidUtils;
import org.qza.gft.crw.store.data.entity.Gift;
import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.gift.GiftRepository;
import org.qza.gft.crw.store.data.repo.product.ProductRepository;
import org.qza.gft.crw.store.data.service.convert.Product2Gift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gft
 */
@Service
@Transactional(readOnly = false)
public class GiftServiceImpl implements GiftService {

	final static Logger log = LoggerFactory.getLogger(GiftServiceImpl.class);

	@Autowired
	private GiftRepository repo;

	@Autowired
	private ProductRepository productRepo;

	@Override
	public void insertAll(Collection<Product> data) {
		if(ValidUtils.notBlank(data)){
			Collection<Gift> gifts = Product2Gift.convert(data);
			insertAll(gifts);
		}
	}

	@Override
	public void insertAll(Long[] productsIds) {
		if(ValidUtils.notBlank(productsIds)) {
			Iterable<Long> ids = Arrays.asList(productsIds);
			Iterable<Product> data = productRepo.findAll(ids);
			Collection<Gift> gifts = Product2Gift.convert(data);
			insertAll(gifts);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Gift> findAll(Pageable page) {
		return repo.findAll(page);
	}

	private void insertAll(Iterable<Gift> gifts) {
		for (Iterator<Gift> iterator = gifts.iterator(); iterator.hasNext();) {
			Gift gift = iterator.next();
			try {
				repo.save(gift);
			} catch (DataIntegrityViolationException ex) {
				log.warn(ex.getMessage());
			}
		}
	}

}
