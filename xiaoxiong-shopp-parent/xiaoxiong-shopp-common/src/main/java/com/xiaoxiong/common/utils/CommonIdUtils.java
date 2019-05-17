package com.xiaoxiong.common.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 生成唯一的订单号，不可重复
 */
public class CommonIdUtils {
    public static String generate() {
        String time = DateFormatUtils.format(System.currentTimeMillis(), "yyMMddHHmmssSSS");//15个
        time += RandomStringUtils.randomNumeric(5);
        return time;
    }
}
