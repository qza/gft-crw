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
import org.qza.gft.crw.ValidUtils;
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
	
	final private String domain;

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
		this.domain = context.getProps().getCrawlerRootDomain();
	}

	@Override
	public Message crawlResults(String link) {
		try {
			Response res = Jsoup.connect(domain+link).timeout(parserTimeout)
					.maxBodySize(parserMaxbytes).execute();
			Document doc = Jsoup.parse(res.body());
			String name = getText(doc, cssName);
			if (!ValidUtils.notBlank(name)) {
				log.warn("NO NAME" + link);
			}
			String category = getText(doc, cssCategory);
			if (!ValidUtils.notBlank(category)) {
				category = "UNKNOWN";
			}
			String price = getText(doc, cssPrice);
			if (!ValidUtils.notBlank(price)) {
				log.warn("NO PRICE" + link);
			}
			String rating = getText(doc, cssRating);
			if (!ValidUtils.notBlank(rating)) {
				// IGNORE
				// log.warn("NO RATING"+link);
			}
			String image = getAttr(doc, cssImage, "src");
			if (!ValidUtils.notBlank(image)) {
				log.warn("NO IMAGE" + link);
			}
			Elements elems = getElements(doc, cssRelated);
			Set<String> related = new HashSet<>(elems.size());
			Iterator<Element> links = elems.iterator();
			while (links.hasNext()) {
				related.add(links.next().attr("href"));
			}
			Message m = new Message(name, category, price, rating, link, image);
			m.addRelated(related);
			// checkMemory();
			return m;
		} catch (java.net.SocketException tex) {
			log.error(String.format("Socket exception for link %s", link));
		} catch (java.net.SocketTimeoutException tex) {
			log.error(String.format("Timeout for link %s", link));
		} catch (org.jsoup.HttpStatusException notFound) {
			log.error(String.format("Link %s not found", link));
			notFound.printStackTrace();
		} catch (java.io.EOFException eof) {
			log.error(String.format("Link %s bad format", link));
		} catch (java.io.IOException ioe) {
			log.error(String.format("IO issue with link %s", link));
		} catch (java.lang.IllegalArgumentException ex) {
			log.error(String.format("Illegal link %s", link));
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
		Elements result = new Elements();
		String[] selectors = selector.split(",");
		for (int i = 0; i < selectors.length; i++) {
			String s = selectors[i].trim();
			Elements elems = doc.select(s);
			if (checkElems(elems)) {
				result.addAll(elems);
				break;
			}
		}
		return result;
	}

	private boolean checkElems(Elements elems) {
		return (elems != null && elems.size() > 0);
	}

}
