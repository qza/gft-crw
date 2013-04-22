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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Message read(byte[] value) {
		try {
			return mapper.readValue(value, Message.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String url = "http://www.amazon.com/GOOD_OLD_WILLEY_BOY/B823211";
		String image = "http://ecx.images-amazon.com/images/I/51GjZekBmWL._SX385_.jpg";
		Message m = new Message("bla", "fun/happy", "4.52", "4", url, image);
		m.getRelated().add("http://www.amazon.com/BLABLA");
		m.getRelated().add("http://www.amazon.com/BLABLA22222");
		System.out.println(new String(new MessageConverter().write(m)));
	}

}
