package utils;

import java.sql.Date;

public class DateUtilToSql {
    public static Date conv(java.util.Date utilDate) {
        if (utilDate == null)
            return null;
        long milliseconds = utilDate.getTime();
        return new Date(milliseconds);
    }

    public static java.util.Date reconv(Date sqlDate) {
        if (sqlDate == null)
            return null;
        return new java.util.Date(sqlDate.getTime());
    }
}