package com.mobdeve.s13.g4.taskmanagement.database;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateDataHelper {
    public static List<Calendar> generateDatesForMonth(int year, int month) {
        List<Calendar> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for( int i = 1; i <= daysInMonth; i++ ) {
            Calendar date = (Calendar) calendar.clone();
            dates.add(date);
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }
}