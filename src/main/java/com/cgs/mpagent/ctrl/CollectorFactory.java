package com.cgs.mpagent.ctrl;

public class CollectorFactory {
	
	public static String OsName = System.getProperties().getProperty("os.name");
	
	public static SysCollector createSysCollector(){
		if(OsName.contains("Linux")){
			return new LinuxCollector();
		}else if(OsName.contains("Windows")){
			return new WinCollector();
		}else{
			return null;
		}
	}
	
	public static ServiceCtrl createService(String svcName){
		if(OsName.contains("Linux")){
			return new LinuxServiceCtrl(svcName);
		}else if(OsName.contains("Windows")){
			return null;
		}else{
			return null;
		}
	}
	
	public static ProcessCtrl createProcess(){
		if(OsName.contains("Linux")){
			return new LinuxProcessCtrl();
		}else if(OsName.contains("Windows")){
			return new WinProcessCtrl();
		}else{
			return null;
		}
	}
}
