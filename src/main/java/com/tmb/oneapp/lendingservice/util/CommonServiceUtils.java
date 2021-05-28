package com.tmb.oneapp.lendingservice.util;

import com.tmb.common.logger.TMBLogger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonServiceUtils {
    private static final TMBLogger<CommonServiceUtils> logger = new TMBLogger<>(CommonServiceUtils.class);

    private CommonServiceUtils() {

    }

    public static String getDateAndTimeInYYYYMMDDHHMMSS() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);

    }
}
