package cardio.com.cardio.common.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cardio.com.cardio.common.model.view.CustomMapObject;
import cardio.com.cardio.common.model.view.CustomMapsList;

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

    public static Date getDateFromStringDateAndTime(String dateStr, String timeStr) throws ParseException {

        String result = dateStr + " " + timeStr;

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return fmt.parse(result);
    }

    public static String getTimeStringFromDate (Date date){

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String resultStr = fmt.format(date);

        return resultStr.substring(resultStr.length() - 6,resultStr.length());
    }

    private static Date getDateWithoutSeconds (Date date) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = formato.format(date);
        return formato.parse(dateStr);
    }

    public static int compareDates (Date date1, Date date2) throws ParseException {
        Date date1Formated = getDateWithoutSeconds(date1);
        Date date2Formated = getDateWithoutSeconds(date2);

        if (date1Formated.getTime() == date2Formated.getTime())
            return 0;
        else if (date1Formated.getTime() < date2Formated.getTime())
            return -1;
        else
            return 1;
    }

    public static int compareDateWithCurrentDate (Date date1) throws ParseException {
        return compareDates(new Date(), date1);
    }

    public static boolean containsInMapsLists (String key, List<CustomMapsList> customMapsLists){
        for (CustomMapsList mCustomMapsList: customMapsLists) {
            if (mCustomMapsList.getTitle().equals(key)) return true;
        }

        return false;
    }

    public static void addIntoMapsLists (String key, CustomMapObject customMapObject, List<CustomMapsList> customMapsLists) {
        for (CustomMapsList mCustomMapsList: customMapsLists) {
            if (mCustomMapsList.getTitle().equals(key)) {
                mCustomMapsList.getCustomMapObjectList().add(customMapObject);
            };
        }
    }

    public static void sortCustomMapListsWhereTitleIsDate (List<CustomMapsList> customMapsLists){
        Collections.sort(customMapsLists, new Comparator<CustomMapsList>() {
            @Override
            public int compare(CustomMapsList r1, CustomMapsList r2) {
                try {
                    return Formater.compareDates(Formater.getDateFromString(r1.getTitle()),
                            Formater.getDateFromString(r2.getTitle()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return -1;
            }
        });
    }
}
