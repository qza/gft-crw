package org.qza.gft.crw.server.spawn;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.qza.gft.crw.server.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class NioServerWorker implements Runnable {

	final private Logger log;
	final private Context context;
	final private List<NioChannel> channels;

	public NioServerWorker(final Context context) {
		this.channels = new ArrayList<>();
		this.context = context;
		this.log = LoggerFactory.getLogger(NioServerWorker.class);
	}

	@Override
	public void run() {
		log.info("Entering infinite reaction cycle");
		while(true) {
			synchronized(channels) {
				for (int i = 0; i < channels.size(); i++) {
					try {
						channels.get(i).react();
					}
					catch(Exception ex) {
						log.error(ex.getMessage());
					}
				}
			}
		}
	}

	public void addChannel(AsynchronousSocketChannel channel) {
		NioChannel ch = new NioChannel(context, channel);
		channels.add(ch);
	}

	public void shutdown() {
		Iterator<NioChannel> it = channels.iterator();
		while (it.hasNext()) {
			it.next().shutdown();
		}
		channels.clear();
	}

}
