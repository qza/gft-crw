package org.qza.gft.crw.client.ntt;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

import org.qza.gft.crw.client.crawler.Crawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends ChannelInboundMessageHandlerAdapter<String> {

	private final Logger log = LoggerFactory.getLogger(ClientHandler.class);
	
	private final Crawler crawler;
	
	public ClientHandler(final Crawler crawler) {
		this.crawler = crawler;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, String msg)
			throws Exception {
		System.out.println(msg);
		if(msg.startsWith("http")) {
			log.info("link processed");
			ctx.write(crawler.crawlResults(msg));
		} else {
			System.err.println(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		log.warn("Unexpected exception from downstream.", cause);
		ctx.close();
	}
}