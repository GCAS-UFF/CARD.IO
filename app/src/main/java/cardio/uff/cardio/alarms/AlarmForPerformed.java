package cardio.uff.cardio.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import cardio.uff.cardio.common.activities.SplashActivity;

public class AlarmForPerformed {

    private final static int HOUR_OF_FIRST_ALARM = 10;
    private final static int HOUR_OF_SECOND_ALARM = 16;
    private final static int HOUR_OF_THIRD_ALARM = 20;

    private final static int FIRST_ALARM_ID = 230;
    private final static int SECOND_ALARM_ID = 252;
    private final static int THRIRD_ALARM_ID = 345;

    private AlarmManager alarmMgr;

    public void start(Context context){
        createAlarm(context, HOUR_OF_FIRST_ALARM, FIRST_ALARM_ID);
        createAlarm(context, HOUR_OF_SECOND_ALARM, SECOND_ALARM_ID);
        createAlarm(context, HOUR_OF_THIRD_ALARM, THRIRD_ALARM_ID);
    }

    private void createAlarm (Context context, int hourOfDay, int id){
            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            Intent intent = new Intent();
//            intent.setAction("cardio.uff.PERFORMED_INTENT");
//            intent.setClass(context, PerformedBroadcastReceiver.class);

            Intent intent = new Intent(context, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Set the alarm to start
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, 18);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 , alarmIntent);
    }

    private void cancell (Context context, int id){
        Intent intent = new Intent(context, SplashActivity.class);
        PendingIntent alarmIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
    }
}
