package com.tangly.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * Created by tangly on 2018/4/15.
 */
public class TimeUtil {

    /**
     * 毫秒数转化为累计时长
     *
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }

    /**
     * 计算两个时间戳的时差
     * @param nowTime 新的时间
     * @param beforeTime 远的时间
     * @return
     */
    public static int compareTimeDiffSeconds(long nowTime , long beforeTime){
        return  (int) ((nowTime - beforeTime)/(1000));
    }

    /**
     * 获取制定格式的时间
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

}
