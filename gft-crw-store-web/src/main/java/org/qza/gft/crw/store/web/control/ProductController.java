package org.qza.gft.crw.store.web.control;

import org.qza.gft.crw.store.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
	
	@RequestMapping(value="/products")
	public ModelAndView goToHelloPage() {
		ModelAndView view = new ModelAndView();
		view.setViewName("products"); 
		view.addObject("product", new Product());
		return view;
	}

}
