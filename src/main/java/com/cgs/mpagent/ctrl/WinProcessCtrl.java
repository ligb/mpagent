package com.cgs.mpagent.ctrl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.cgs.mpagent.jconsole.LocalVirtualMachine;
import com.cgs.mpagent.jconsole.ProxyClient;
import com.cgs.mpagent.proto.JavaProcess;
import com.cgs.mpagent.proto.Process;

public class WinProcessCtrl implements ProcessCtrl{
	protected Logger log = Logger.getLogger(this.getClass());
	@Override
	public Process getPrcState(String startup) {
		Process process = new Process();
		process.setCommand(startup);
		return process;
	}

	@Override
	public JavaProcess getJavaProcess(String startup) {
		Map<Integer, LocalVirtualMachine> map = LocalVirtualMachine.getAllVirtualMachines();
		JavaProcess javaProcess = new JavaProcess();
		javaProcess.setName(startup);
		for(Integer i: map.keySet()){
			LocalVirtualMachine lvm = map.get(i);
			if(lvm.displayName().toLowerCase().contains(startup.toLowerCase())){
				try {
					ProxyClient client = ProxyClient.getProxyClient(lvm);
					if(client.isDead()){
						client.connect();
					}else{
						client.flush();
					}
					javaProcess.setName(lvm.displayName());
					javaProcess.setPid(i);
					javaProcess.setHeapMemoryMax(client.getMemoryMXBean().getHeapMemoryUsage().getMax());
					javaProcess.setHeapMemoryUsed(client.getMemoryMXBean().getHeapMemoryUsage().getUsed());
					javaProcess.setNonheapMemoryMax(client.getMemoryMXBean().getNonHeapMemoryUsage().getMax());
					javaProcess.setNonheapMemoryUsed(client.getMemoryMXBean().getNonHeapMemoryUsage().getUsed());
					javaProcess.setThreadCount(client.getThreadMXBean().getThreadCount());
					javaProcess.setPeakThreadCount(client.getThreadMXBean().getPeakThreadCount());
					javaProcess.setDaemonThreadCount(client.getThreadMXBean().getDaemonThreadCount());
					map.clear();
					return javaProcess;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		map.clear();
		return javaProcess;
	}

}
