package com.university.schedule.Utils;

public class Utils {

    public static String cutTime(String dateTime){
        String[] res = dateTime.split(" ");
        return res[0];
    }
}
