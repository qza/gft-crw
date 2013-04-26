package org.qza.gft.crw.store.web.model;

import java.util.List;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.service.Page;

/**
 * @author gft
 */
public class Builder {

	public static Response makeResponse(List<Product> products, Page page) {
		Response productReponse = new Response();
		productReponse.setProducts(products);
		productReponse.setPage(page.next());
		return productReponse;
	}

}
