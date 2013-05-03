package org.qza.gft.crw.store.data.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.qza.gft.crw.store.data.repo.model.Stats;

public class ProductRepositoryImpl implements ProductRepositoryMain {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Stats stats() {
		Stats stats = new Stats();
		stats.setRecordCount(getCount());
		stats.setVisitedCount(getVisitedCount());
		stats.setForGiftCount(getForgiftCount());
		return stats;
	}

	private long getCount() {
		String query = "select count(*) from products ";
		return getFirstLong(query);
	}

	private long getVisitedCount() {
		String query = "select count(*) from products where visited = true";
		return getFirstLong(query);
	}

	private long getForgiftCount() {
		String query = "select count(*) from products where for_gift = true";
		return getFirstLong(query);
	}

	private long getFirstLong(String query) {
		Query q = em.createNativeQuery(query);
		Object result = q.getResultList().iterator().next();
		return Long.valueOf(String.valueOf(result));
	}

}
