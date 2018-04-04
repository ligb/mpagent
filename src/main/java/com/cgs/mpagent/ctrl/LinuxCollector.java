package com.cgs.mpagent.ctrl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.cgs.mpagent.proto.Disk;

public class LinuxCollector implements SysCollector{
	private Map<String, Object> lastCpuInfo;
	private Map<String, Object> curCpuInfo;
	public LinuxCollector(){
		lastCpuInfo = getCpuInfo();
	}
	
	@Override
	public int getCpuPct() {
		try {
			curCpuInfo = getCpuInfo();

			long user1 = (long) lastCpuInfo.get("user");
			long nice1 = (long) lastCpuInfo.get("nice");
			long system1 = (long) lastCpuInfo.get("system");
			long idle1 = (long) lastCpuInfo.get("idle");

			long user2 = (long) curCpuInfo.get("user");
			long nice2 = (long) curCpuInfo.get("nice");
			long system2 = (long) curCpuInfo.get("system");
			long idle2 = (long) curCpuInfo.get("idle");

			long total1 = user1 + system1 + nice1;
			long total2 = user2 + system2 + nice2;
			float total = total2 - total1;

			long totalIdle1 = user1 + nice1 + system1 + idle1;
			long totalIdle2 = user2 + nice2 + system2 + idle2;
			float totalidle = totalIdle2 - totalIdle1;

			float cpusage = (total / totalidle) * 100;
			return (int) cpusage;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lastCpuInfo = curCpuInfo;
		}
		return 0;
	}
	
	@Override
	public Map<CharSequence, Long> getMemInfo() {
		Map<CharSequence, Long> map = new HashMap<CharSequence, Long>();
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		try {
			inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
			buffer = new BufferedReader(inputs);
			String line = "";
			while (true) {
				line = buffer.readLine();
				if (line == null)
					break;
				int beginIndex = 0;
				int endIndex = line.indexOf(":");
				if (endIndex != -1) {
					String key = line.substring(beginIndex, endIndex);
					beginIndex = endIndex + 1;
					endIndex = line.length();
					String memory = line.substring(beginIndex, endIndex);
					String value = memory.replace("kB", "").trim();
					map.put(key, Long.parseLong(value));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
				inputs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return map;

	}
	
	@Override
	public long getTotalMemory() {
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		try {
			inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
			buffer = new BufferedReader(inputs);
			String line = "";
			while (true) {
				line = buffer.readLine();
				if (line == null)
					break;
				int beginIndex = 0;
				int endIndex = line.indexOf(":");
				if (endIndex != -1) {
					String key = line.substring(beginIndex, endIndex);
					if(key.equals("MemTotal")){
						beginIndex = endIndex + 1;
						endIndex = line.length();
						String memory = line.substring(beginIndex, endIndex);
						String value = memory.replace("kB", "").trim();
						return Long.parseLong(value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
				inputs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return 0;
	}
	
	@Override
	public long getFreeMemory(){
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		try {
			inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
			buffer = new BufferedReader(inputs);
			String line = "";
			while (true) {
				line = buffer.readLine();
				if (line == null)
					break;
				int beginIndex = 0;
				int endIndex = line.indexOf(":");
				if (endIndex != -1) {
					String key = line.substring(beginIndex, endIndex);
					if(key.equals("MemFree")){
						beginIndex = endIndex + 1;
						endIndex = line.length();
						String memory = line.substring(beginIndex, endIndex);
						String value = memory.replace("kB", "").trim();
						return Long.parseLong(value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
				inputs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int getMemoryPct() {
		Map<CharSequence, Long> map = getMemInfo();
		try {
			long memTotal = map.get("MemTotal");
			long memFree = map.get("MemFree");
			long memUsed = memTotal - memFree;
			long buffers = map.get("Buffers");
			long cached = map.get("Cached");

			double usage = (double) (memUsed - buffers - cached) / memTotal * 100;
			return (int) usage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int getSwapPct() {
		Map<CharSequence, Long> map = getMemInfo();
		try {
			long swapTotal = map.get("SwapTotal");
			long swapFree = map.get("SwapFree");
			long swapUsed = swapTotal - swapFree;
			long swapCached = map.get("SwapCached");

			double usage = (double) (swapUsed - swapCached) / swapTotal * 100;
			return (int) usage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Map<CharSequence, Double> getLoadAvg() {
		Map<CharSequence, Double> map = new HashMap<CharSequence, Double>();
		try {
			java.lang.Process p = Runtime.getRuntime().exec("w");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = in.readLine();
			str = str.replace(",", "");
			String[] strArr = str.split("\\s{1,}");
			map.put("1", Double.parseDouble(strArr[strArr.length - 3]));
			map.put("5", Double.parseDouble(strArr[strArr.length - 2]));
			map.put("15", Double.parseDouble(strArr[strArr.length - 1]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public List<Disk> getDisks() {
		List<Disk> list = new ArrayList<Disk>();
		try {
			java.lang.Process p = Runtime.getRuntime().exec("df -hl");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String[] strArr = null;
			String str = in.readLine();
			while((str = in.readLine())!= null){
				strArr = str.split("\\s{1,}");
				if(diskSizeToDouble(strArr[1]) >= 1){
					Disk disk = new Disk();
					disk.setName(strArr[0]);
					disk.setTotal(diskSizeToDouble(strArr[1]));
					disk.setUsed(diskSizeToDouble(strArr[2]));
					disk.setMounted(strArr[5]);
					list.add(disk);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public Map<String, Object> getCpuInfo() {
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
			buffer = new BufferedReader(inputs);
			String line = "";
			long user = 0, nice = 0, system = 0, idle = 0;
			while (true) {
				line = buffer.readLine();
				if (line == null) {
					break;
				}
				if (line.startsWith("cpu")) {
					StringTokenizer tokenizer = new StringTokenizer(line);
					List<String> temp = new ArrayList<String>();
					while (tokenizer.hasMoreElements()) {
						String value = tokenizer.nextToken();
						temp.add(value);
					}
					user += Long.parseLong(temp.get(1));
					nice += Long.parseLong(temp.get(2));
					system += Long.parseLong(temp.get(3));
					idle += Long.parseLong(temp.get(4));
				}
			}
			map.put("user", user);
			map.put("nice", nice);
			map.put("system", system);
			map.put("idle", idle);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
				inputs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return map;
	}
	
	public static Double diskSizeToDouble(String diskSize){
		if (diskSize.indexOf("G") != -1){
			Double size = Double.parseDouble(diskSize.substring(0, diskSize.length()-1));
			return (double)Math.round(size*100)/100;
		}else if(diskSize.indexOf("M") != -1){
			Double size = Double.parseDouble(diskSize.substring(0, diskSize.length()-1))/1024;
			return (double)Math.round(size*100)/100;
		}else{
			return 0.00;
		}
	}

	@Override
	public String getSystemName() {
		return System.getProperties().getProperty("os.name");
	}

	//bps
	@Override
	public long getNetIO() {
        Process pro1,pro2;  
        Runtime r = Runtime.getRuntime();  
        long curRate = 0;
        try {  
            String command = "cat /proc/net/dev";  
            //第一次采集流量数据  
            long startTime = System.currentTimeMillis();  
            pro1 = r.exec(command);  
            BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));  
            String line = null;  
            long inSize1 = 0, outSize1 = 0;  
            while((line=in1.readLine()) != null){     
                line = line.trim();  
                if(line.startsWith("eno") || line.startsWith("eth")){  
                    String[] temp = line.split("\\s+");   
                    inSize1 = Long.parseLong(temp[1]); //Receive bytes,单位为Byte  
                    outSize1 = Long.parseLong(temp[9]);             //Transmit bytes,单位为Byte  
                    break;  
                }                 
            }     
            in1.close();  
            pro1.destroy();  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
            	e.printStackTrace();
            }  
            //第二次采集流量数据  
            long endTime = System.currentTimeMillis();  
            pro2 = r.exec(command);  
            BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));  
            long inSize2 = 0 ,outSize2 = 0;  
            while((line=in2.readLine()) != null){     
                line = line.trim();  
                if(line.startsWith("eno") || line.startsWith("eth")){  
                    String[] temp = line.split("\\s+");   
                    inSize2 = Long.parseLong(temp[1]);  
                    outSize2 = Long.parseLong(temp[9]);  
                    break;  
                }                 
            }  
            float interval = (float)(endTime - startTime)/1000;  
            //网口传输速度,单位为bps  
            curRate = (long) ((inSize2 - inSize1 + outSize2 - outSize1)*8/(interval));                
            in2.close();  
            pro2.destroy(); 
        } catch (Exception e) {  
            e.printStackTrace();
            return -1;
        }     
        return curRate > 0 ? curRate:0;  
	}

	//bytes / s
	@Override
	public long getDiskIO() {
		Process proc;
		Runtime r = Runtime.getRuntime();  
		float read = 0, write = 0;
		try{
			String command = "iostat -dx";
			proc = r.exec(command);  
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null; 
			//单位 kB/s
			while((line = in.readLine()) != null){
				line = line.trim();
				if(line.isEmpty() || line.startsWith("Device") || line.startsWith("Linux")){
					continue;
				}
				String[] temp = line.split("\\s+");
				read += Float.parseFloat(temp[5]);
				write += Float.parseFloat(temp[6]);
			}
			in.close();  
            proc.destroy(); 
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return (long) (Math.abs(read) + Math.abs(write))*1000;
	}
}
