package org.qza.gft.crw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author gft
 */
public class Message {

	private String code;
	private String name;
	private String category;
	private String price;
	private String rating;
	private String url;
	private String image;
	private List<String> related;

	public Message() {
	}

	public Message(String name, String category, String price, String rating,
			String url, String image) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.rating = rating;
		this.url = url;
		this.image = extractImage(image);
		this.related = new ArrayList<String>();
		this.code = extractCode(url);
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

	/*
	 *  don't use directly, just to please object mapper
	 */
	public List<String> getRelated() {
		return related;
	}

	public void addRelated(Set<String> related) {
		for (Iterator<String> iterator = related.iterator(); iterator.hasNext();) {
			this.addRelated(iterator.next());
		}
	}

	public void addRelated(String related) {
		this.related.add(extractUrl(related));
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setUrl(String url) {
		this.url = extractUrl(url);
		this.code = extractCode(url);
	}

	public String getImage() {
		return image;
	}

	public String getCode() {
		return code;
	}

	@Override
	public int hashCode() {
		return this.code.hashCode();
	}

	@Override
	public boolean equals(Object arg) {
		if (arg != null && arg instanceof Message) {
			if (((Message) arg).code.equals(this.code)) {
				return true;
			} else {
				return false;
			}
		}
		return super.equals(arg);
	}

	@Override
	public String toString() {
		return String.format(
				"code: %s name: %s category: %s image: %s url: %s", code, name,
				category, image, url);
	}

	public static String extractCode(String urlParameter) {
		int l = urlParameter.lastIndexOf("/dp/");
		if(l >= 0) {
			// create new to release big
			return new String(urlParameter.substring(l+4));
		}
		return null;
	}
	
	public static String extractUrl(String urlParameter) {
		int l = urlParameter.indexOf("com/");
		if(l > 0) {
			// create new to release big
			return new String(urlParameter.substring(l+4));
		}
		return null;
	}
	
	public static String extractImage(String imageUrl) {
		int l = imageUrl.indexOf("/images/");
		if(l > 0) {
			// create new to release big
			return new String(imageUrl.substring(l+8));
		}
		return null;
	}

}
