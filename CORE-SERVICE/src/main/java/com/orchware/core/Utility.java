package com.orchware.core;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Utility {

    public static String randomAlphanumeric(int limit) {
        return RandomStringUtils.randomAlphanumeric(limit);
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //create instance of the Calendar class and set the date to the given date
        Calendar cal = Calendar.getInstance();

        System.out.println(sdf.format(cal.getTime())+" is the date before adding days");

        // use add() method to add the days to the given date
        cal.add(Calendar.DAY_OF_MONTH, 2);
        String dateAfter = sdf.format(cal.getTime());

        //date after adding three days to the given date
        System.out.println(dateAfter+" is the date after adding 3 days.");

//        System.out.println(addDays(new Date(), 2));
    }

    public static Date addDays(Date date, Integer days) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusDays(days);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date addMinutes(Date date, Integer minutes) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusMinutes(minutes);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
