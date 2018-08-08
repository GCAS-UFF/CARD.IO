package cardio.com.cardio.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formater {
    public static Date getDateFromString (String dateStr) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.parse(dateStr);
    }

    public static Float getFloatFromString(String number){
        try {
            return Float.parseFloat(number);
        } catch (Exception e){
            e.printStackTrace();
            return 0.0f;
        }
    }

    public static int getIntegerFromString(String number){
        try {
            return Integer.parseInt(number);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static long getLongFromString(String number){
        try{
            return Long.parseLong(number);
        }catch (Exception e){
            e.printStackTrace();
            return 0l;
        }
    }

    public static String getStringFromDate(Date date){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(date);
    }

    public static Date getDateFronStringDateAndTime (String dateStr, String timeStr) throws ParseException {

        String result = dateStr + " " + timeStr;

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return fmt.parse(result);
    }

    public static String getTimeStringFromDate (Date date){

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String resultStr = fmt.format(date);

        return resultStr.substring(resultStr.length() - 6,resultStr.length());
    }

    public static Date getCurrentDateWithoutSeconds() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = formato.format(new Date());
        return formato.parse(dateStr);
    }
}
