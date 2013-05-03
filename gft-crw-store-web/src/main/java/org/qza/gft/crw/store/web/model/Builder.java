package org.qza.gft.crw.store.web.model;

import javax.servlet.http.HttpServletRequest;

import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.model.Stats;
import org.springframework.data.domain.Page;

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

	public static Response makeResponse(Request request,
			Page<Product> products, Stats stats) {
		Response response = new Response();
		response.setData(products);
		response.setStats(stats);
		return response;
	}

}
