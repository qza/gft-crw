package org.qza.gft.crw.gzip;

import java.io.IOException;
import java.util.Collection;
import java.util.zip.GZIPOutputStream;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;

public class MessageGziper implements Gziper<Message> {

	final private MessageConverter converter = new MessageConverter();

	@Override
	public void read(Collection<Message> data, String message) {
		data.add(converter.read(message.getBytes()));
	}

	@Override
	public void write(GZIPOutputStream out, Message message) throws IOException {
		out.write(converter.write(message));
		out.write("\r\n".getBytes());
	}
}
