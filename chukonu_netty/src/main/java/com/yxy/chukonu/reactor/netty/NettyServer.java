package com.yxy.chukonu.reactor.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	private final int port ;
	ServerBootstrap bs = new ServerBootstrap() ;
	
	public NettyServer(int port) {
		this.port = port ;
	}
	
	public void launch() {
		// acceptor
		EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1) ;
		// worker
		EventLoopGroup workerLoopGroup = new NioEventLoopGroup() ;
		
		bs.group(bossLoopGroup, workerLoopGroup)
		// set nio channel type
		.channel(NioServerSocketChannel.class)
		.localAddress(port)
		// set channel parameter
		.option(ChannelOption.SO_KEEPALIVE, true)
		.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
		// assemble child channel pipeline
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// all handlers are managed by pipeline
				ch.pipeline().addLast(new NettyServerHandler()) ;
			}
		}) ;
		
		// bind server
		try {
			ChannelFuture channelFuture = bs.bind().sync() ;
			System.out.println("Server is listening on: " + channelFuture.channel().localAddress());
			
			ChannelFuture closeFuture = channelFuture.channel().closeFuture() ;
			closeFuture.sync() ;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerLoopGroup.shutdownGracefully() ;
			bossLoopGroup.shutdownGracefully() ;
		}
		
		
	}

	
	
	public static void main(String[] args) {
		new NettyServer(8080).launch();
	}

}
