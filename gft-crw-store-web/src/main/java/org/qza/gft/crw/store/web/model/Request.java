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
	private int pageNumber = 0;

	private Pageable page;

	private String[] selected;

	private String[] ids;

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

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
		if (this.pageNumber < 0) {
			this.page = new PageRequest(this.pageNumber + 1, PAGE);
		} else {
			this.page = new PageRequest(this.pageNumber, PAGE);
		}

	}

	public Pageable getPage() {
		return this.page;
	}

	public Pageable getNextPage() {
		this.pageNumber = this.pageNumber + 1;
		return new PageRequest(this.pageNumber, PAGE);
	}

	public String[] getSelected() {
		if (selected == null) {
			selected = new String[0];
		}
		return selected;
	}

	public Long[] getSelectedLong() {
		Long[] result = new Long[getSelected().length];
		for (int i = 0; i < result.length; i++) {
			result[i] = Long.valueOf(getSelected()[i]);
		}
		return result;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getIds() {
		return ids;
	}

	public Long[] getIdsLong() {
		if (ValidUtils.notBlank(getIds())) {
			Long[] result = new Long[getIds().length];
			for (int i = 0; i < result.length; i++) {
				result[i] = Long.valueOf(getIds()[i]);
			}
			return result;
		}
		return null;
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
