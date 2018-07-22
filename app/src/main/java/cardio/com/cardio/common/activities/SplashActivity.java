package cardio.com.cardio.common.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cardio.com.cardio.common.Firebase.FirebaseConfig;
import cardio.com.cardio.R;
import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.patiente.activities.MainActivityPatient;
import cardio.com.cardio.professional.activities.MainActivityProfessional;

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

            String tipo = PreferencesUtils.getString(this, getString(R.string.userTypeKey));

            if (tipo.equals((new Paciente()).getTipo())) {
                startActivity(new Intent(this, MainActivityPatient.class));
            } else {
                startActivity(new Intent(this, MainActivityProfessional.class));
            }
        }
        else
            startActivity(new Intent(this, LoginActivity.class));
    }
}
