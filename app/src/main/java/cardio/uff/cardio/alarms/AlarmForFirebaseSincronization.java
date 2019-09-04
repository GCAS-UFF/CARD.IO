package cardio.uff.cardio.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.util.Date;

import cardio.uff.cardio.common.util.Formater;

public class AlarmForFirebaseSincronization {


    private final int id = 567;
    private final long interval = 1*60*1000;
    private AlarmManager alarmMgr;
    private long initialTime;

    public void createAlarm (Context context) {
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        try {
            initialTime = Formater.getDateFromString("01/01/2019").getTime();
        } catch (ParseException e){
            e.printStackTrace();
        }

        Intent intent = new Intent(context, FirebaseSincronizationBroadcastReceiver.class);

        //567 é um id qualquer
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Esse alarme vai sincronizar o firebase conforme o intervalo definido
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, initialTime, interval, alarmIntent);
    }

}
