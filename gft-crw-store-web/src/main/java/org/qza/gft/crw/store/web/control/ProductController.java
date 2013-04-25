package org.qza.gft.crw.store.web.control;

import java.util.List;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.service.Page;
import org.qza.gft.crw.store.service.ProductStoreService;
import org.qza.gft.crw.store.web.model.ProductResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

	@Autowired
	private ProductStoreService service;

	@RequestMapping(method = RequestMethod.GET, value = "/products")
	public @ResponseBody
	ProductResponse loadProductData(@RequestParam("page_number") int pageNumber) {
		System.out.println("LOAD DATA CALLED");
		return getResponse(pageNumber);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/products_save")
	public @ResponseBody ProductResponse saveProductData(@RequestParam("page_number") int pageNumber,
			@RequestParam(required = false, value = "cb") int[] selected){
		System.out.println("SAVE DATA CALLED");
		if (selected != null && selected.length > 0) {
			service.updateAll(selected, "for_gift", Boolean.TRUE);
		}
		return getResponse(pageNumber);
	}
	
	private ProductResponse getResponse(int pageNumber){
		Page page = new Page(pageNumber);
		List<Product> products = service.fetchAll(page);
		ProductResponse productReponse = new ProductResponse();
		productReponse.setProducts(products);
		productReponse.setPage(page.next());
		return productReponse;
	}

}
