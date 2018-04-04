package com.cgs.mpagent.ctrl;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.SigarException;

import com.cgs.mpagent.proto.Disk;
import com.cgs.mpagent.util.SigarUtil;

public class WinCollector implements SysCollector{

	@Override
	public String getSystemName() {
		return System.getProperties().getProperty("os.name");
	}

	@Override
	public int getCpuPct() {
		int usage = 0;
		try {
			usage = SigarUtil.getCpuUsage();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return usage;
	}

	@Override
	public Map<CharSequence, Long> getMemInfo() {
		Map<CharSequence, Long> map = new HashMap<CharSequence, Long>();
		return map;
	}

	@Override
	public long getTotalMemory() {
		try{
			OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();   
			long totalMemorySize = osmxb.getTotalPhysicalMemorySize();
			return totalMemorySize;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long getFreeMemory() {
		try{
			OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();   
			long freeMemorySize = osmxb.getFreePhysicalMemorySize();
			return freeMemorySize;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getMemoryPct() {
		try{
			OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();   
			int pct = (int) (100*(osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize())/osmxb.getTotalPhysicalMemorySize());
			return pct;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getSwapPct() {
		try{
			OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();   
			int pct = (int) (100*(osmxb.getTotalSwapSpaceSize() - osmxb.getFreeSwapSpaceSize())/osmxb.getTotalSwapSpaceSize());
			return pct;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Map<CharSequence, Double> getLoadAvg() {
		Map<CharSequence, Double> map = new HashMap<CharSequence, Double>();
		return map;
	}

	@Override
	public List<Disk> getDisks() {
		List<Disk> list = new ArrayList<Disk>();
		try {
			Map<String, FileSystemUsage> map = SigarUtil.getDiskUsage();
			for (String key : map.keySet()) {
				Disk disk = new Disk();
				disk.setName(key);
				disk.setTotal((double)Math.round((double) map.get(key).getTotal() / 1024 / 1024*100) / 100);
				disk.setUsed((double)Math.round((double) map.get(key).getUsed() / 1024 / 1024*100) / 100);
				disk.setMounted(key);
				list.add(disk);
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
		return list;
	}

	//bps
	@Override
	public long getNetIO() {
		try {
			long[] last = SigarUtil.getNetIO();
			Thread.sleep(1000);
			long[] cur = SigarUtil.getNetIO();
			long io = 8*(cur[0] + cur[1] - last[0] -last[1]);
			return io > 0 ? io:0 ;
		} catch (SigarException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}

	//bytes / s
	@Override
	public long getDiskIO() {
		try {
			long[] last = SigarUtil.getDiskIO();
			Thread.sleep(1000);
			long[] cur = SigarUtil.getDiskIO();
			long io = cur[0] + cur[1] - last[0] -last[1];
			return io > 0 ? io:0 ;
		} catch (SigarException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
