package com.hzih.mmcs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-8
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

    /**
     * 转时间Date类型成为format
     * @param date       需要转的时间
     * @param format     转成的格式
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
