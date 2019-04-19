package cardio.uff.cardio.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.model.model.Medicamento;
import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.patiente.activities.MainActivityPatient;

public class AlarmForMedication {

        public final static String PARAM_ID = "id";
        public final static String PARAM_NOTIFICATION_ID = "notificationId";
        public final static String PARAM_TITLE = "title";
        public final static String PARAM_CONTENT = "content";
        public final static String PARAM_HORARIOS = "horarios";

        private AlarmManager alarmMgr;

        public void createAlarm (Context context, Recomentation recomentation) throws ParseException {
            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Medicamento medicamento = (Medicamento) recomentation.getAction();

            String title = context.getResources().getString(R.string.message_title_monitoring_medication,
                    medicamento.getName());

            String content = context.getResources().getString(R.string.message_content_monitoring_medication,
                    medicamento.getQuantidade(),
                    medicamento.getDosagem(),
                    medicamento.getName());

            Intent intent = new Intent(context, MedicationBroadcastReceiver.class);
            intent.putExtra(PARAM_ID, recomentation.getId());
            intent.putExtra(PARAM_NOTIFICATION_ID, recomentation.getNotificationId());
            intent.putExtra(PARAM_TITLE, title);
            intent.putExtra(PARAM_CONTENT, content);
            intent.putStringArrayListExtra(PARAM_HORARIOS, new ArrayList<>(Arrays.asList(medicamento.getHorarios())));

            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, recomentation.getNotificationId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //O Alarme precisa ser preciso e se repetir em um certo intervalo
            Date initialTime = Formater.getDateFromStringDateAndTime(
                    Formater.getStringFromDate(new Date(recomentation.getStartDate())),
                    medicamento.getHorario()
            );
            long dayInMiliseconds = 24 * 60 * 60 * 1000;
            long interval = dayInMiliseconds/recomentation.getFrequencyByDay();


            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, initialTime.getTime(), interval, alarmIntent);
        }

    public void cancell (Context context, int id){
        Intent intent = new Intent(context, MainActivityPatient.class);
        PendingIntent alarmIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
    }
}
