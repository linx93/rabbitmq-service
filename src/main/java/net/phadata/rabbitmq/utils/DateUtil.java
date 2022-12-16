package net.phadata.rabbitmq.utils;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author felix
 */
public class DateUtil {
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");

    /**
     * 获取当前时间戳 单位秒
     *
     * @return 时间戳
     */
    public static long getSecond() {
        return Instant.now(Clock.system(ZONE_ID)).getEpochSecond();
    }


    /**
     * 获取当前时间戳 单位毫秒
     *
     * @return 时间戳
     */
    public static long getMill() {
        return Instant.now(Clock.system(ZONE_ID)).toEpochMilli();
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalDateTime localDateTime() {
        return LocalDateTime.now(ZONE_ID);
    }

    public static void main(String[] args) {
        System.out.println(getSecond());
        System.out.println(getMill());
    }
}
