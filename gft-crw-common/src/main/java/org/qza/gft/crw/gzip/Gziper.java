package org.qza.gft.crw.gzip;

import java.io.IOException;
import java.util.Collection;
import java.util.zip.GZIPOutputStream;

public interface Gziper<C> {

	void read(Collection<C> data, String message);

	void write(GZIPOutputStream out, C object) throws IOException;

}
