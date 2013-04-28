package org.qza.gft.crw.store.web.model;

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
		if (category != null ) {
			criteria.put("category", category);
		}
	}

	public void setPageNumber(String pageNumber) {
		if (pageNumber != null && !pageNumber.equals("")) {
			this.pageNumber = Integer.valueOf(pageNumber);
		}
	}

	public void setSelected(String[] selected) {
		if (selected != null && selected.length > 0) {
			this.selected = selected;
		}
	}

	public boolean isVisited() {
		Object val = criteria.get("visited");
		if (val != null) {
			return Boolean.valueOf(String.valueOf(val));
		}
		return false;
	}

	public void setVisited(String visited) {
		if (visited != null && !visited.equals("")) {
			criteria.put("visited", Boolean.valueOf(visited));
		}
	}

	public boolean isForGift() {
		Object val = criteria.get("for_gift");
		if (val != null) {
			return Boolean.valueOf(String.valueOf(val));
		}
		return false;
	}

	public void setForGift(String forGift) {
		if (forGift != null && !forGift.equals("")) {
			criteria.put("for_gift", Boolean.valueOf(forGift));
		}
	}

	public Page getPage() {
		int num = pageNumber > 0 ? pageNumber : 1;
		return new Page(num);
	}

	public DBObject getCriteria() {
		return criteria;
	}

	public void log() {
		StringBuilder builder = new StringBuilder();
		builder.append(getPageNumber()).append(" ").append(getCategory())
				.append(" ").append(isVisited());
		System.out.println(builder.toString());
	}

}
