package com.ringtop.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/****
 * 把异常信息的信息全部写出来
 * @author Administrator
 *
 */
public class ExceptionToString {

	public static String getTrace(Throwable t) {
	       
		 StringWriter stringWriter = new StringWriter();
		 PrintWriter writer = new PrintWriter(stringWriter);
		 t.printStackTrace(writer);
		 StringBuffer buffer = stringWriter.getBuffer();
		 return buffer.toString();
   }
}
