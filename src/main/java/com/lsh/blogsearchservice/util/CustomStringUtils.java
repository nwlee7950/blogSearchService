package com.lsh.blogsearchservice.util;

import java.util.Map;

public class CustomStringUtils {

    public static String mapToUrlParam(Map<String, Object> params) {
        StringBuffer paramData = new StringBuffer();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (paramData.length() != 0) {
                paramData.append('&');
            }
            paramData.append(param.getKey());
            paramData.append('=');
            paramData.append(param.getValue());
        }
        return paramData.toString();
    }
}
