package com.ruoyi.common.utils.uuid;

import java.security.SecureRandom;
import java.util.Date;

/**
 * 生成编号工具
 */
public class GenerateNumberUtils {

    /**
     * 生成仓库编号
     *
     * @return
     */
    public static String generateNumber() {
        Date curDate = new Date();
        return new java.text.SimpleDateFormat("yyyyMMdd").format(curDate) + (100+(new SecureRandom()).nextInt(900));
    }

    /**
     * 生成汇款识别码
     *
     * @param orderNumber
     * @return
     */
    public static String generateRemitCode(String orderNumber) {
        String remitCode =
                UUID.randomUUID().toString().toUpperCase().substring(0, 2) + orderNumber.substring(orderNumber.length() - 6, orderNumber.length());
        return remitCode;
    }
}
