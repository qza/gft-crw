package org.qza.gft.crw.client.crawler;

import org.qza.gft.crw.Message;

/**
 * @author gft
 */
public interface Crawler {

	/**
	 * Crawl for related links
	 * 
	 * @param link
	 *            URL to crawl
	 * @return Crawler message
	 */
	Message crawlResults(String link);

}
