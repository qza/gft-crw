package org.qza.gft.crw.store.web.model;

import javax.servlet.http.HttpServletRequest;

import org.qza.gft.crw.ValidUtils;
import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.model.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

/**
 * @author gft
 * 
 *         Helps building object for web controllers
 */
public class Builder {

	static final Logger log = LoggerFactory.getLogger(Builder.class);

	public static Request makeRequest(HttpServletRequest req) {
		Request request = new Request();
		request.setCategory(req.getParameter("category"));
		request.setVisited(req.getParameter("visited"));
		request.setForGift(req.getParameter("for_gift"));
		String pageNumber = req.getParameter("pageNumber");
		if (ValidUtils.notBlank(pageNumber)) {
			request.setPageNumber(Integer.valueOf(pageNumber));
		} else {
			log.warn("No page number");
		}
		request.setIds(req.getParameterValues("ids"));
		request.setSelected(req.getParameterValues("cb"));
		return request;
	}

	public static Response makeResponse(Request req, Page<Product> products, Stats stats) {
		Response response = new Response();
		response.setReq(req);
		response.setData(products);
		response.setStats(stats);
		return response;
	}

}
