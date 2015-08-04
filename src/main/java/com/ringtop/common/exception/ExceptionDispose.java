package com.ringtop.common.exception;

public class ExceptionDispose {	
	
	public static Object[] dispose(Exception e){	
		if(e==null){
			return null;
		}
		return disposeExceptionMsg(e.getMessage());
	}
	public static String handleException(String message){	
		if(message==null){
			return "";
		}
		Object[] obj=ExceptionDispose.disposeExceptionMsg(message);
		if(obj!=null){
			if(obj[0]!=null){
				return((String)obj[0]);
			}			
		}		
		return message;
	}
	public static Object[] disposeExceptionMsg(String message){
		Object[] obj=new Object[2];
		
		if(message==null){
			return null;
		}else if(message.indexOf("child record found")>=0){
			obj[0]="错误:有其他记录引用该记录，不能修改!"+message;
			obj[1]=null;
		}
		else if(message.indexOf("ORA-01089")>=0){
			obj[0]="错误:连接数据库失败!"+message;
			obj[1]=null;
		}else if(message.indexOf("Network Adapter could not")>=0 || message.indexOf("not create PoolableConnectionFactory")>=0){
			obj[0]="错误:网络异常，连接数据库失败!"+message;
			obj[1]=null;
		}
		else if(message.indexOf("Connection reset")>=0){
			obj[0]="错误:数据库连接失效,读写数据库失败!"+message;			
			obj[1]=null;
		}else if(message.indexOf("inserted value too large")>=0 ){
			obj[0]="错误:写入字段内容过大,读写数据库失败!"+message;
			obj[1]=null;
		}
		else{
			System.out.println("exception message="+message);
			obj[0]=null;
			obj[1]=message;
		}
		return obj;
	}	
	
}
