package cardio.uff.cardio.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.NotificationUtils;
import cardio.uff.cardio.patiente.activities.MainActivityPatient;

public class MedicationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String id = intent.getStringExtra(AlarmForMedication.PARAM_ID);
        int notificationId = intent.getIntExtra(AlarmForMedication.PARAM_NOTIFICATION_ID, 0);
        long date = (new Date()).getTime();
        String title = intent.getStringExtra(AlarmForMedication.PARAM_TITLE);
        String content = intent.getStringExtra(AlarmForMedication.PARAM_CONTENT);
        ArrayList<String> horarios = intent.getStringArrayListExtra(AlarmForMedication.PARAM_HORARIOS);

        Date currentDate = new Date();
        String currenteDateStr = Formater.getStringFromDate(currentDate);

        for (String horario : horarios){
            Log.d("debug_kelly", " " + horario);
            try {
                Date dateToFilter = Formater.getDateFromStringDateAndTime(currenteDateStr, horario);
                if (Formater.compareDatesWithoutSeconds(currentDate, dateToFilter) ==0){

                    title = title.concat(" às " + horario);

                    Intent clickIntent = new Intent(context, MainActivityPatient.class);
                    clickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    clickIntent.putExtra("trocaTelaHome", R.id.ll_medicacoes);
                    clickIntent.putExtra("id", id);
                    clickIntent.putExtra("date", date);

                    NotificationUtils.getInstance().showNotification(context, title, content, clickIntent, (notificationId-5));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
