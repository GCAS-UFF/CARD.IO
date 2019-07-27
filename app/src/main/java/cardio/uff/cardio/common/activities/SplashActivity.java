package cardio.uff.cardio.common.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cardio.uff.cardio.alarms.AlarmForFirebaseSincronization;
import cardio.uff.cardio.alarms.AlarmForPerformed;
import cardio.uff.cardio.alarms.PerformedBroadcastReceiver;
import cardio.uff.cardio.common.Firebase.FirebaseConfig;
import cardio.uff.cardio.R;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.model.model.Paciente;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.monitoratingThreshold.ThresholdMonitoratingLiquid;
import cardio.uff.cardio.monitoratingThreshold.ThresholdMonitoratingWeigth;
import cardio.uff.cardio.monitoringPerformed.RealizedMonitoratingExercise;
import cardio.uff.cardio.monitoringPerformed.RealizedMonitoratingWeigth;
import cardio.uff.cardio.patiente.activities.MainActivityPatient;
import cardio.uff.cardio.professional.activities.MainActivityProfessional;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) actionBar.hide();

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    tryLogin();
                    finish();
                }
            }
        };
        timer.start();
    }

    public void tryLogin(){
        if(FirebaseConfig.getFirebaseAuth().getCurrentUser() != null) {

            String type = PreferencesUtils.getString(this, FirebaseHelper.USER_TYPE_KEY);

            if (type.equals((new Paciente()).getTipo())) {
                startActivity(new Intent(this, MainActivityPatient.class));

                AlarmForFirebaseSincronization alarmForFirebaseSincronization =  new AlarmForFirebaseSincronization();
                alarmForFirebaseSincronization.createAlarm(this);

            } else {
                startActivity(new Intent(this, MainActivityProfessional.class));
            }
        }
        else
            startActivity(new Intent(this, LoginActivity.class));
    }
}
