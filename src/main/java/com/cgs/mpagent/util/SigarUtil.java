package com.cgs.mpagent.util;

import java.util.HashMap;
import java.util.Map;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class SigarUtil {
	static Sigar sigar = new Sigar();
	
	public static int getCpuUsage() throws SigarException{
		CpuPerc[] cpus = sigar.getCpuPercList();
		double sum = 0;
		for(CpuPerc cpu: cpus){
			sum += cpu.getCombined();
		}
		int cpuUsage = (int) (sum*100)/cpus.length;
		return cpuUsage;
	} 
	
	public static Map<String, FileSystemUsage> getDiskUsage() throws SigarException{
		Map<String, FileSystemUsage> map = new HashMap<String, FileSystemUsage>();
		FileSystem fslist[] = sigar.getFileSystemList();
		for (int i = 0;i < fslist.length; i++) {
			try {	
				map.put(fslist[i].getDirName(), sigar.getFileSystemUsage(fslist[i].getDirName()));
			} catch (SigarException e) {
				continue;
			}
		}
		return map;
	}
	
	public static long[] getDiskIO() throws SigarException{
		FileSystem fslist[] = sigar.getFileSystemList();
		long[] io = {0,0};
		for (FileSystem fs: fslist) {
			FileSystemUsage usage = null;
			try {
				usage = sigar.getFileSystemUsage(fs.getDirName());
				io[0] += usage.getDiskReadBytes();
				io[1] += usage.getDiskWriteBytes();
			} catch (SigarException e) {
				continue;
			}
		}
		return io;
	}
	
	//Bytes
	public static long[] getNetIO() throws SigarException{
		String ifNames[] = sigar.getNetInterfaceList();
		long[] io = {0,0};
		for(String name: ifNames){
			try {
				NetInterfaceStat netStat = sigar.getNetInterfaceStat(name);
				io[0] += netStat.getRxBytes();
				io[1] += netStat.getTxBytes();
			} catch (SigarException e) {
				continue;
			}
		}
		return io;
	}
}
