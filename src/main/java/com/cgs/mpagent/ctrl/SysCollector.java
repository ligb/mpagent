package com.cgs.mpagent.ctrl;

import java.util.List;
import java.util.Map;

import com.cgs.mpagent.proto.Disk;

public interface SysCollector {
	public String getSystemName();
	//%
	public int getCpuPct(); 
	//kB
	public Map<CharSequence, Long> getMemInfo();
	//kB
	public long getTotalMemory();
	//kB
	public long getFreeMemory();
	//%
	public int getMemoryPct();
	//%
	public int getSwapPct();
	// 1min, 5min, 15min
	public Map<CharSequence, Double> getLoadAvg();
	//GB
	public List<Disk> getDisks();
	
	public long getNetIO();
	
	public long getDiskIO();
	
}
