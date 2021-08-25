package com.tmb.oneapp.lendingservice.util;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommonServiceUtils {
    private static final TMBLogger<CommonServiceUtils> logger = new TMBLogger<>(CommonServiceUtils.class);

    private CommonServiceUtils() {

    }

    public static String getDateAndTimeInYYYYMMDDHHMMSS(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return formatter.format(date);

    }

    public static String getDateAndTimeInYYMMDDHHMMSS(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        return formatter.format(date);

    }

    public static String getDateAndTimeInYYYYMMDDHHMMSS() {
        return getDateAndTimeInYYYYMMDDHHMMSS(new Date());
    }

    public static String formatPhoneNumber(String mobNo) {

        if (StringUtils.isNotBlank(mobNo) && mobNo.length() == 10) {
            StringBuilder formattedMobNo = new StringBuilder(mobNo);
            formattedMobNo.insert(3, "-");
            formattedMobNo.insert(7, "-");

            return formattedMobNo.toString();

        }
        return "";
    }

    public static String formatCustomerId(String customerId) {

        if (StringUtils.isNotBlank(customerId)) {
            StringBuilder formatCustomerId = new StringBuilder(customerId);
            formatCustomerId.insert(1, "-");
            formatCustomerId.insert(6, "-");
            formatCustomerId.insert(12, "-");
            formatCustomerId.insert(15, "-");
            return formatCustomerId.toString();

        }
        return "";
    }

    public static String getThaiYear(String year) {
        logger.info("Thai year is {} :", year);
        return String.valueOf(Integer.parseInt(year) + 543);
    }

    public static String getThaiMonth(String month) {
        String[] thaiMonths = {
                "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน",
                "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม",
                "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
        return thaiMonths[Integer.parseInt(month) - 1];
    }

    public static String getRmId(String crmId) throws TMBCommonException {
        try {
            String rmId = new StringBuilder(new StringBuilder(crmId).reverse().substring(0, 14)).reverse().toString();
            logger.info("rmId : {}", rmId);
            return rmId;
        }catch (Exception e) {
            throw new TMBCommonException(ResponseCode.INVALID_DATA.getCode(), "invalid crmId", ResponseCode.INVALID_DATA.getService(), HttpStatus.BAD_REQUEST, e);
        }
    }

    public static long validateCaId(String caId) throws TMBCommonException {
        try {
            return Long.parseLong(caId);
        }catch (Exception e) {
            throw new TMBCommonException(ResponseCode.INVALID_DATA.getCode(), "invalid caId", ResponseCode.INVALID_DATA.getService(), HttpStatus.BAD_REQUEST, e);
        }
    }

    public static List<String> parseStringToList(String listString) throws TMBCommonException {
        try {
            String[] split = listString.split(",");
            return Arrays.asList(split);
        }catch (Exception e) {
            throw new TMBCommonException(ResponseCode.INVALID_DATA.getCode(), "invalid listString", ResponseCode.INVALID_DATA.getService(), HttpStatus.BAD_REQUEST, e);
        }
    }
    
    public static  String getThaiDate(String dateEng) {
        if (StringUtils.isBlank(dateEng))
            return "";
        String[] dateArray = dateEng.split("-");
        String thaiYear = getThaiYear(dateArray[0]);
        String thaiMonth = getThaiMonth(dateArray[1]);
        StringBuilder thaiDate = new StringBuilder();
        thaiDate.append(dateArray[2]);
        thaiDate.append(LendingServiceConstant.SPACE);
        thaiDate.append(thaiMonth);
        thaiDate.append(LendingServiceConstant.SPACE);
        thaiDate.append(thaiYear);
        return thaiDate.toString();
    }

}
