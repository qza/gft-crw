package org.qza.gft.crw.store.service;

public class Page {

	private int number;
	private int size;

	public Page() {
		this.number = 0;
		this.size = 20;
	}
	
	public Page(int number) {
		this.number = number;
		this.size = 20;
	}

	public Page(int number, int size) {
		this.number = number;
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public int getSize() {
		return size;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int offset() {
		return (number - 1) * size;
	}

	public Page next() {
		return new Page(number + 1, size);
	}

}
