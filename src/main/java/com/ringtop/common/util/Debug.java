package com.ringtop.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import sun.reflect.Reflection;

/********************
 * 日志管理
 * @author Administrator
 *
 */
public class Debug {
	
	
	public static final boolean debug = true;

	public Debug() {
	
	}

	public static void debug(String debug) {
		Logger.getLogger(Reflection.getCallerClass(2)).debug(debug);
	}

	public static void Error(String error) {
		Logger.getLogger(Reflection.getCallerClass(2)).error(error);		
	}

	public static void Fatal(String fatal) {
		Logger.getLogger(Reflection.getCallerClass(2)).fatal(fatal);
	}

	public static void info(String msg) {
		try {
			if (Debug.debug) {
				if(Reflection.getCallerClass(2)!=null && Reflection.getCallerClass(2).getName().indexOf("Thread")>=0){
					System.out.println(Reflection.getCallerClass(2).getName()+"- "+msg);
				}else if(Reflection.getCallerClass(2)!=null){
					Logger.getLogger(Reflection.getCallerClass(2)).info(msg);
				}else{
					System.out.println(msg);
				}
			}
		} catch (Exception e) {
			System.err.println("Debug error");
			e.printStackTrace();
		}


	}

	public static void writeLog(String s) {
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(Debug.logFile, true));
			br.write(s);
			br.newLine();
		} catch (IOException ex) {
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex1) {
					ex1.printStackTrace();
				}
			}
		}

	}

	public static String logFile = System.getProperty("user.dir")+ File.separator + "log.txt";
}
