package org.qza.gft.crw.store.data.repo;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.model.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

public class ProductRepositoryImpl implements ProductRepositoryMain {

	final static Logger log = LoggerFactory.getLogger(ProductRepositoryImpl.class);  
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Stats stats() {
		return null;
	}

	@Override
	public void saveAll(Collection<Product> products) {
		for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			try {
				em.persist(product);
				em.getTransaction().commit();
			} catch(DataIntegrityViolationException ex) {
				log.warn(ex.getMessage());
			}
		}
	}

}
