package cardio.uff.cardio.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Date;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.model.model.Consulta;
import cardio.uff.cardio.common.util.Formater;

public class AlarmForAppointment {

    public final static String PARAM_ID = "id";
    public final static String PARAM_NOTIFICATION_ID = "notificationId";
    public final static String PARAM_DATE = "date";
    public final static String PARAM_TITLE = "title";
    public final static String PARAM_CONTENT = "content";
    public final static String PARAM_ATTENDED = "attended";

    private AlarmManager alarmMgr;

    public void createAlarm (Context context, Consulta appointment){
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        String title = context.getResources().getString(R.string.message_title_monitoring_appointment,
                    appointment.getEspecialideProfissional(),
                    Formater.getStringFromDate(new Date(appointment.getData())));

        String content = context.getResources().getString(R.string.message_content_monitoring_appointment,
                appointment.getEspecialideProfissional(),
                Formater.getTimeStringFromDate(new Date(appointment.getData())),
                appointment.getLocalizacao());

        Intent intent = new Intent(context, AppointmentBroadcastReceiver.class);
        intent.putExtra(PARAM_ID, appointment.getId());
        intent.putExtra(PARAM_NOTIFICATION_ID, appointment.getNotificationId());
        intent.putExtra(PARAM_DATE, appointment.getData());
        intent.putExtra(PARAM_TITLE, title);
        intent.putExtra(PARAM_CONTENT, content);
        intent.putExtra(PARAM_ATTENDED, appointment.isAttended());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, appointment.getNotificationId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Como a consulta é apenas um dia, se cria um alarme sem repetição
        alarmMgr.set(AlarmManager.RTC_WAKEUP, appointment.getData() - 24*60*60*1000, alarmIntent);
    }

}
