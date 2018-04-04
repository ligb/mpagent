package com.cgs.mpagent.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LinuxServiceCtrl implements ServiceCtrl{
	private String name;
	
	public LinuxServiceCtrl(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean start() {
		try {
			java.lang.Process p = Runtime.getRuntime().exec("service " + this.name + " start");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = null;
			while((str = in.readLine())!= null){
				System.out.println(str);
				if(str.indexOf("started") != -1){
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean stop() {
		try {
			java.lang.Process p = Runtime.getRuntime().exec("service " + this.name + " stop");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = null;
			while((str = in.readLine())!= null){
				System.out.println(str);
				if(str.indexOf("stopped") != -1){
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
