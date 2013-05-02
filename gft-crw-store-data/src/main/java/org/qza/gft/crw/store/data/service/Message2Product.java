package org.qza.gft.crw.store.data.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.store.data.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Message2Product {

	final static private Logger log = LoggerFactory
			.getLogger(Message2Product.class);

	public static Collection<Product> convert(Collection<Message> messages) {
		List<Product> results = new ArrayList<>();
		for (Iterator<Message> iterator = messages.iterator(); iterator
				.hasNext();) {
			Message message = iterator.next();
			results.add(convert(message));
		}
		return results;
	}

	public static Product convert(Message message) {
		Product product = new Product();
		String name = message.getName();
		String category = message.getCategory();
		String image = message.getImage();
		String price = message.getPrice();
		String rating = message.getRating();
		String url = message.getUrl();
		Set<String> related = message.getRelated();
		if (notBlank(new String[] { name, category, image, url })) {
			product.setName(name.trim());
			product.setCategory(category.trim());
			product.setImage(image.trim());
			product.setUrl(url);
			if (notBlank(price)) {
				try {
					product.setPrice(Double.valueOf(price.substring(1).trim()));
				} catch (Exception ex) {
					log.warn("Cannot convert price");
				}
			}
			if (notBlank(rating)) {
				try {
					product.setRating(Double.valueOf(price.substring(0,
							price.indexOf("out") - 1).trim()));
				} catch (Exception ex) {
					log.warn("Cannot convert rating");
				}
			}
			if (notBlank(related)) {
				String[] strArray = related.toArray(new String[related.size()]);
				String strString = Arrays.toString(strArray)
						.replaceAll("\\[", "").replaceAll("\\]", "");
				if (notBlank(strString)) {
					product.setRelatedUrls(strString);
				}
			}
		}
		return product;
	}

	public static Message convert(Product product) {
		return null;
	}

	private static boolean notBlank(Set<String> strings) {
		return strings != null && strings.size() > 0;
	}

	private static boolean notBlank(String[] strings) {
		boolean result = true;
		for (int i = 0; i < strings.length && result; i++) {
			result = notBlank(strings[i]);
		}
		return result;
	}

	private static boolean notBlank(String string) {
		return string != null && string.length() > 0;
	}

}
