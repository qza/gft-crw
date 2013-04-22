package org.qza.gft.crw.server.report;

import org.qza.gft.crw.server.report.Template;

/**
 * @author gft
 */
public class Builder {

	final private Template template;

	public Builder(final Template template) {
		this.template = template;
	}

	public String build() {
		template.newLine();
		template.line("Crawling report");
		template.line("******************************************************");
		template.newLine();
		template.line("Parameters");
		template.newLine();
		template.padNumber("Max Queue size");
		template.padNumber("Pool initialSize");
		template.padNumber("Pool maxSize");
		template.newLine();
		template.line("Results");
		template.newLine();
		template.padText("Duration");
		template.padText("Completed tasks");
		template.padNumber("Remained tasks");
		template.padNumber("Remained in queue");
		template.padNumber("Visited");
		template.padText("Visited / Second");
		template.newLine();
		template.padNumber("Free memory");
		template.newLine();
		template.line("******************************************************");
		template.newLine();
		return template.get();
	}

}
