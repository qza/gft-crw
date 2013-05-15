package org.qza.gft.crw.client.crawler.impl;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) throws Exception {
		String cssPrice = "#price_feature_div span, #actualPriceValue b, #price tr td.a-text-strike, span.price";
		String link = "http://www.amazon.com/";
		String[] products = {
				"Striker-Backpack-Bags-Under-Armour/dp/B004KBW7LU",
				"Waterford-Lismore-Hock-Wine-Glasses/dp/B0009XQTOE",
				"Rapid-Review-Pathology-Revised-Reprint/dp/0323084389"
		};
		for (int i = 0; i < products.length; i++) {
				Response res = Jsoup.connect(link+products[i]).execute();
				Document doc = Jsoup.parse(res.body());
				String price = getText2(doc, cssPrice);
				System.out.println("PRICE = " + price);	
		}
	}
	
	static private String getText2(Document doc, String selector) {
		Elements elems = getElements2(doc, selector);
		if (checkElems2(elems)) {
			return elems.iterator().next().text().trim();
		}
		return null;
	}

	static Elements getElements2(Document doc, String selector) {
		Elements result = new Elements();
		String[] selectors = selector.split(",");
		for (int i = 0; i < selectors.length; i++) {
			String s = selectors[i].trim();
			Elements elems = doc.select(s);
			if (checkElems2(elems)) {
				result.addAll(elems);
				break;
			}
		}
		return result;
	}

	static boolean checkElems2(Elements elems) {
		return (elems != null && elems.size() > 0);
	}

}
