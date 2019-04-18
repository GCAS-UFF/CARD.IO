package cardio.uff.cardio.common.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import java.util.Date;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.activities.SplashActivity;

public class NotificationUtils {

    private static String CHANNEL_ID = "default";
    private static String CHANNEL_NAME = "Default";
    private static String CHANNEL_DESCRIPTION = "default channel for notifications";

    private static NotificationUtils mNotificationUtils;

    public static void initialize (Context context){
        mNotificationUtils = new NotificationUtils();
        createNotificationChannel(context);
    }

    public static NotificationUtils getInstance (){
        return mNotificationUtils;
    }

    private NotificationUtils() {}

    public void showNotification (Context context, String title, String content, Intent intent, int id){
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ico_add)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, builder.build());
    }

    public void showNotification (Context context, String title, String content){
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        showNotification(context, title, content, intent, Formater.createRandomID());
    }

    private static void createNotificationChannel(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = CHANNEL_NAME;
                String description = CHANNEL_DESCRIPTION;
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
