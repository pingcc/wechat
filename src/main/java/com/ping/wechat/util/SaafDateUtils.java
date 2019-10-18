package com.ping.wechat.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * 比较两个日期区间工具类
 * 是否相同checkSameEdge，
 * 重叠contain，
 *
 * @author luofenwu
 */
public class SaafDateUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * {@link LocalDate} 转 {@link Date}
     * @param localDate {@link LocalDate}
     * @author ZhangJun
     * @createTime 2019-08-16
     * @description LocalDate 转 Date
     */
    public static Date localDateToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * {@link LocalDateTime} 转 {@link Date}
     * @param localDateTime {@link LocalDateTime}
     * @author ZhangJun
     * @createTime 2019-08-16
     * @description LocalDateTime 转 Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * {@link Date} 转 {@link LocalDateTime}
     * @param date {@link Date}
     * @author ZhangJun
     * @createTime 2019-08-16
     * @description Date 转 LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
    }

    /**
     * {@link Date} 转 {@link LocalDate}
     * @param date {@link Date}
     * @author ZhangJun
     * @createTime 2019-08-16
     * @description Date 转 LocalDate
     */
    public static LocalDate dateToLocalDate(Date date){
        return dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * 获取指定所属月的第一天
     * @param date {@link Date} 指定日期
     * @author ZhangJun
     * @createTime 2019-08-16
     * @description 获取指定所属月的第一天
     */
    public static Date firstDayOfMonth(Date date){
        LocalDate localDate = dateToLocalDate(date).with(TemporalAdjusters.firstDayOfMonth());
        return localDateToDate(localDate);
    }

    /**
     * 获取指定日期所属月的最后一天
     * @param date {@link Date} 指定日期
     * @author ZhangJun
     * @createTime 2019-08-16
     * @description 获取指定日期所属月的最后一天
     */
    public static Date lastDayOfMonth(Date date){
        LocalDate localDate = dateToLocalDate(date).with(TemporalAdjusters.lastDayOfMonth());
        return localDateToDate(localDate);
    }

    /**
     * 字符串转日期类型
     *
     * @param dateStr 日期字符串
     * @param formatStr 格式化表达式
     * @return Date
     */
    public static Date string2UtilDate(String dateStr, String formatStr) {
        if (null == formatStr) {
            formatStr = "yyyy-MM-dd";
        }
        DateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转日期类型
     *
     * @param dateStr 日期字符串
     * @return Date
     */
    public static Date string2UtilDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        String formatStr = "";
        if (dateStr.length() == 10) {
            formatStr = "yyyy-MM-dd";
        }
        if(StringUtils.isBlank(formatStr)){
            formatStr = DEFAULT_PATTERN;
        }
        return string2UtilDate(dateStr, formatStr);
    }

    /**
     * 将日期转换为带时间的字符串格式
     * @param date 日期
     * @return 字符串日期:yyyy-MM-dd HH:mm:ss
     * @author ZhangJun
     * @createTime 2018/3/14
     * @description 将日期转换为String格式
     */
    public static String convertDateToString(Date date){
        if(date == null){
            return null;
        }
        return convertDateToString(date,DEFAULT_PATTERN);
    }

    /**
     * 将日期转换为字符串格式
     * @param date 日期
     * @param pattern 格式
     * @return 字符串日期
     * @author ZhangJun
     * @createTime 2018/3/14
     * @description 将日期转换为String格式
     */
    public static String convertDateToString(Date date,String pattern){
        if(date==null){
            return null;
        }
        if(StringUtils.isBlank(pattern)){
            pattern = DEFAULT_PATTERN;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 获取日期(获取当天日期getDate(0))
     * @param day 天数
     * @return 返回增加day天后的日期
     * @author ZhangJun
     * @createTime 2018/3/15
     * @description 获取日期(获取当天日期getDate(0))
     */
    public static Date getDate(int day) {
        Calendar cal = getCalendar(new Date());
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取后几天日期
     * @param date 指定日期
     * @param day 增加的天数
     * @return 指定日期增加day天后的日期
     * @author ZhangJun
     * @createTime 2018/3/15
     * @description 获取后面第几天
     */
    public static Date getNextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 取得指定日期的Calendar对象
     * @param date 日期
     * @return 指定日期的Calendar对象
     * @author ZhangJun
     * @createTime 2018/3/15
     * @description 取得当前日期的Calendar对象
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 清除指定日期的时间
     *
     * @param date 带有时间的日期
     * @return 不带时间的日期
     * @author ZhangJun
     * @createTime 2018/7/12
     * @description 清除指定日期的时间，将传入的日期的时间部分设置为0，并返回结果
     */
    public static Date clearTime(Date date) {
        Calendar cal = getCalendar(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 比较两个日期是不是在同一个自然天内
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return true/false
     */
    public static boolean checkSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMdd");
        return formatTime.format(date1).equals(formatTime.format(date2));
    }

    /**
     * 比较两个日期是不是在同一个自然月内
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return true/false
     */
    public static boolean checkSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMM");
        return formatTime.format(date1).equals(formatTime.format(date2));
    }

    /**
     * 获取日期所属月的第一天 00:00:00
     * @param
     * @param
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            StringBuilder sb = new StringBuilder(sdf.format(date));
            sb.setLength(7);
            sb.append("-01");
            return sdf.parse(sb.toString());
        }catch (Exception e){
            return null;
        }
    }

    // 比较两个日期相差的天数 相对日期来查

    public static long getDistDatesExt(Date startDate, Date endDate) {
        long totalDate = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStartString = format.format(startDate);
        String dateEndString = format.format(endDate);
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        sb1.append(dateStartString).append(" 00:00:00");
        sb2.append(dateEndString).append(" 00:00:00");
        try {
            startDate = sdf.parse(sb1.toString());
            endDate = sdf.parse(sb2.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timestart = startDate.getTime();
        long timeend = endDate.getTime();
        totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
        System.out.println(startDate + " 与 " + endDate + "相差天数================" + totalDate);
        return totalDate;
    }

    public static class DateInterval {
        private Date startDate;
        private Date endDate;
        private List<Date> intervalDateList = new ArrayList<Date>(); //时间区间内的日期list

        private DateInterval(Date startDate, Date endDate) {
            super();
            this.startDate = startDate;
            this.endDate = endDate;
            this.init();
        }

        private void init() {
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("params error:startDate and endDate can not be null!    startDate和endDate参数不能为空");
            }
            if (startDate.after(endDate)) {
                throw new IllegalArgumentException("params error：startDate can not afeter endDate!please check your params!  startDate不能在endDate 之后");
            }
            intervalDateList.add(startDate);
            long distDatesExt = getDistDatesExt(startDate, endDate);
            Calendar c = Calendar.getInstance();
            for (int i = 1; i < distDatesExt; i++) {
                c.setTime(startDate);
                c.add(Calendar.DAY_OF_MONTH, i);
                intervalDateList.add(c.getTime());
            }
            intervalDateList.add(endDate);
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public List<Date> getIntervalDateList() {
            return intervalDateList;
        }

        /**
         * this区间是否与参数区间重叠
         *
         * @param dateInterval DateInterval对象
         * @return true/false
         */
        public boolean contain(DateInterval dateInterval) {
            List<Date> thisIntervalDate = this.getIntervalDateList();
            List<Date> otherIntervalDate = dateInterval.getIntervalDateList();
            for (Date thisD : thisIntervalDate) {
                for (Date otherD : otherIntervalDate) {
                    if (checkSameDay(thisD, otherD)) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * this区间与参数区间是否相同
         *
         * @param dateInterval DateInterval对象
         * @return true/false
         */
        public boolean checkSameEdge(DateInterval dateInterval) {
            return (checkSameDay(this.getStartDate(), dateInterval.getStartDate()) && checkSameDay(this.getEndDate(), dateInterval.getEndDate()));
        }

        /**
         * 判断两个DateInterval单元的首尾端点是否相隔指定时间
         *
         * @param dateInterval DateInterval对象
         * @param intervalDay  两个日期相隔几天
         * @return true/false
         */
        public boolean edgeInvCheck(DateInterval dateInterval, int intervalDay) {
            if (getDistDatesExt(this.getEndDate(), dateInterval.getStartDate()) < intervalDay) {
                return false;
            }

            if (getDistDatesExt(this.getStartDate(), dateInterval.getEndDate()) < intervalDay) {
                return false;
            }
            return true;
        }
    }

    public static void print(List<DateInterval> list) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("print begin=======================================");
        for (DateInterval di : list) {
            for (Date d : di.getIntervalDateList()) {
                System.out.println("di:" + df.format(d));
            }
        }
        System.out.println("print end========================================");
    }

    /***
     * 判断时间区间是否重叠
     *
     * @param listTocheck 源有时间区间list
     * @param diToCheck   需要检验的时间区间
     * @return true/false
     */
    public static boolean checkListContain(List<DateInterval> listTocheck, DateInterval diToCheck) {
        boolean checkFlag = false;
        for (DateInterval di : listTocheck) {
            System.out.println("是否同一区间checkSameEdge:" + diToCheck.checkSameEdge(di));
            System.out.println("是否包含 contain:" + diToCheck.contain(di));
            System.out.println("首尾端点是否相隔指定n天 edgeInvCheck:" + diToCheck.edgeInvCheck(di, 1));
            System.out.println("unit check ret:" + (diToCheck.checkSameEdge(di) || (!diToCheck.contain(di) && diToCheck.edgeInvCheck(di, 1))));
            if (diToCheck.contain(di)) {
                checkFlag = true;
            }
        }
        return checkFlag;
    }

    /**
     * 判断两个时间区间是否重叠
     *
     * @param startDate1 区间1起始日期
     * @param endDate1   区间1终止日期
     * @param startDate2 区间2起始日期
     * @param endDate2   区间2终止日期
     * @return true重叠，false不重叠
     */
    public static boolean checkContain(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        if (endDate1 == null) {
            endDate1 = string2UtilDate("2099-12-31", null);
        }
        if (endDate2 == null) {
            endDate2 = string2UtilDate("2099-12-31", null);
        }
        DateInterval di = new DateInterval(startDate1, endDate1);
        DateInterval di2 = new DateInterval(startDate2, endDate2);
        System.out.println(startDate1 + " 到 " + endDate1 + " 与 " + startDate2 + " 到 " + endDate2 + " 是否重叠======= " + di2.contain(di));
        return di2.contain(di);
    }

    /**
     * 获取两个月份区间中的中间月份
     *
     * @param oldMonth 201602
     * @param newMonth 201605
     * @return {201603,201604}
     * @throws ParseException 转换异常
     */
    public static List getFrom2Month(String oldMonth, String newMonth) throws ParseException {
        List list = new ArrayList();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Date oldDate = df.parse(oldMonth);
        Date newDate = df.parse(newMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.MONTH, 1);
        oldDate = calendar.getTime();
        while (oldDate.compareTo(newDate) < 0) {

            String s = df.format(oldDate);
            list.add(s);
            calendar.add(Calendar.MONTH, 1);
            oldDate = calendar.getTime();
        }

        return list;
    }

    /**
     * 比较两个时间的大小
     * @param day1Str 时间字符串1
     * @param day2Str 时间字符串2
     * @return -1：day1Str < day2Str;
     *          0: day1Str = day2Str;
     *          1: day1Str > day2Str;
     * @throws ParseException 抛出异常
     */
    public static int compare2Days(String day1Str, String day2Str, String pattern) throws ParseException {
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        if (day2Str == null) {
            day2Str = convertDateToString(new Date());
        }
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        //将字符串形式的时间转化为Date类型的时间
        Date day1 = sdf.parse(day1Str);
        Date day2 = sdf.parse(day2Str);
        //Date类的一个方法，如果a早于b返回true，否则返回false
        return day1.compareTo(day2);
    }

    /**
     * 获取"yyyyMMddHHmmss"的格式字符串
     * @return
     */
    public static String getDateSeq() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String mDateTime = formatter.format(cal.getTime());
        return mDateTime;
    }


    /**
     * 获取指天日期所属月的最后几天
     * @param date 指定日期
     * @param days 返回最后days天
     * @author ZhangJun
     * @createTime 2019-06-03
     * @description 获取指天日期所属月的最后几天
     */
    public static List<Date> getMonthLastDays(Date date,int days){

        List<Date> retDates = new LinkedList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(days<=0){
            days = 1;
        }

        for(int i=days;i>0;i--){
            cal.add(Calendar.MONTH,1);
            cal.set(Calendar.DAY_OF_MONTH,1);
            cal.add(Calendar.DAY_OF_MONTH,-i);
            retDates.add(cal.getTime());
        }

        return retDates;
    }

    /**
     * 获取指定日期所属月的前days天
     * @param date 指定日期
     * @param days 返回前days天
     * @author ZhangJun
     * @createTime 2019-06-03
     * @description 获取指定日期所属月的前days天
     */
    public static List<Date> getMonthFirstDays(Date date,int days){

        List<Date> retDates = new LinkedList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        retDates.add(cal.getTime());

        if(days<=0){
            days = 1;
        }

        for(int i=1;i<days;i++){
            cal.add(Calendar.DAY_OF_MONTH,1);
            retDates.add(cal.getTime());
        }

        return retDates;
    }


    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(2019,4,10);
        List<Date> monthLastDays = getMonthFirstDays(cal.getTime(), 5);
        for (int i = 0, size = monthLastDays.size(); i < size; i++) {
            Date date1 =  monthLastDays.get(i);
            System.err.println(SaafDateUtils.convertDateToString(date1,"yyyy-MM-dd"));
        }

    }

}
