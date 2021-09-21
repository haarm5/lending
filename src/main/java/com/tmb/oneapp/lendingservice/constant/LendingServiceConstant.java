package com.tmb.oneapp.lendingservice.constant;


/**
 * Constant class for Application
 *
 */
public class LendingServiceConstant {

    /**
     * created private constructor so that no body can create object of this call
     */
    private LendingServiceConstant() {
    }

    public static final String INITIALIZE_SSL_CONTEXT = "[initializeSSLContext] ";
    public static final String EXCEPTION_OCCURED = "Exception occured : {}";

    /**
     * REQUEST HEADERS
     */
    public static final String HEADER_X_FORWARD_FOR = "x-forward-for";
    public static final String HEADER_OS_VERSION = "os-version";
    public static final String HEADER_CHANNEL = "channel";
    public static final String HEADER_APP_VERSION = "app-version";
    public static final String HEADER_X_CRMID = "x-crmid";
    public static final String HEADER_DEVICE_ID = "device-id";
    public static final String HEADER_DEVICE_MODEL = "device-model";

    public static final String HEADER_SOAP_ACTION = "SOAPAction";
    public static final String HEADER_CORRELATION_ID = "x-correlation-id";
    public static final String HEADER_ACCOUNT_ID = "account-id";

    /**
     * ACTIVITY IDS
     */
    public static final String FINISH_BLOCK_CARD_ACTIVITY_ID = "00700402";

    /**
     * ACTIVITY LOGGING FIELDS
     */
    public static final String ACTIVITY_SCREEN_NAME_TUTORIAL_CST = "tutorial case tracking";

    /**
     * Response
     */
    public static final String DATA_NOT_FOUND = "DATA NOT FOUND";
    public static final String INVALID_DATA = "INVALID DATA";

    public static final String HEADER_TIMESTAMP = "Timestamp";
    public static final String UNDER_SCORE = "_";
    public static final String SPACE = " ";
    public static final String SEPARATOR = "/";
}
