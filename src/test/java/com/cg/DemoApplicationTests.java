package com.cg;

import java.io.File;
import java.io.IOException;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;













import com.cgs.mpagent.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DemoApplicationTests {

	@Test
	public void contextLoads() throws IOException, InterruptedException {
//		Shutdown s = new Shutdown();
//		Thread thread = new Thread(s);
//		Runtime.getRuntime().addShutdownHook(thread);
//		while(true){
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		Map<Integer, LocalVirtualMachine> map = 
//                LocalVirtualMachine.getAllVirtualMachines();
//		for(Integer i: map.keySet()){
//        	LocalVirtualMachine l = map.get(i);
//        	
//        	ProxyClient p = ProxyClient.getProxyClient(l);
//        	p.connect();
//        	MemoryMXBean m = p.getMemoryMXBean();
//        	ThreadMXBean t = p.getThreadMXBean();
//        	OperatingSystemMXBean o = p.getOperatingSystemMXBean();
//        	System.out.println(l.displayName() + " LoadAverage: " + o.getSystemLoadAverage() + " Arch: " + o.getArch()
//        			+ " AvailableProcessors:" + o.getAvailableProcessors() + " Name: " + o.getName() + " Version: " + o.getVersion());
//        	System.out.println(l.displayName() + " MemoryUsed: " + m.getHeapMemoryUsage().getUsed()/1024/1024);
//        	System.out.println(l.displayName() + " ThreadCount: " + t.getThreadCount() + " PeakThreadCount: " + t.getPeakThreadCount());
//        }
//		Thread.sleep(20000);
//		for(Integer i: map.keySet()){
//        	LocalVirtualMachine l = map.get(i);
//        	
//        	ProxyClient p = ProxyClient.getProxyClient(l);
//        	p.flush();
//        	MemoryMXBean m = p.getMemoryMXBean();
//        	ThreadMXBean t = p.getThreadMXBean();
//        	OperatingSystemMXBean o = p.getOperatingSystemMXBean();
//        	System.out.println(l.displayName() + " LoadAverage: " + o.getSystemLoadAverage() + " Arch: " + o.getArch()
//        			+ " AvailableProcessors:" + o.getAvailableProcessors() + " Name: " + o.getName() + " Version: " + o.getVersion());
//        	System.out.println(l.displayName() + " MemoryUsed: " + m.getHeapMemoryUsage().getUsed()/1024/1024);
//        	System.out.println(l.displayName() + " ThreadCount: " + t.getThreadCount() + " PeakThreadCount: " + t.getPeakThreadCount());
//        }
		
		String OsName = System.getProperties().getProperty("os.name");
		System.out.println(OsName);
	}

}
