package com.example.hotelappwithhibernate;

import javafx.util.StringConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToStringConverter extends StringConverter<Date> {
    @Override
    public String toString(Date d) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(d);
    }

    @Override
    public Date fromString(String s) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = df.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
