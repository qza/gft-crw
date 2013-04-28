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
	final private String cssImage;

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
		this.cssImage = context.getProps().getParserCssImage();
	}

	@Override
	public Message crawlResults(String link) {
		try {
			Response res = Jsoup.connect(link).timeout(parserTimeout)
					.maxBodySize(parserMaxbytes).execute();
			Document doc = Jsoup.parse(res.body());
			String name = getText(doc, cssName);
			String category = getText(doc, cssCategory);
			String price = getText(doc, cssPrice);
			String rating = getText(doc, cssRating);
			String image = getAttr(doc, cssImage, "src");
			Elements elems = doc.select(cssRelated);
			Set<String> related = new HashSet<>(elems.size());
			Iterator<Element> links = elems.iterator();
			while (links.hasNext()) {
				related.add(links.next().attr("href"));
			}
			Message m = new Message(name, category, price, rating, link, image);
			m.getRelated().addAll(related);
			return m;
		} catch (java.net.SocketException tex) {
			log.error(String.format("Socket exception for link %s", link));
		} catch (java.net.SocketTimeoutException tex) {
			log.error(String.format("Timeout for link %s", link));
		} catch (org.jsoup.HttpStatusException notFound) {
			log.error(String.format("Link %s not found", link));
		} catch(java.io.EOFException eof) {
			log.error(String.format("Link %s bad format", link));
		} catch (Exception e) {
			log.error(String.format("Problem with link %s", link), e);
			throw new RuntimeException(e);
		}
		return null;
	}

	private String getAttr(Document doc, String selector, String attr) {
		Elements elems = getElements(doc, selector);
		if (checkElems(elems)) {
			return elems.iterator().next().attr(attr).trim();
		}
		return null;
	}

	private String getText(Document doc, String selector) {
		Elements elems = getElements(doc, selector);
		if (checkElems(elems)) {
			return elems.iterator().next().text().trim();
		}
		return null;
	}

	private Elements getElements(Document doc, String selector) {
		String[] selectors = selector.split(",");
		for (int i = 0; i < selectors.length; i++) {
			Elements elems = doc.select(selectors[i].trim());
			if (checkElems(elems)) {
				return elems;
			}
		}
		return null;
	}

	private boolean checkElems(Elements elems) {
		return (elems != null && elems.size() > 0);
	}

}
