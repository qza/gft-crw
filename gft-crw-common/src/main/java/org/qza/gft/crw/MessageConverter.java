package org.qza.gft.crw;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class MessageConverter {

	private final ObjectMapper mapper;
	

	public MessageConverter() {
		mapper = new ObjectMapper();
	}

	public byte[] write(Message message) {
		try {
			return mapper.writeValueAsString(message).getBytes();
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Message read(byte[] value) {
		try {
			return mapper.readValue(value, Message.class);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String url = "http://www.amazon.com/GSG-5-Single-Point-Tactical-Sling/dp/B002HK704U";
		String image = "http://ecx.images-amazon.com/images/I/21Xb8GFGWGL.jpg";
		String name = "GSG-5 Single Point Tactical Sling";
		String category = "Sports & Outdoors";
		String price = "4.99";
		String rating = "4.3";
		Message m = new Message(name, category, price, rating, url, image);
		m.addRelated("http://www.amazon.com/Bluecell-Single-Tactical-Bungee-Shoulder/dp/B00835AX24");
		m.addRelated("http://www.amazon.com/MetalTac-Point-Tactical-Bungee-Sling/dp/B002T6XLZ4");
		m.addRelated("http://www.amazon.com/Coitac-Single-Point-Rifle-Bungee/dp/B003VL1CY8");
		m.addRelated("http://www.amazon.com/1-Point-Tactical-Bungee-Sling/dp/B005ODCI1C");
		m.addRelated("http://www.amazon.com/Tactical-Foldable-Vertical-Adjustable-Positions/dp/B005OD7LNC");
		m.addRelated("http://www.amazon.com/Military-Enforcement-AR-15-Single-Adapter/dp/B004RJBFM2");
		m.addRelated("http://www.amazon.com/Military-Enforcement-AR-15-Ambidextrous-Adapter/dp/B004RJL2AW");
		m.addRelated("http://www.amazon.com/BLACKHAWK-70SA00BK-Single-Point-Sling-Adapter/dp/B001EBXR7C");
		m.addRelated("http://www.amazon.com/Tactical-Ergonomic-Foregrip-Pressure-Compartment/dp/B001DBJUTM");
		m.addRelated("http://www.amazon.com/Blackhawk-Storm-Single-Point-Disconnect/dp/B005KJMPXG");
		System.out.println(new String(new MessageConverter().write(m)));
	}

}
