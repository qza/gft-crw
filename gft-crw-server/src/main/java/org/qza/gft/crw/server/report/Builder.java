package org.qza.gft.crw.server.report;

import org.qza.gft.crw.server.report.Template;

/**
 * @author gft
 */
public class Builder {

	final private Template t;

	public Builder(final Template template) {
		this.t = template;
	}

	public String build() {
		t.newLine().line("Crawling report");
		t.line("******************************************************");
		t.newLine();
		t.line("Parameters").newLine();
		t.padNumber("Max Queue size");
		t.padNumber("Pool initialSize");
		t.padNumber("Pool maxSize").newLine();
		t.line("Results").newLine();
		t.padText("Duration");
		t.padText("Completed tasks");
		t.padNumber("Remained tasks");
		t.padNumber("Remained in queue");
		t.padNumber("Visited");
		t.padNumber("Buffered");
		t.padText("Visited / Second").newLine();
		t.padNumber("Free memory").newLine();
		t.line("******************************************************");
		t.newLine();
		return t.get();
	}

}
