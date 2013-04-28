package org.qza.gft.crw.store.web.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.service.ProductStoreService;
import org.qza.gft.crw.store.service.model.Page;
import org.qza.gft.crw.store.service.model.Stats;
import org.qza.gft.crw.store.web.model.Builder;
import org.qza.gft.crw.store.web.model.Request;
import org.qza.gft.crw.store.web.model.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DBObject;

/**
 * @author gft
 */
@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductStoreService service;

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
		updateSelected(req.getSelected());
		updateVisited(req.getPage().previous(), req.getCriteria());
		return getResponse(req);
	}

	private Request getRequest(HttpServletRequest req) {
		return Builder.makeRequest(req);
	}

	private void updateSelected(String[] selected) {
		if (selected != null && selected.length > 0) {
			service.updateAll(selected, "for_gift", Boolean.TRUE);
		}
	}

	private void updateVisited(Page page, DBObject criteria) {
		service.updateAll(page, "visited", Boolean.TRUE, criteria);
	}

	private Response getResponse(Request req) {
		List<Product> products = service.fetchAll(req.getPage(),
				req.getCriteria());
		Page nextPage = req.getPage().next();
		Stats stats = service.stats();
		return Builder.makeResponse(products, nextPage, stats);
	}

}
