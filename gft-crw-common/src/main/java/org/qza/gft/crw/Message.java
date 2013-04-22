package org.qza.gft.crw;

import java.util.HashSet;
import java.util.Set;

public class Message {

	private String name;
	private String category;
	private String price;
	private String rating;
	private String url;
	private String image;
	private Set<String> related;

	public Message() {
	}

	public Message(String name, String category, String price, String rating,
			String url, String image) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.rating = rating;
		this.url = url;
		this.image = image;
		this.related = new HashSet<String>();
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getPrice() {
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

	public void addRelated(Set<String> related) {
		this.related.addAll(related);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public void setRelated(Set<String> related) {
		this.related = related;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
