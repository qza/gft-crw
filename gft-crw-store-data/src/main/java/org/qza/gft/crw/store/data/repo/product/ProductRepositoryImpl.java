package org.qza.gft.crw.store.data.repo.product;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.qza.gft.crw.ArrayUtils;
import org.qza.gft.crw.store.data.repo.model.Stats;

public class ProductRepositoryImpl implements ProductRepositoryMain {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Stats stats() {
		Stats stats = new Stats();
		stats.setRecordCount(getCount());
		stats.setForGiftCount(getForgiftCount());
		return stats;
	}

	private long getCount() {
		String query = "select count(*) from products";
		return getFirstLong(query);
	}

	private long getForgiftCount() {
		String query = "select count(*) from gifts";
		return getFirstLong(query);
	}

	private long getFirstLong(String query) {
		Query q = em.createNativeQuery(query);
		Object result = q.getResultList().iterator().next();
		return Long.valueOf(String.valueOf(result));
	}

	@Override
	public Set<String> visited() {
		Set<String> result = new HashSet<>();
		String query = "select relatedUrls from products limit 500000";
		Query q = em.createNativeQuery(query);
		Iterator<?> results = q.getResultList().iterator();
		String[] related = null;
		while (results.hasNext()) {
			try {
				related = String.valueOf(results.next()).split(",");
				for (int i = 0; i < related.length; i++) {
					result.add(related[i]);
				}
			}
			finally {
				related = null;
				System.gc();
			}
		}
		return result;
	}

	@Override
	public Set<String> collected() {
		Set<String> result = new HashSet<>();
		String query = "select url from products";
		Query q = em.createNativeQuery(query);
		Iterator<?> results = q.getResultList().iterator();
		while (results.hasNext()) {
			result.add(String.valueOf(results.next()));
		}
		return result;
	}

	@Override
	public void update(Long[] ids, String column, boolean value) {
		String query = "update products set " + column + "=" + value
				+ " where id in ";
		query += ArrayUtils.sqlInArray(ids);
		System.out.println("QUERY = " + query);
		Query q = em.createNativeQuery(query);
		q.executeUpdate();
	}

}
