package org.qza.gft.crw.client.ntt;

import io.netty.buffer.BufType;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import org.qza.gft.crw.client.Context;
import org.qza.gft.crw.client.crawler.impl.JsoupCrawler;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

	final private Context context;

	public ClientInitializer(final Context context) {
		this.context = context;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder(BufType.BYTE));
		pipeline.addLast("handler", new ClientHandler(new JsoupCrawler(context)));
	}
}