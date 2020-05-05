package com.yxy.chukonu.reactor.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter{

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg ;
        try {
	        while(in.isReadable()) {
	        	System.out.print(in.readableBytes());
	        }
        } finally {
        	ReferenceCountUtil.release(msg) ;
        }
        
        
    }

}
