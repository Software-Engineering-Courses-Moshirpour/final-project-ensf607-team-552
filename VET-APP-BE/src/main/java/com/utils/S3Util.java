package com.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class S3Util {

    public static String getDateFilename(String fileName){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String dateStr = fmt.format(cal.getTime());
        String newName = FilenameUtils.removeExtension(fileName) + dateStr + "." + StringUtils.getFilenameExtension(fileName);
        return newName;
    }

    public static String getTickFilename(String fileName){
        long dateStr = System.currentTimeMillis();
        String newName = FilenameUtils.removeExtension(fileName) + dateStr + "." + StringUtils.getFilenameExtension(fileName);
        return newName;
    }
}
