package org.qza.gft.crw.store.web.control;

import java.util.List;

import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.service.ProductStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

	@Autowired
	private ProductStoreService service;

	@RequestMapping(value = "/products")
	public @ResponseBody
	List<Product> loadProductData() {
		List<Product> products = service.fetchAll();
		return products;
	}

}
