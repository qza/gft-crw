package org.qza.gft.crw.store.web.control;

import javax.servlet.http.HttpServletRequest;

import org.qza.gft.crw.ValidUtils;
import org.qza.gft.crw.store.data.entity.Product;
import org.qza.gft.crw.store.data.repo.model.Stats;
import org.qza.gft.crw.store.data.service.gift.GiftService;
import org.qza.gft.crw.store.data.service.product.ProductService;
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
	private ProductService productService;

	@Autowired
	private GiftService giftService;

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
		storeGifts(req);
		return getResponse(req);
	}

	private Request getRequest(HttpServletRequest req) {
		return Builder.makeRequest(req);
	}

	private void storeGifts(Request req) {
		giftService.insertAll(req.getSelectedLong());
	}

	private Response getResponse(Request req) {
		Page<Product> products = null;
		if (ValidUtils.notBlank(req.getCategory())) {
			products = productService.findByCategory(req.getCategory(),
					req.getNextPage());
		} else {
			products = productService.findAll(req.getNextPage());
		}
		Stats stats = productService.stats();
		return Builder.makeResponse(req, products, stats);
	}

}
