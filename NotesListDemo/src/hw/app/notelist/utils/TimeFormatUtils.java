package hw.app.notelist.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeFormatUtils {
    private static Calendar mCalendar = Calendar.getInstance();

    /**
     * 获取当前时间 格式："MM-dd hh:mm:ss"
     */
    public synchronized static String getNowTimeString() {
        mCalendar.setTime(new Date(System.currentTimeMillis()));
        StringBuffer sb = new StringBuffer();

        int nMonth = mCalendar.get(Calendar.MONTH) + 1;
        sb.append(nMonth < 10 ? "0" + nMonth : nMonth);
        sb.append("-");

        int nDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        sb.append((nDay < 10) ? "0" + nDay : nDay);
        sb.append(" ");

        sb.append(mCalendar.get(Calendar.HOUR_OF_DAY) + ":");
        sb.append(mCalendar.get(Calendar.MINUTE) + ":");
        sb.append(mCalendar.get(Calendar.SECOND) + " ");

        return sb.toString();
    }

    /**
     * 获取当前年月日 格式："yyyy-MM-dd"
     */
    public static String getNowDateString() {
        mCalendar.setTime(new Date(System.currentTimeMillis()));
        StringBuffer sb = new StringBuffer();

        int nYear = mCalendar.get(Calendar.YEAR);
        sb.append(nYear);
        sb.append("-");

        int nMonth = mCalendar.get(Calendar.MONTH) + 1;
        sb.append(nMonth < 10 ? "0" + nMonth : nMonth);
        sb.append("-");

        int nDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        sb.append((nDay < 10) ? "0" + nDay : nDay);

        return sb.toString();
    }
    
    public static String TimeStamp2Date(Long timestamp, String formats){    
//      Long timestamp = Long.parseLong(timestampString)*1000;    
      String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));    
      return date;    
    }  

    /**
     * 比较日期是否早于现在
     * 
     * @param timeString 日期文字
     * @param timeFormat 日期格式"yyyy-MM-dd"
     * @return
     */
    public static boolean beforeDate(String timeString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date d = sdf.parse(timeString);
            return beforeDate(d);
        } catch (ParseException e) {
            // TODO Auto-generated catch block

        }

        return false;
    }

    /**
     * 比较日期是否早于现在
     * 
     * @param date
     * @return
     */
    public static boolean beforeDate(Date date) {
        String currentTime = getNowDateString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date nowdate = sdf.parse(currentTime);
            return date.before(nowdate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;

    }

    private TimeFormatUtils() {
    };
}
