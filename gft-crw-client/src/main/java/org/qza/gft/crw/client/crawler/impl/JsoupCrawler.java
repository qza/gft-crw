package org.qza.gft.crw.client.crawler.impl;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.qza.gft.crw.client.Context;
import org.qza.gft.crw.client.crawler.Crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class JsoupCrawler implements Crawler {

	final private Logger log;

	final private String parserCss;

	final private Integer parserTimeout;

	final private Integer parserMaxbytes;

	public JsoupCrawler(final Context context) {
		this.log = LoggerFactory.getLogger(JsoupCrawler.class);
		this.parserCss = context.getProps().getParserCss();
		this.parserTimeout = context.getProps().getParserTimeout();
		this.parserMaxbytes = context.getProps().getParserMaxbytes();
	}

	@Override
	public String[] crawlResults(String link) {
		String[] results = null;
		try {
			Response res = Jsoup.connect(link).timeout(parserTimeout)
					.maxBodySize(parserMaxbytes).execute();
			Elements elems = Jsoup.parse(res.body()).select(parserCss);
			results = new String[elems.size()];
			Iterator<Element> links = elems.iterator();
			for (int i = 0; i < results.length; i++) {
				results[i] = links.next().attr("href");
			}
		} catch (IOException e) {
			log.error(String.format("Problem with link %s", link));
		}
		return results;
	}

}
