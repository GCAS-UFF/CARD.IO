package cardio.uff.cardio.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.util.NotificationUtils;
import cardio.uff.cardio.patiente.activities.MainActivityPatient;

public class AppointmentBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String id = intent.getStringExtra(AlarmForAppointment.PARAM_ID);
        int notificationId = intent.getIntExtra(AlarmForAppointment.PARAM_NOTIFICATION_ID, 0);
        long date = intent.getLongExtra(AlarmForAppointment.PARAM_DATE, 0);
        String title = intent.getStringExtra(AlarmForAppointment.PARAM_TITLE);
        String content = intent.getStringExtra(AlarmForAppointment.PARAM_CONTENT);
        boolean attended = intent.getBooleanExtra(AlarmForAppointment.PARAM_ATTENDED, false);


        if (!attended) {
            Intent clickIntent = new Intent(context, MainActivityPatient.class);
            clickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            clickIntent.putExtra("trocaTelaHome", R.id.ll_consultas);
            clickIntent.putExtra("id", id);
            clickIntent.putExtra("date", date);

            NotificationUtils.getInstance().showNotification(context, title, content, clickIntent, (notificationId - 5));
        }
    }
}
