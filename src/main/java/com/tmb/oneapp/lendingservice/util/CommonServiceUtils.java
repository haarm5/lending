package com.tmb.oneapp.lendingservice.util;

import com.tmb.common.logger.TMBLogger;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonServiceUtils {
    private static final TMBLogger<CommonServiceUtils> logger = new TMBLogger<>(CommonServiceUtils.class);

    private CommonServiceUtils() {

    }

    public static String getDateAndTimeInYYYYMMDDHHMMSS(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return formatter.format(date);

    }
    public static String getDateAndTimeInYYYYMMDDHHMMSS() {
        return getDateAndTimeInYYYYMMDDHHMMSS(new Date());
    }

    public static String formatPhoneNumber(String mobNo){

        if(StringUtils.isNotBlank(mobNo) && mobNo.length() == 10){
            StringBuilder formattedMobNo = new StringBuilder(mobNo);
            formattedMobNo.insert(3,"-");
            formattedMobNo.insert(7,"-");

            return formattedMobNo.toString();

        }
        return "";
    }

    public static String formatCustomerId(String customerId){

        if(StringUtils.isNotBlank(customerId)){
            StringBuilder formatCustomerId = new StringBuilder(customerId);
            formatCustomerId.insert(1,"-");
            formatCustomerId.insert(6,"-");
            formatCustomerId.insert(12,"-");
            formatCustomerId.insert(15,"-");
            return formatCustomerId.toString();

        }
        return "";
    }

    public static String getThaiYear(String year){
        logger.info("Thai year is {} :",year);
        return String.valueOf(Integer.parseInt(year) + 543);
    }

    public static String getThaiMonth(String month){
        String[] thaiMonths = {
                "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
                "พฤษภาคม", "มิถุนายน" ,"กรกฎาคม", "สิงหาคม",
                "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
        return thaiMonths[Integer.parseInt(month)-1];
    }
}
