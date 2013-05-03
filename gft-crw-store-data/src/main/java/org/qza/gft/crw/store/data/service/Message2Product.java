package org.qza.gft.crw.store.data.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		Set<Product> results = new HashSet<>();
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
					product.setPrice(getFirstPrice(price));
				} catch (Exception ex) {
					log.warn("Cannot convert price: " + price);
				}
			}
			if (notBlank(rating)) {
				try {
					product.setRating(getRating(rating));
				} catch (Exception ex) {
					log.warn("Cannot convert rating " + rating);
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

	private static double getFirstPrice(String price) {
		price = price.replaceAll(",", "");
		Pattern pattern = Pattern.compile("\\d+\\.*\\d+");
		Matcher matcher = pattern.matcher(price);
		if (matcher.find()) {
			return Double.valueOf(matcher.group());
		} else {
			return 0;
		}
	}

	private static double getRating(String rating) {
		Pattern pattern = Pattern.compile("\\d\\.\\d");
		Matcher matcher = pattern.matcher(rating);
		if (matcher.find()) {
			return Double.valueOf(matcher.group());
		} else {
			return 0;
		}
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
