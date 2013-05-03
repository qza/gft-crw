package org.qza.gft.crw.store.web.model;

import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.model.Stats;
import org.springframework.data.domain.Page;

/**
 * @author gft
 * 
 *         Store service response object with paged product data
 */
public class Response {

	private Page<Product> data;

	private Stats stats;

	public Response() {
	}

	public Page<Product> getData() {
		return data;
	}

	public void setData(Page<Product> data) {
		this.data = data;
	}

	public int getPageNumber() {
		return getData().getNumber();
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

}
