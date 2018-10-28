package cardio.com.cardio.common.util;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import cardio.com.cardio.R;
import cardio.com.cardio.common.activities.SplashActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUltils {

    private Context context;
    private NotificationManager notificationManager;
    private AlarmManager alarmManager;
    private NotificationCompat.Builder mBuilder;
    private int notificationId = 1;
    private String channelId = "channel-01";
    private String channelName = "Channel Name";
    private int importance = NotificationManager.IMPORTANCE_HIGH;


    public NotificationUltils(Context context){
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

    }

    public void start(String contextTitle, String contextText){

        mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.round_ico)
                .setContentTitle(contextTitle)
                .setContentText(contextText);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    public void scheduleAlarme (String title, String text){

        Intent contentIntent = new Intent(context, SplashActivity.class);
        PendingIntent pendingContentIntent = PendingIntent.getActivity(context, 0,
                contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 10000,
                pendingContentIntent);

    }
}