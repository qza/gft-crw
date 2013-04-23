package org.qza.gft.crw.store.entity;

import java.util.Set;

public class Product {

	private Object _id;
	private String name;
	private String category;
	private String price;
	private String rating;
	private String url;
	private String image;
	private Set<String> related;
	private Set<String> tags;

	public Product() {
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

	public void setRelated(Set<String> related) {
		this.related = related;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public void addTag(String tag) {
		getTags().add(tag);
	}
	
	public Object get_id() {
		return _id;
	}
	
	public void set_id(Object _id) {
		this._id = _id;
	}
}
