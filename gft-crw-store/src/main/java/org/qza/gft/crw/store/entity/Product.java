package org.qza.gft.crw.store.entity;

import java.util.Set;

public class Product {

	private String id;
	private String name;
	private String category;
	private String price;
	private String rating;
	private String url;
	private String image;
	private Set<Product> related;
	private Set<Tag> tags;

	public Product() {
	}

	public String getId() {
		return id;
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

	public Set<Product> getRelated() {
		return related;
	}

	public void addRelated(Set<Product> related) {
		this.related.addAll(related);
	}

	public void setRelated(Set<Product> related) {
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

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public void addTag(Tag tag) {
		getTags().add(tag);
	}
}
