package org.qza.gft.crw.store.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * @author gft
 */
@Entity
@Table(name = "products")
public class Product extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 7014806576369506706L;

	@Column(unique = true)
	private String name;

	private String category;
	private double price;
	private double rating;
	private String url;
	private String image;
	private String tags;
	@Column(length = 2048)
	private String relatedUrls;
	private boolean for_gift;
	private boolean visited;

	public Product() {
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

	public double getRating() {
		return rating;
	}

	public String getUrl() {
		return url;
	}

	public String getRelatedUrls() {
		return relatedUrls;
	}

	public void setRelatedUrls(String relatedUrls) {
		this.relatedUrls = relatedUrls;
	}

	public String[] getRelatedUrlsArray() {
		return relatedUrls != null ? relatedUrls.split(",") : new String[] {};
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

	public void setPrice(double price) {
		this.price = price;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String[] getTagsArray() {
		return tags != null ? tags.split(",") : new String[] {};
	}

	public void setFor_gift(boolean for_gift) {
		this.for_gift = for_gift;
	}

	public boolean isFor_gift() {
		return for_gift;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	@Override
	public int hashCode() {
		if (this.url != null) {
			return this.url.hashCode();
		}
		return super.hashCode();
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 != null && arg0 instanceof Product) {
			Product p = (Product) arg0;
			if (p.url != null && this.url != null) {
				if (p.url.equals(this.url)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return super.equals(arg0);
	}

}
