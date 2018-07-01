package cardio.com.cardio.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

@SuppressLint("ValidFragment")
public class DataPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private ComunicatorDataPicker comunicatorDataPicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getContext(), this, year, month, day);
    }

    @SuppressLint("ValidFragment")
    public DataPickerFragment(ComunicatorDataPicker comunicatorDataPicker) {
        this.comunicatorDataPicker = comunicatorDataPicker;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String dayString;
        if (dayOfMonth < 10) dayString = "0" + dayOfMonth;
        else dayString = String.valueOf(dayOfMonth);

        String monthString;
        if (++month < 10) monthString = "0" + month;
        else monthString = String.valueOf(month);

        String datestring = dayString + "/" + monthString + "/" + String.valueOf(year);

        comunicatorDataPicker.setDate(datestring);

    }

    public interface ComunicatorDataPicker{
        void setDate (String dataString);
    }
}
