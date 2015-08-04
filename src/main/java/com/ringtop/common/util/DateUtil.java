package com.ringtop.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间格式化工具
 * @author Administrator
 *
 */
public class DateUtil {

	public static final String FMT_YY_MM_DD = "yyyy-MM-dd";
	public static final String FMT_YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String FMT_YYMMDD = "yyyyMMdd";
	public static final String FMT_HHMMSS = "HHmmss";
	public static final String FMT_YYMMDDHHMMSS = "yyyyMMddHHmmss";


	/******************************************************
	 * 返回字符串,指定格式的日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String shortFmt(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/******************************************************
	 * 返回字符串,指定格式的当前日期
	 * 
	 * @return
	 */
	public static String getDate() {
		return shortFmt(new Date(), FMT_YY_MM_DD);
	}
	
	public static String getDate(String format) {
		
		return shortFmt(new Date(), format);
	}

	/******************************************************
	 * 返回字符串,yyyy-MM-dd，指定日期
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFmt(Date date) {
		return shortFmt(date, FMT_YY_MM_DD);
	}

	/******************************************************
	 * 获取指定日期的前interval天(interval 为负数) -1 前一天 或者后interval天(interval 为正数) 1 后一天
	 * 
	 * @param date
	 * @param interval
	 * @return
	 */
	public static String getBeforeOrAfterDate(Date date, int interval,String format) {
		Calendar currentTime = Calendar.getInstance();
		currentTime.setTime(date);
		currentTime.add(Calendar.DATE, interval);
		return shortFmt(currentTime.getTime(), format);
	}

	/******************************************************
	 * 获取当前时间的上一天
	 * 
	 * @return
	 */
	public static String getYesterday() {
		return getBeforeOrAfterDate(new Date(), -1, FMT_YY_MM_DD);
	}

	public static String getYesterday_YYYYMMDD() {
		return getBeforeOrAfterDate(new Date(), -1, FMT_YYMMDD);
	}

	//////////////////////以下返回java.util.Date型///////////////////////////////////

	/******************************************************
	 * 把java.sql.date 转换成java.util.date
	 */
	public static Date convertSqlDateToUtilDate(java.sql.Date sqlDate){
		return new Date(sqlDate.getTime());
	}
	
	
	/******************************************************
	 * 返回日期型，指定格式的日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(String strDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		if(null != strDate && !"".equals(strDate)){
			try {
				date = sdf.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	
	/******************************************************
	 * 返回日期型，yyyy-MM-dd HH:mm:ss格式，指定日期
	 * 
	 * @param param
	 * @return
	 */
	public static Date parse(String param) {
		return parse(param, FMT_YY_MM_DD_HH_MM_SS);
	}
	

	/******************************************************
	 * 返回日期型，yyyy-MM-dd格式，指定日期
	 * 
	 * @param param
	 * @return
	 */
	public static Date parseShort(String param) {
		return parse(param, FMT_YY_MM_DD);
	}

	
	//////////////////////以下返回java.sql.Date型////////////////////////////////////

	/*******************************************************
	 * 把.util.date 转换成java.sql.date
	 */
	public static Date convertUtilDateToSqlDate(Date utilDate){
		return new java.sql.Date(utilDate.getTime());
	}
	public static java.sql.Date parseDate(String param) {
		return parseSqlDate(param, FMT_YYMMDD);
	}

	//////////////////////以下返回java.sql.Timestamp型///////////////////////////////

	public static Timestamp parseTime(String param) {
		return new Timestamp(parseSqlDate(param, FMT_YYMMDD).getTime());
	}


	/******************************************************
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static java.sql.Date parseSqlDate(String strDate, String format) {
		return new java.sql.Date(parse(strDate, format).getTime());
	}

	// ////////////////////以下返回INT型//////////////////////////////////////

	/******************************************************
	 * 返回某年某月的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDays(int year, int month) {
		int[] numberMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int numberOfMonth = numberMonth[month - 1];
		if (month == 2
				&& ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)))) {
			numberOfMonth++;
		}
		return numberOfMonth;
	}

	
	/******************************************************
	 * 获取指定日期的年份
	 * 
	 * @param date
	 * @return
	 * @throws NumberFormatException
	 */
	public static int getYear(String date) throws NumberFormatException {
		String m = date.substring(0, 4);
		return Integer.parseInt(m);
	}

	
	/******************************************************
	 * 获取指定日期的月份
	 * 
	 * @param date
	 * @return
	 * @throws NumberFormatException
	 */
	public static int getMonth(String date) throws NumberFormatException {
		String m = date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3);
		return Integer.parseInt(m);
	}

	
	/******************************************************
	 * 获取指定日期的天
	 * 
	 * @param date
	 * @return
	 * @throws NumberFormatException
	 */
	public static int getDay(String date) throws NumberFormatException {
		String m = date.substring(date.lastIndexOf("-") + 1);
		return Integer.parseInt(m);
	}
	
	
	/******************************************************
	 * 获取指定两个时间段的天数
	 * 
	 * @param date
	 * @return
	 * @throws NumberFormatException
	 */
	public static int getDayNum(String dateBegin,String dateEnd) throws NumberFormatException {
		Long n = parseShort(dateEnd).getTime() - parseShort(dateBegin).getTime();
		int dayNum = (int)(n/(1000*60*60*24));  
		return dayNum;
	}
	
	
	/******************************************************    
	 * 获取当天时间 
	 * @return
	 */
    public static String getNowTime(){   
        Date now = new Date();      
      //可以方便地修改日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(FMT_YY_MM_DD);      
        String hehe = dateFormat.format(now);      
        return hehe;   
    }
    
    
    /******************************************************
	 * 获取指定两个时间段的天数
	 * 
	 * @param date
	 * @return
	 * @throws NumberFormatException
	 */
	public static int getDayNumValue(String dateBegin,String dateEnd) throws NumberFormatException {
		Long n = parseDate(dateEnd).getTime() - parseDate(dateBegin).getTime();
		int dayNum = (int)(n/(1000*60*60*24));  
		return dayNum;
	}
	
	
	/******************************************************
     * 返回两日期间隔天数
     * @param begin
     * @param end
     * @return
     * @throws ParseException
     */
	public static long getDiffDays(String begin, String end) throws ParseException{
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    	long head = df.parse(begin).getTime();
    	long tail = df.parse(end).getTime();
    	long days = (tail - head)/(1000*60*60*24); 
    	return days;
    }
	
	
	/******************************************************
	* @see 得到当前时刻的时间字符串
	* @return String para的标准时间格式的串,例如：返回'2011-08-09 16:00:00'
	*/
	public static Timestamp getTimestamp() {
		Timestamp ret = null;
		try {
				java.util.Date currentDate = new java.util.Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
				String date = dateFormat.format(currentDate);
				Date nowdate = dateFormat.parse(date);
				long datelong = nowdate.getTime();
				ret = new Timestamp(datelong);
			}
			catch (Exception e) {
			}
			return ret;
		}
	
	public static String getTimestampStr(Timestamp ts){
		String tsStr = "" ;
		if(ts != null){
			try{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				tsStr = dateFormat.format(new Date(ts.getTime()));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return tsStr;
	}
	
	
	/******************************************************
	 * 
	 * @param currentDate 当前日期
	 * @return 前或后几天的日期
	 */
	public static java.sql.Date getBeforeOrAfterDate(java.sql.Date currentDate, int interval){
		Calendar currentTime = Calendar.getInstance();
		currentTime.setTime(new Date(currentDate.getTime()));
		currentTime.add(Calendar.DATE, interval);
		
		Date afterUtilDate = currentTime.getTime();
		return new java.sql.Date(afterUtilDate.getTime());
	}
	
	
	/*************************************************
	 * 获取当前日期对应的前几个月或者或几个月的日期
	 * @param month		正数表示后面，负数表示前面
	 * @param format	日期格式
	 * @return
	 */
	public static String getCurrentBeforeDate(int month, String format) {
		
		SimpleDateFormat matter=new SimpleDateFormat(format);
		
	     Calendar calendar = Calendar.getInstance();
	     //将calendar装换为Date类型
	     @SuppressWarnings("unused")
		Date date01 = calendar.getTime();
	     
	     calendar.add(Calendar.MONTH, month);
	     
	     Date date02 = calendar.getTime();

	     return matter.format(date02);
	}  
	
	
	/*************************************************
	 * 获取当前日期指定的前后天数对应的日期
	 * @param day
	 * @param formatType
	 * @return
	 */
	public static String getCurrentBeforeDateByDay(int day, String formatType) {
		
		SimpleDateFormat format = new SimpleDateFormat(formatType);
		Calendar calendar = Calendar.getInstance();
		@SuppressWarnings("unused")
		Date date = calendar.getTime();
		
		calendar.add(Calendar.DATE, day);
		
		Date date2 = calendar.getTime();
		
		return format.format(date2);
	}
	
	/************************************************
	 * 
	 * 当前时间和指定的时间比较，如果当前时间小于指定的时间
	 * 	  则返回当天指定的时间，不然后返回指定时间的后一天。
	 * 
	 * @param hhmmss	指定的时间-格式为：HH:mm:ss
	 * @param hhmmss	任务执行的起始时间
	 * @return
	 */
	public static Date getTagDate(String hh_mm_ss) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();

		Date date = calendar.getTime();
		
		String dateTagStr = dateFormat.format(date)+" " + hh_mm_ss;
		int flag = dateTagStr.compareTo(format.format(date));
		Date dateTag = null;
		
		try {
			
			if(flag < 1) {
				calendar.add(Calendar.DATE, 1);
				dateTagStr = dateFormat.format(calendar.getTime()) + " " + hh_mm_ss;
				dateTag = format.parse(dateTagStr);
			} else {
				dateTag = format.parse(dateTagStr);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return dateTag;
	}
}
