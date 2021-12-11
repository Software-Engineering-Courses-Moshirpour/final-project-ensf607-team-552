package com.utils;

import javax.servlet.http.HttpServletRequest;

public class UrlUtility {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
