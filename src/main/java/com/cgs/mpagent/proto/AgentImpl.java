package com.cgs.mpagent.proto;


import java.util.List;

import org.apache.avro.AvroRemoteException;

import com.cgs.mpagent.ctrl.CollectorFactory;
import com.cgs.mpagent.ctrl.ProcessCtrl;
import com.cgs.mpagent.ctrl.ServiceCtrl;
import com.cgs.mpagent.ctrl.SysCollector;

public class AgentImpl implements Agent{
	private SysCollector collector;
	private ProcessCtrl processCtrl;
	
	public AgentImpl(){
		collector = CollectorFactory.createSysCollector();
		processCtrl = CollectorFactory.createProcess();
	}
	
	@Override
	public CharSequence getSystemName() throws AvroRemoteException {
		return collector.getSystemName();
	}
	
	@Override
	public int getCpuPct() throws AvroRemoteException {
		return collector.getCpuPct();
	}
	
	@Override
	public int getMemoryPct() throws AvroRemoteException {
		return collector.getMemoryPct();
	}

	@Override
	public int getSwapPct() throws AvroRemoteException {
		return collector.getSwapPct();
	}
	
	@Override
	public long getDiskIO() throws AvroRemoteException {
		return collector.getDiskIO();
	}

	@Override
	public long getNetIO() throws AvroRemoteException {
		return collector.getNetIO();
	}
	
	@Override
	public long getFreeMemory() throws AvroRemoteException {
		return collector.getFreeMemory();
	}
	
//	@Override
//	public Map<CharSequence, Long> getMemInfo() throws AvroRemoteException {
//		return collector.getMemInfo();
//	}
	
//	@Override
//	public Map<CharSequence, Double> getLoadAvg() throws AvroRemoteException {
//		return collector.getLoadAvg();
//	}

	@Override
	public List<Disk> getDisks() throws AvroRemoteException {
		return collector.getDisks();
	}

//	@Override
//	public List<Process> getJavaProcesses() throws AvroRemoteException {
//		return collector.getJavaProcesses();
//	}
	
	@Override
	public boolean startService(CharSequence name) throws AvroRemoteException {
		ServiceCtrl service = CollectorFactory.createService(name.toString());
		return service.start();
	}
	
	@Override
	public boolean stopService(CharSequence name) throws AvroRemoteException {
		ServiceCtrl service = CollectorFactory.createService(name.toString());
		return service.stop();
	}
	
	@Override
	public Process getPrcState(CharSequence startup) throws AvroRemoteException {
		return processCtrl.getPrcState(startup.toString());
	}
	
	@Override
	public JavaProcess getJavaState(CharSequence startup) throws AvroRemoteException {
		JavaProcess javaProcess = processCtrl.getJavaProcess(startup.toString());
		return javaProcess;
	}

}
