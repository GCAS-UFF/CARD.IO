package cardio.com.cardio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formater {
    public static Date getDateFromString (String dateStr) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.parse(dateStr);
    }
}
