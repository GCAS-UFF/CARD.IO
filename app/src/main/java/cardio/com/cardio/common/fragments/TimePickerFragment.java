package cardio.com.cardio.common.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TimePicker;

import java.util.Calendar;

import cardio.com.cardio.professional.fragments.PrescribeMedicineDialogFragment;

@SuppressLint("ValidFragment")
public class TimePickerFragment  extends PrescribeMedicineDialogFragment implements TimePickerDialog.OnTimeSetListener {

    private ComunicatorTimePicker comunicatorTimePicker;

    @SuppressLint("ValidFragment")
    public TimePickerFragment(ComunicatorTimePicker comunicatorTimePicker) {
        this.comunicatorTimePicker = comunicatorTimePicker;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getContext(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hourDayString;
        if (hourOfDay < 10) hourDayString = "0" + hourOfDay;
        else hourDayString = String.valueOf(hourOfDay);

        String minuteString;
        if (minute < 10) minuteString = "0" + minute;
        else minuteString = String.valueOf(minute);

        String time = hourDayString + ":" + minuteString;

        comunicatorTimePicker.setTime(time);
    }

    public interface ComunicatorTimePicker{
        void setTime (String timeString);
    }
}