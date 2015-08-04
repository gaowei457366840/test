package com.ringtop.common.exception;


public class BaseException extends RuntimeException {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseException(String message) {
	        super(message);
	    }

	    public BaseException(Exception ex) {
	        super(ex.getMessage());
	    }
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private Throwable cause;    
//	public BaseException (String msg){    
//	 super(msg);    
//	}    
//	     
//	public BaseException(String msg, Throwable ex){    
//	 super(msg);    
//	 this.cause = ex;    
//	}    
//	     
//	public Throwable getCause(){    
//	 return (this.cause == null ? this :this.cause);    
//	}    
//	     
//	public String getMessage(){    
//	 String message = super.getMessage();    
//	 Throwable cause = getCause();    
//	   if(cause != null){    
//	     message = message + ";nested Exception is " + cause;    
//	   }    
//	 return message;    
//	}    
//	public void printStackTrace(PrintStream ps){    
//	 if(getCause() == null){    
//	    super.printStackTrace(ps);    
//	        
//	 }else{    
//	 ps.println(this);    
//	 getCause().printStackTrace(ps);    
//	 }    
//	}    
//	     
//	public void printStackTrace(PrintWriter pw){    
//	 if(getCause() == null){    
//	    super.printStackTrace(pw);    
//	 }    
//	 else{    
//	    pw.println(this);    
//	    getCause().printStackTrace(pw);    
//	 }    
//	}    
//	public void printStackTrace(){    
//	 printStackTrace(System.err);    
//	}    

}
