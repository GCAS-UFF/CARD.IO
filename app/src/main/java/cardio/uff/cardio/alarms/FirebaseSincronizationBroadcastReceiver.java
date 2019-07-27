package cardio.uff.cardio.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.monitoringPerformed.RealizedMonitoratingExercise;
import cardio.uff.cardio.monitoringPerformed.RealizedMonitoratingWeigth;

public class FirebaseSincronizationBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseHelper.getInstance().sincronize();
    }
}