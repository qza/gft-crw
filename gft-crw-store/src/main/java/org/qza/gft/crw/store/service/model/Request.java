package org.qza.gft.crw.store.service.model;

import org.qza.gft.crw.store.service.model.Page;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author gft
 * 
 *         Store service request
 */
public class Request {

	private DBObject criteria;

	private int pageNumber;

	private String[] selected;

	public Request() {
		criteria = new BasicDBObject();
	}

	public String getCategory() {
		Object val = criteria.get("category");
		if (val != null) {
			return String.valueOf(val);
		}
		return null;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public String[] getSelected() {
		return selected;
	}

	public void setCategory(String category) {
		criteria.put("category", category);
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setSelected(String[] selected) {
		this.selected = selected;
	}

	public boolean isVisited() {
		Object val = criteria.get("visited");
		if (val != null) {
			return Boolean.valueOf(String.valueOf(val));
		}
		return false;
	}

	public void setVisited(boolean visited) {
		criteria.put("visited", visited);
	}

	public Page getPage() {
		int num = pageNumber > 0 ? pageNumber : 1;
		return new Page(num);
	}

	public DBObject getCriteria() {
		return criteria;
	}

}
