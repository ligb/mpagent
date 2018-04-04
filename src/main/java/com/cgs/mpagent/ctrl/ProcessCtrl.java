package com.cgs.mpagent.ctrl;

import com.cgs.mpagent.proto.JavaProcess;
import com.cgs.mpagent.proto.Process;

public interface ProcessCtrl {
	public Process getPrcState(String startup);  //return null if not run
	public JavaProcess getJavaProcess(String startup);  //return null if not run
//	public List<Process> getJavaProcesses();
}
