package org.qza.gft.crw.server.report;

public class Template {

	final private StringBuilder text;

	public Template() {
		this.text = new StringBuilder();
	}

	public String get() {
		return text.toString();
	}

	public Template newLine() {
		text.append("\r\n");
		return this;
	}

	public Template newLine(int count) {
		for (int i = 0; i < count; i++) {
			newLine();
		}
		text.append("\r\n");
		return this;
	}

	public Template line(String line) {
		text.append(line);
		return newLine();
	}

	public Template tabline(String line) {
		text.append("\t");
		return line(line);
	}

	public Template padNumber(String name) {
		return number(format(name));
	}

	public Template padText(String name) {
		return text(format(name));
	}

	public Template padBoolean(String name) {
		return bool(format(name));
	}

	public Template number(String name) {
		add(name, "d");
		return newLine();
	}

	public Template text(String name) {
		add(name, "s");
		return newLine();
	}

	public Template bool(String name) {
		add(name, "b");
		return newLine();
	}

	private void add(String name, String type) {
		text.append(name).append(" %").append(type);
	}

	private String format(String name) {
		return String.format(" \t %20s : ", name); // TODO Magic number
	}

}
