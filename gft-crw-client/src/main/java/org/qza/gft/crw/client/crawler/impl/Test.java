package org.qza.gft.crw.client.crawler.impl;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) throws Exception {
		String cssPrice = "#price_feature_div span, #actualPriceValue b, #olpDivId span.price, #price tr td.a-text-strike";
		String link = "http://www.amazon.com/";
		String[] products = {
				"Waterford-Crystal-6003182300-Lismore-9-Ounce/dp/B000SMQ7JS",
				"Waterford-Crystal-Lismore-Essence-Goblet/dp/B001HL0FHO",
				"Williams-Allegro-88-Key-Digital-Piano/dp/B0049SBVLG",
				"Wine-Bucket-Polished-Stainless-Steel/dp/B000B90MAI",
				"Quiksilver-Mens-Know-This-Smith/dp/B00AO7U2JS",
				"Tommy-Hilfiger-Genuine-Leather-Passcase/dp/B004BVAFN6",
				"iHome-iHM60LY-Portable-Speaker-Translucent/dp/B004OA714U",
				"Mission-Belt-Black-Leather-Ratchet/dp/B009A117N4",
				"Outlander-Packable-Lightweight-Backpack-Daypack-Green/dp/B0092ECRMO",
				"Toysmith-4574-4M-Brush-Robot/dp/B002EWYEHW",
				"3B-Scientific-A20-Plastic-Classic/dp/B005EI8S7K",
				"Lauren-Solid-Color-Stunning-Pashmina/dp/B001VOH7KU",
				"Melissa-Doug-2576-Pirate-Chest/dp/B000ARW60U",
				"Western-Cowgirl-Glass-Dorfman-Pacific/dp/B003DPP090",
				"London-Fog-Close-Umbrella-Signature/dp/B004FV5XN4",
				"Lightning-Powder-Magnetic-Fingerprint-16-Ounce/dp/B0050H34TC",
				"Fascinations-ANTW1-AntWorks/dp/B0006FSEOI",
				"Toysmith-3653-Tin-Can-Robot/dp/B0014WO96Y",
				"Vinci-Catapult-3B-Scientific-W64067/dp/B0047ZZJTQ",
				"JiMarti-Sunglasses-Fishing-Cycling-Unbreakable-TR90-Silver/dp/B002SI5QV0",
				"Victory-Medium-Duffel-Under-Armour/dp/B004D82KDA",
				"American-Educational-Piece-Glow--Finder/dp/B006581WKI",
				"Spectrum-AIL-602-Digital-Stand/dp/B003XJ6GC6",
				"Table-Bag-W2716-Portable-Carrying/dp/B001V7B2OY"
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
