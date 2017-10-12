package com.game.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {
	/** yyyyMMdd */
	public static final String DATE_FORMAT_0 = "yyyyMMdd";
	/** yyyy-MM-dd */
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
	/** yyyy-MM-dd HH:mm:ss */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Date类型转String
	 * @param day
	 * @param format
	 * @return
	 */
	public static String date2String(Date day, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String formatStr = simpleDateFormat.format(day);
		return formatStr;
	}
	
	/**
	 * String类型转Date
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date string2Date(String dateStr, String format) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		DateTime dateTime = formatter.parseDateTime(dateStr);
		Date date = dateTime.toDate();
		return date;
	}
	
	/**
	 * 是否同一天
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameDay(long time1, long time2) {
		return isSameDay(new Date(time1), new Date(time2));
	}
	
	/**
	 * 是否同一天
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static boolean isSameDay(Date day1, Date day2) {
		String day1Str = date2String(day1, DATE_FORMAT_0);
		String day2Str = date2String(day2, DATE_FORMAT_0);
		return day1Str.equals(day2Str);
	}
	
	/**
	 * 是否第二天
	 * @param preDate
	 * @param nextDate
	 * @return
	 */
	public static boolean isNextDay(Date preDate, Date nextDate) {
		Date day = addDay(preDate, 1);
		return isSameDay(day, nextDate);
	}
	
	/**
	 * 是否第二天
	 * @param preTime
	 * @param nextTime
	 * @return
	 */
	public static boolean isNextDay(long preTime, long nextTime) {
		return isNextDay(new Date(preTime), new Date(nextTime));
	}
	
	/**
	 * 取得前一天日期
	 * @return
	 */
	public static Date getPreviousDay() {
		return getPreviousDay(new Date());
	}
	
	/**
	 * 取得前一天日期
	 * @param curDate
	 * @return
	 */
	public static Date getPreviousDay(Date curDate) {
		return addDay(curDate, -1);
	}
	
	/**
	 * 添加天数
	 * @param day
	 * @param add
	 * @return
	 */
	public static Date addDay(Date day, int addDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.add(Calendar.DATE, addDay);
		return calendar.getTime();
	}
}
