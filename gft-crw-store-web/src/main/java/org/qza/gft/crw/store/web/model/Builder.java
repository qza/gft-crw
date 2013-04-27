package org.qza.gft.crw.store.web.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.service.model.Page;

/**
 * @author gft
 * 
 *         Helps building object for web controllers
 */
public class Builder {

	public static Request makeRequest(HttpServletRequest req) {
		Request request = new Request();
		String cat = req.getParameter("category");
		request.setCategory(cat);
		String visited = req.getParameter("visited");
		if (visited != null) {
			request.setVisited(Boolean.valueOf(visited));
		}
		String pageNum = req.getParameter("pageNumber");
		if (pageNum != null) {
			request.setPageNumber(Integer.valueOf(pageNum));
		}
		String[] sel = req.getParameterValues("cb");
		if (sel != null && sel.length > 0) {
			request.setSelected(sel);
		}
		return request;
	}

	public static Request makeRequest(String[] selected, int pageNumber) {
		Request request = new Request();
		request.setSelected(selected);
		request.setPageNumber(pageNumber);
		return request;
	}

	public static Request makeRequest(boolean visited, int pageNumber) {
		Request request = new Request();
		request.setVisited(visited);
		request.setPageNumber(pageNumber);
		return request;
	}

	public static Response makeResponse(List<Product> products, Page page) {
		Response productReponse = new Response();
		productReponse.setProducts(products);
		productReponse.setPage(page);
		return productReponse;
	}

}
