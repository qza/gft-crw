package org.qza.gft.crw.store.web.control;

import java.util.List;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.service.Page;
import org.qza.gft.crw.store.service.ProductStoreService;
import org.qza.gft.crw.store.web.model.Builder;
import org.qza.gft.crw.store.web.model.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gft
 */
@Controller
public class ProductController {

	@Autowired
	private ProductStoreService service;

	@RequestMapping(method = RequestMethod.GET, value = "/products")
	public @ResponseBody
	Response loadProductData(@RequestParam("page_number") int pageNumber) {
		return getResponse(pageNumber);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/products")
	public @ResponseBody
	Response saveProductData(
			@RequestParam(value = "page_number") int pageNumber,
			@RequestParam(required = false, value = "cb") String[] selected) {
		updateSelected(selected);
		return getResponse(pageNumber);
	}

	private void updateSelected(String[] selected) {
		if (selected != null && selected.length > 0) {
			service.updateAll(selected, "for_gift", Boolean.TRUE);
		}
	}

	private Response getResponse(int pageNumber) {
		Page page = new Page(pageNumber);
		List<Product> products = service.fetchAll(page);
		return Builder.makeResponse(products, page);
	}

}
