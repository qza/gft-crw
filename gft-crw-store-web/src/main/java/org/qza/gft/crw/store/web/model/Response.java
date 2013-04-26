package org.qza.gft.crw.store.web.model;

import java.util.ArrayList;
import java.util.List;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.service.Page;

/**
 * @author gft
 * 
 *         Web response object with paged product data
 */
public class Response {

	private Page page;
	private List<Product> products;

	public Response() {
		products = new ArrayList<>();
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product product) {
		this.products.add(product);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getPageNumber() {
		return getPage().getNumber();
	}

}
