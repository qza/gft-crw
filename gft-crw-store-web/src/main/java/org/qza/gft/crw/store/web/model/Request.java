package org.qza.gft.crw.store.web.model;

import org.qza.gft.crw.ValidUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author gft
 * 
 *         Store service request
 */
public class Request {

	// TODO
	static final int PAGE = 20;

	private String name;
	private String category;
	private boolean forGift;
	private boolean visited;
	private int pageNumber;

	private Pageable page;

	private String[] selected;

	public Request() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(String visited) {
		if (ValidUtils.notBlank(visited)) {
			this.visited = Boolean.valueOf(visited);
		}
	}

	public boolean isForGift() {
		return forGift;
	}

	public void setForGift(String forGift) {
		if (ValidUtils.notBlank(forGift)) {
			this.forGift = Boolean.valueOf(forGift);
		}
	}

	public int getPageNumber() {
		return pageNumber;
	}
	
	public Pageable nextPage(){
		return new PageRequest(getPageNumber()+1, PAGE);
	}

	public void setPageNumber(String pageNumber) {
		if (ValidUtils.notBlank(pageNumber)) {
			this.pageNumber = Integer.valueOf(pageNumber);
			this.page = new PageRequest(this.pageNumber, PAGE);
		}
	}

	public Pageable getPage() {
		if (pageNumber == 0) {
			pageNumber = 1;
			return new PageRequest(pageNumber, PAGE);
		} else {
			return this.page;
		}
	}

	public String[] getSelected() {
		return selected;
	}

	public void setSelected(String[] selected) {
		this.selected = selected;
	}

	public void log() {
		StringBuilder builder = new StringBuilder();
		builder.append(getPageNumber()).append(" ").append(getCategory())
				.append(" ").append(isVisited());
		System.out.println(builder.toString());
	}

}
