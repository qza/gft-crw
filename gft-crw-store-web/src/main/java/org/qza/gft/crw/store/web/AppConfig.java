package org.qza.gft.crw.store.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {
	
	@Autowired
	private Environment env;

}
