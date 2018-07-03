package cn.lynu.lyq.study.mywschat.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class WebSocketUtils {
	public static void main(String[] args) {
		System.out.println(getProcessID());
		System.out.println(getThreadID());
	}
	
	public static final int getProcessID() {  
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue();  
    } 
	
	public static final long getThreadID(){
		return Thread.currentThread().getId();
	}
	
	public static final String getProcessAndThreadID(){
		return "ProcessID="+getProcessID()+", Thread ID=" + getThreadID();
	}
	
	
}
