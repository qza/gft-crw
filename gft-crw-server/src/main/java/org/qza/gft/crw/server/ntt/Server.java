package org.qza.gft.crw.server.ntt;

import java.util.Date;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.qza.gft.crw.server.Context;
import org.qza.gft.crw.server.Initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

	final private Logger log;
	final private Context context;
	final private Initializer initializer;
	
	public Server(final Context context, final Initializer initializer) {
		this.context = context;
		this.log = LoggerFactory.getLogger(Server.class);
		this.initializer = initializer;
	}
	
    public void start() throws InterruptedException {
    	initializer.initServerState();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ServerInitializer(context));
            b.bind(9090).sync().channel().closeFuture().sync();
            log.info("Server started " + new Date());
        }
        finally {
        	log.info("Server is shutting down " + new Date());
            bossGroup.shutdown();
            workerGroup.shutdown();
        }
    }

}
