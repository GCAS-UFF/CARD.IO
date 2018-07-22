package cardio.com.cardio.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

}
