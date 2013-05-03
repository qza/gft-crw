package org.qza.gft.crw.store.data.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.qza.gft.crw.store.data.repo.model.Stats;

public class ProductRepositoryImpl implements ProductRepositoryMain {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Stats stats() {
		return null;
	}

}
