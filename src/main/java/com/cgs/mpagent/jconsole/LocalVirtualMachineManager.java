package com.cgs.mpagent.jconsole;

import java.util.Date;
import java.util.Map;


public class LocalVirtualMachineManager {
	private static Map<Integer, LocalVirtualMachine> map = LocalVirtualMachine.getAllVirtualMachines();
	private static Date date = new Date();
	
//	private LocalVirtualMachineManager(){
//		map = LocalVirtualMachine.getAllVirtualMachines();
//		date = new Date();
//	}
	
	public static Map<Integer, LocalVirtualMachine> getLocalVirtualMachines(){
		if((new Date().getTime() - date.getTime()) > 5000*60){
			map = LocalVirtualMachine.getAllVirtualMachines();
		}
		return map;
	}
}
