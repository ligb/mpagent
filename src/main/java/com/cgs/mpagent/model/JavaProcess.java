package com.cgs.mpagent.model;

public class JavaProcess {
	private int pid;
	private String name;
	private long heapMemoryUsed;
	private long heapMemoryMax;
	private long nonheapMemoryUsed;
	private long nonheapMemoryMax;
	private long threadCount;
	private long daemonThreadCount;
	private long peakThreadCount;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getHeapMemoryUsed() {
		return heapMemoryUsed;
	}
	public void setHeapMemoryUsed(long heapMemoryUsed) {
		this.heapMemoryUsed = heapMemoryUsed;
	}
	public long getHeapMemoryMax() {
		return heapMemoryMax;
	}
	public void setHeapMemoryMax(long heapMemoryMax) {
		this.heapMemoryMax = heapMemoryMax;
	}
	public long getNonheapMemoryUsed() {
		return nonheapMemoryUsed;
	}
	public void setNonheapMemoryUsed(long nonheapMemoryUsed) {
		this.nonheapMemoryUsed = nonheapMemoryUsed;
	}
	public long getNonheapMemoryMax() {
		return nonheapMemoryMax;
	}
	public void setNonheapMemoryMax(long nonheapMemoryMax) {
		this.nonheapMemoryMax = nonheapMemoryMax;
	}
	public long getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(long threadCount) {
		this.threadCount = threadCount;
	}
	public long getDaemonThreadCount() {
		return daemonThreadCount;
	}
	public void setDaemonThreadCount(long daemonThreadCount) {
		this.daemonThreadCount = daemonThreadCount;
	}
	public long getPeakThreadCount() {
		return peakThreadCount;
	}
	public void setPeakThreadCount(long peakThreadCount) {
		this.peakThreadCount = peakThreadCount;
	}
	
}
