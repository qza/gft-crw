package org.qza.gft.crw.client.crawler;

/**
 * @author gft
 */
public interface Crawler {

	/**
	 * Crawl for related links
	 * 
	 * @param link
	 *            URL to crawl
	 * @return Array of related URLs as strings
	 */
	String[] crawlResults(String link);

}
