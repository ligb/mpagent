package com.cgs.mpagent.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.cgs.mpagent.jconsole.LocalVirtualMachine;
import com.cgs.mpagent.jconsole.ProxyClient;
import com.cgs.mpagent.proto.JavaProcess;
import com.cgs.mpagent.proto.Process;


public class LinuxProcessCtrl implements ProcessCtrl{
	@Override
	public Process getPrcState(String startup) {
		Process process = new Process();
		process.setCommand(startup);
		try {
			String[] cmd = {
					"/bin/sh",
					"-c",
					"ps -aux | grep " + startup
					};
			java.lang.Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String[] strArr = null;
			String str = null;
			while((str = in.readLine())!= null){
				if(str.indexOf("grep") == -1){
					strArr = str.split("\\s{1,}");
					process.setPid(Integer.parseInt(strArr[1]));
					process.setMemory(Integer.parseInt(strArr[5]));
//					process.setCommand(strArr[strArr.length-1]);	
					return process;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
					return javaProcess;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return javaProcess;
	}
	
//	@Override
//	public List<Process> getJavaProcesses() {
//		try {
//			List<Process> list = new ArrayList<Process>();
//			String[] cmd = {
//					"/bin/sh",
//					"-c",
//					"ps -aux | grep java"
//					};
//			java.lang.Process p = Runtime.getRuntime().exec(cmd);
//			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//			String[] strArr = null;
//			String str = null;
//			while((str = in.readLine())!= null){
//				strArr = str.split("\\s{1,}");
//				if(strArr[strArr.length-1].equals("java")){
//					continue;
//				}
//				Process process = new Process();
//				process.setPid(Integer.parseInt(strArr[1]));
//				process.setMemory(Integer.parseInt(strArr[5]));
//				process.setCommand(strArr[strArr.length-1]);				
//				list.add(process);
//			}
//
//			return list;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
