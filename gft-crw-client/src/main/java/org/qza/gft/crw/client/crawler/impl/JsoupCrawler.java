package org.qza.gft.crw.client.crawler.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.client.Context;
import org.qza.gft.crw.client.crawler.Crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class JsoupCrawler implements Crawler {

	final private Logger log;

	final private String cssName;
	final private String cssCategory;
	final private String cssPrice;
	final private String cssRating;
	final private String cssRelated;

	final private Integer parserTimeout;
	final private Integer parserMaxbytes;

	public JsoupCrawler(final Context context) {
		this.log = LoggerFactory.getLogger(JsoupCrawler.class);
		this.cssName = context.getProps().getParserCssName();
		this.cssCategory = context.getProps().getParserCssCategory();
		this.cssPrice = context.getProps().getParserCssPrice();
		this.cssRating = context.getProps().getParserCssRating();
		this.cssRelated = context.getProps().getParserCssRelated();
		this.parserTimeout = context.getProps().getParserTimeout();
		this.parserMaxbytes = context.getProps().getParserMaxbytes();
	}

	@Override
	public Message crawlResults(String link) {
		try {
			Response res = Jsoup.connect(link).timeout(parserTimeout)
					.maxBodySize(parserMaxbytes).execute();
			Document doc = Jsoup.parse(res.body());
			String name = getText(doc, cssName);
			String category = getText(doc, cssCategory);
			double price = getDoubleFromText(doc, cssPrice);
			String rating = getText(doc, cssRating);
			Elements elems = doc.select(cssRelated);
			Set<String> related = new HashSet<>(elems.size());
			Iterator<Element> links = elems.iterator();
			while (links.hasNext()) {
				related.add(links.next().attr("href"));
			}
			Message m = new Message(name, category, price, rating, link);
			m.getRelated().addAll(related);
			return m;
		} catch (Exception e) {
			log.error(String.format("Problem with link %s", link));
			e.printStackTrace();
		}
		return null;
	}

	private String getText(Document doc, String selector) {
		String[] selectors = selector.split(",");
		for (int i = 0; i < selectors.length; i++) {
			Elements elems = doc.select(selectors[i].trim());
			if (elems != null && elems.size() > 0) {
				return elems.iterator().next().text().trim();
			}
		}
		return null;
	}

	private double getDoubleFromText(Document doc, String selector) {
		String val = getText(doc, selector);
		if (val != null) {
			return Double.valueOf(val).doubleValue();
		}
		return 0;
	}

}
