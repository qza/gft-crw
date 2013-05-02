package org.qza.gft.crw.server.ntt;

import java.net.InetAddress;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;
import org.qza.gft.crw.server.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

public class ServerHandler extends ChannelInboundMessageHandlerAdapter<String> {

	static final ChannelGroup channels = new DefaultChannelGroup();

	final private Context context;

	final private MessageConverter converter;

	final private Logger log;

	public ServerHandler(final Context context) {
		this.context = context;
		this.converter = new MessageConverter();
		this.log = LoggerFactory.getLogger(ServerHandler.class);
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		int index = channels.size() + 1;
		log.info("Queue size " + context.getQueue().size());
		ctx.write("Hi Node! " + index + " Welcome to "
				+ InetAddress.getLocalHost().getHostName() + "!\n");
		ctx.write(context.getQueue().take());
		channels.add(ctx.channel());
		log.info("Client " + index + " connected");
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, String request)
			throws Exception {
		processData(request);
		String next = context.getQueue().take();
		ctx.channel().write(next);
		if ("exit".equals(request.toLowerCase())) {
			ctx.close();
		}
		log.info("Visited: " + context.getVisited().size());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		log.warn("Unexpected exception from downstream.", cause);
		ctx.close();
	}

	private void processData(String data) {
		try {
			Message message = converter.read(data.getBytes());
			if (message != null) {
				context.addMessage(message);
			}
		} catch (Exception ex) {
			log.error(data);
		}
	}

}
