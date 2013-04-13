package org.qza.gft.crw.server.report;

public class Template {

	final private StringBuilder text;

	public Template() {
		this.text = new StringBuilder();
	}

	public String get() {
		return text.toString();
	}

	public void newLine() {
		text.append("\r\n");
	}

	public void newLine(int count) {
		for (int i = 0; i < count; i++) {
			newLine();
		}
		text.append("\r\n");
	}

	public void line(String line) {
		text.append(line);
		newLine();
	}

	public void tabline(String line) {
		text.append("\t");
		line(line);
	}

	public void padNumber(String name) {
		number(format(name));
	}

	public void padText(String name) {
		text(format(name));
	}

	public void padBoolean(String name) {
		bool(format(name));
	}

	public void number(String name) {
		add(name, "d");
		newLine();
	}

	public void text(String name) {
		add(name, "s");
		newLine();
	}

	public void bool(String name) {
		add(name, "b");
		newLine();
	}

	private void add(String name, String type) {
		text.append(name).append(" %").append(type);
	}

	private String format(String name) {
		return String.format(" \t %20s : ", name); // TODO Magic number
	}

}
