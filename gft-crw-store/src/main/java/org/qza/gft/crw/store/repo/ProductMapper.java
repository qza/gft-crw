package org.qza.gft.crw.store.repo;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.qza.gft.crw.store.entity.Product;

public class ProductMapper {

	private final ObjectMapper mapper;

	public ProductMapper() {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public String write(Product product) {
		try {
			return mapper.writeValueAsString(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Product read(String value) {
		try {
			return mapper.readValue(value, Product.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
