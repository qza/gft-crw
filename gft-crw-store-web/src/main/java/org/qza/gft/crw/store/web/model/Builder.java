package org.qza.gft.crw.store.web.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.service.model.Page;
import org.qza.gft.crw.store.service.model.Stats;

/**
 * @author gft
 * 
 *         Helps building object for web controllers
 */
public class Builder {

	public static Request makeRequest(HttpServletRequest req) {
		Request request = new Request();
		request.setCategory(req.getParameter("category"));
		request.setVisited(req.getParameter("visited"));
		request.setForGift(req.getParameter("for_gift"));
		request.setPageNumber(req.getParameter("pageNumber"));
		request.setSelected(req.getParameterValues("cb"));
		return request;
	}

	public static Response makeResponse(List<Product> products, Page page,
			Stats stats) {
		Response response = new Response();
		response.setProducts(products);
		response.setPage(page);
		response.setStats(stats);
		return response;
	}

}
