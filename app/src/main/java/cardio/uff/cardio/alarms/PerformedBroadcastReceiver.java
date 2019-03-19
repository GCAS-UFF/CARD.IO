package cardio.uff.cardio.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cardio.uff.cardio.monitoringPerformed.RealizedMonitoratingExercise;
import cardio.uff.cardio.monitoringPerformed.RealizedMonitoratingWeigth;

public class PerformedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        RealizedMonitoratingExercise realizedMonitoratingExercise = new RealizedMonitoratingExercise(context);
        realizedMonitoratingExercise.start();

        RealizedMonitoratingWeigth realizedMonitoratingWeigth = new RealizedMonitoratingWeigth(context);
        realizedMonitoratingWeigth.start();
    }
}