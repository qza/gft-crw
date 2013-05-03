package org.qza.gft.crw.store.data.repo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

	@Override
	public Set<String> collected() {
		Set<String> result = new HashSet<>();
		String query = "select relatedUrls from products";
		Query q = em.createNativeQuery(query);
		Iterator<?> results = q.getResultList().iterator();
		while(results.hasNext()) {
			String[] related = String.valueOf(results.next()).split(",");
			for (int i = 0; i < related.length; i++) {
				result.add(related[i]);
			}
		}
		return result;
	}

	@Override
	public Set<String> visited() {
		Set<String> result = new HashSet<>();
		String query = "select url from products";
		Query q = em.createNativeQuery(query);
		Iterator<?> results = q.getResultList().iterator();
		while(results.hasNext()) {
			result.add(String.valueOf(results.next()));
		}
		return result;
	}

}
