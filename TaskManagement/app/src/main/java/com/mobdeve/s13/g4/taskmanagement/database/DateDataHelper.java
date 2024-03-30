package com.mobdeve.s13.g4.taskmanagement.database;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateDataHelper {
    public static List<Calendar> generateDates() {
        List<Calendar> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 1);

        for( int i = 0; i < 31; i++ ) {
            Calendar date = (Calendar) calendar.clone();
            dates.add(date);
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }
}