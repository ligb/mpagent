package com.cgs.mpagent.config;

import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cgs.mpagent.proto.Agent;
import com.cgs.mpagent.proto.AgentImpl;

@Configuration
public class NettyConfig {
	
	@Bean
	Server server(){
		
		return new NettyServer(new SpecificResponder(Agent.class, new AgentImpl()), new InetSocketAddress(12003));
	}
	
}
