package com.zte.msg.pushcenter.pccore.utils;

import java.util.regex.Pattern;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/14 16:52
 */
public class Constants {

    public static final Pattern SMS_CONTENT_PATTERN_1 = Pattern.compile("\\{(.*?)}");

    public static final Pattern SMS_CONTENT_PATTERN_2 = Pattern.compile("#(.*?)#");



    public static final String AUTHORIZATION = "Authorization";

    public static int DATA_NOT_DELETED = 0;

    public static String ZTE_NAME = "zte";

    public static final String OPENAPI_URL = "/api/openapi/v1/";

    public static final Integer NOT_RELATED = 0;

    public static final Integer ALREADY_RELATED = 1;
}
