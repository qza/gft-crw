package org.qza.gft.crw;

import java.util.HashSet;
import java.util.Set;

public class Message {

	private final String name;
	private final String category;
	private final double price;
	private final String rating;
	private final String url;
	private final Set<String> related;

	public Message(String name, String category, double price, String rating,
			String url) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.rating = rating;
		this.url = url;
		this.related = new HashSet<String>();
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public double getPrice() {
		return price;
	}

	public String getRating() {
		return rating;
	}

	public String getUrl() {
		return url;
	}

	public Set<String> getRelated() {
		return related;
	}

}
