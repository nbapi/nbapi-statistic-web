package com.elong.nbapi.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

	// private static final SimpleDateFormat FORMAT = new SimpleDateFormat();

	public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";
	public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String HHMM = "HH:mm";

	private DateUtils() {
	}

	public static String addDate(String date, int key, int value, String pattern) {
		Date alertTime = DateUtils.parse(date, pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(alertTime);
		calendar.add(key, value);
		return DateUtils.format(calendar.getTime(), pattern);
	}

	public static Date addDate(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date time = cal.getTime();
		return parse(format(time, DateUtils.DATE_YYYY_MM_DD), DateUtils.DATE_YYYY_MM_DD);
	}

	/** 
	 * 上一月的今天日期
	 *
	 * @return
	 */
	public static String getTodayOfLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date time = cal.getTime();
		return format(time, DateUtils.DATE_YYYY_MM_DD);
	}

	public static Date getEarlyMorning(Date dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		Date time = cal.getTime();
		String tempTimeStr = format(time, DateUtils.DATE_YYYY_MM_DD);
		return parse(tempTimeStr + " 00:00:00", DateUtils.YYYY_MM_DD_HH_MM_SS);
	}

	/** 
	 * 今日凌晨到当前时间
	 *
	 * @return
	 */
	public static String[] getToday0ToCurrentTime() {
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String endDateTime = format(time, DateUtils.YYYY_MM_DD_HH_MM_SS);
		Date startDate = parse(endDateTime, DateUtils.DATE_YYYY_MM_DD);
		String startDateTime = format(startDate, DateUtils.YYYY_MM_DD_HH_MM);
		return new String[] { startDateTime, endDateTime };
	}

	/** 
	 * 昨日凌晨到昨日当前时间
	 *
	 * @return
	 */
	public static String[] getYester0ToCurrentTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		String endDateTime = DateUtils.format(time, DateUtils.YYYY_MM_DD_HH_MM_SS);
		Date startDate = DateUtils.parse(endDateTime, DateUtils.DATE_YYYY_MM_DD);
		String startDateTime = DateUtils.format(startDate, DateUtils.YYYY_MM_DD_HH_MM);
		return new String[] { startDateTime, endDateTime };
	}

	/** 
	 * 上周凌晨到上周当前时间
	 *
	 * @return
	 */
	public static String[] getLastweek0ToCurrentTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		Date time = cal.getTime();
		String endDateTime = DateUtils.format(time, DateUtils.YYYY_MM_DD_HH_MM_SS);
		Date startDate = DateUtils.parse(endDateTime, DateUtils.DATE_YYYY_MM_DD);
		String startDateTime = DateUtils.format(startDate, DateUtils.YYYY_MM_DD_HH_MM);
		return new String[] { startDateTime, endDateTime };
	}

	/** 
	 * 今日近10分钟前到当前时间
	 *
	 * @return
	 */
	public static String[] getTodayNearly10Time() {
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String endDateTime = DateUtils.format(time, DateUtils.YYYY_MM_DD_HH_MM_SS);
		cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -10);
		time = cal.getTime();
		String startDateTime = DateUtils.format(time, DateUtils.YYYY_MM_DD_HH_MM);
		return new String[] { startDateTime, endDateTime };
	}

	/** 
	 * 昨日近10分钟前到昨日当前时间
	 *
	 * @return
	 */
	public static String[] getYesterNearly10Time() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		String endDateTime = DateUtils.format(time, DateUtils.YYYY_MM_DD_HH_MM_SS);
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.add(Calendar.MINUTE, -10);
		time = cal.getTime();
		String startDateTime = DateUtils.format(time, DateUtils.YYYY_MM_DD_HH_MM);
		return new String[] { startDateTime, endDateTime };
	}

	public static String format(Date date, String pattern) {
		SimpleDateFormat FORMAT = new SimpleDateFormat();
		FORMAT.applyPattern(pattern);
		return FORMAT.format(date);
	}

	public static String format(Date date) {
		return DateUtils.format(date, DateUtils.DATE_YYYY_MM_DD);
	}

	public static Date parse(String date) {
		return parse(date, YYYY_MM_DD_HH_MM_SS);
	}

	public static Date parse(String date, String pattern) {
		SimpleDateFormat FORMAT = new SimpleDateFormat();
		FORMAT.applyPattern(pattern);
		try {
			return FORMAT.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

}
