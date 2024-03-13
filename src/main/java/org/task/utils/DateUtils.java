package org.task.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateUtils {

    private final static String[] DATE_PATTERNS = {
        "yyyy-MM-dd",
        "dd/MM/yyyy",
        "dd-MM-yyyy",
        "dd.MM.yyyy",

        "d.M.yyyy",
        "d.MM.yyyy",
        "dd.M.yyyy",
        "d-M-yyyy",
        "d-MM-yyyy",
        "dd-M-yyyy",
        "d/M/yyyy",
        "d/MM/yyyy",
        "dd/M/yyyy"
    };

    public static LocalDate parseDate(Object obj, boolean allowNull) throws Exception {
        LocalDate date = null;
        if(allowNull && ((String)obj).trim().toUpperCase().equals("NULL")){
            return LocalDate.now();
        }else {
            for(String pattern : DATE_PATTERNS){
                try{
                    return LocalDate.parse((String)obj, DateTimeFormatter.ofPattern(pattern));
                }catch(Exception ex){

                }
            }
            return date;
        }
    }
}
