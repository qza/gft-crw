package org.qza.gft.crw.store.web.control;

import javax.servlet.http.HttpServletRequest;

import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.model.Stats;
import org.qza.gft.crw.store.data.service.ProductService;
import org.qza.gft.crw.store.web.model.Builder;
import org.qza.gft.crw.store.web.model.Request;
import org.qza.gft.crw.store.web.model.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gft
 */
@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Response processGet(HttpServletRequest sRequest) {
		Request req = getRequest(sRequest);
		return getResponse(req);
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Response processPost(HttpServletRequest sRequest) {
		Request req = getRequest(sRequest);
		updateForGift(req);
		updateVisited(req);
		return getResponse(req);
	}

	private Request getRequest(HttpServletRequest req) {
		return Builder.makeRequest(req);
	}

	private void updateForGift(Request req) {
		service.update(req.getSelectedLong(), "for_gift", true);
	}

	private void updateVisited(Request req) {
		service.update(req.getIdsLong(), "visited", true);
	}

	private Response getResponse(Request req) {
		Page<Product> products = service.findByVisited(false, req.getPage());
		Stats stats = service.stats();
		return Builder.makeResponse(products, stats);
	}

}
