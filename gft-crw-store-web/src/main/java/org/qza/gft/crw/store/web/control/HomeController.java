package org.qza.gft.crw.store.web.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gft
 * 
 *         Responds with home page
 * 
 */
@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public String home() {
		return "home";
	}

}
