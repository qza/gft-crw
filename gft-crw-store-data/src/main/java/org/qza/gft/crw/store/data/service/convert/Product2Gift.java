package org.qza.gft.crw.store.data.service.convert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.qza.gft.crw.store.data.entity.Gift;
import org.qza.gft.crw.store.data.entity.Product;

/**
 * @author gft
 */
public class Product2Gift {

	public static Collection<Gift> convert(Iterable<Product> products) {
		Set<Gift> gifts = new HashSet<>();
		for (Iterator<Product> iterator = products.iterator(); iterator
				.hasNext();) {
			Product product = iterator.next();
			gifts.add(convert(product));
		}
		return gifts;
	}

	public static Gift convert(Product product) {
		Gift gift = new Gift();
		gift.setName(product.getName());
		gift.setCategory(product.getCategory());
		gift.setUrl(product.getUrl());
		gift.setRating(product.getRating());
		gift.setPrice(product.getPrice());
		gift.setImage(product.getImage());
		return gift;
	}

}
