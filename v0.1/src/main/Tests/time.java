import org.junit.jupiter.api.Test;

import java.awt.image.SampleModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class time {
    @Test
    public void Test() {
        java.util.Date date = new Date();
        System.out.println(date.getTime());
    }

    @Test
    public void Test1() {
        int year = 2003;
        int month = 11;
        int day = 24;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String str = String.valueOf(year) + "-" + month + "-" + day;
        try {
            Date d = df.parse(str);
            System.out.println(df.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


@Test
public void Test2() {
        int year = 2003;
        int month = 11;
        int day = 34;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String str = String.valueOf(year) + "-" + month + "-" + day;
        try {
            Date d = df.parse(str);
            System.out.println(df.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
