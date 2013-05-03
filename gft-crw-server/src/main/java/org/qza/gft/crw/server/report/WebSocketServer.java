package org.qza.gft.crw.server.report;

import java.util.concurrent.TimeUnit;

import org.qza.gft.crw.server.Context;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketServer implements Runnable {

	final private Context context;
	final private Reporter reporter;
	
	public WebSocketServer(final Context context, final Reporter reporter) {
		this.context = context;
		this.reporter = reporter;
	}
	
	@Override
	public void run() {
		try {
			runServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void runServer() throws Exception {
        final ServerBootstrap sb = new ServerBootstrap();
        try {
            sb.group(new NioEventLoopGroup(), new NioEventLoopGroup())
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(final SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                        new HttpRequestDecoder(),
                        new HttpObjectAggregator(65536),
                        new HttpResponseEncoder(),
                        new WebSocketServerProtocolHandler("/websocket"));
                }
            });
            final Channel ch = sb.bind(9999).sync().channel();
            context.getScheduler().schedule(new Runnable() {
				@Override
				public void run() {
					if(ch!=null && ch.isOpen()){
						ch.write(reporter.makeReport());
					}
				}
			}, context.getProps().getReportLogInterval(), TimeUnit.SECONDS);
            System.out.println("Web socket server started at port " + 9999);
            ch.closeFuture().sync();
        } finally {
            sb.shutdown();
        }
    }

}
