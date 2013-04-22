package org.qza.gft.crw.gzip;

import java.io.IOException;
import java.util.Collection;
import java.util.zip.GZIPOutputStream;

public class StringGziper implements Gziper<String> {

	@Override
	public void read(Collection<String> data, String message) {
		data.add(message);
	}

	@Override
	public void write(GZIPOutputStream out, String message) throws IOException {
		out.write(message.getBytes());
		out.write("\r\n".getBytes());
	}

}
