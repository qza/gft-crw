package org.qza.gft.crw.client.ntt;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.qza.gft.crw.client.Context;

public class Client {

	final private Context context;

	public Client(final Context context) {
		this.context = context;
	}

	public void start() throws Exception {
		final EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
				.handler(new ClientInitializer(context));
		// Start the connection attempt.
		final Channel ch = b.connect("localhost", 9090).sync().channel();
		// Read commands from the stdin.
		Thread cl = 
		new Thread(new Runnable() {
			@Override
			public void run() {
				ChannelFuture lastWriteFuture = null;
				BufferedReader in = new BufferedReader(new InputStreamReader(
						System.in));
				for (;;) {
					try {
						String line = in.readLine();
						if (line == null) {
							break;
						}
						lastWriteFuture = ch.write(line + "\r\n");
						if ("exit".equals(line.toLowerCase())) {
							ch.closeFuture().sync();
							break;
						}
						// Wait until all messages are flushed before
						// closing the channel.
						if (lastWriteFuture != null) {
							lastWriteFuture.sync();
						}
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					} finally {
						group.shutdown();
					}
				}
			}
		});
		cl.start();
		cl.join();
	}
}
