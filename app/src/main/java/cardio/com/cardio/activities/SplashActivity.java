package cardio.com.cardio.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cardio.com.cardio.Firebase.FirebaseConfig;
import cardio.com.cardio.R;

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
        if(FirebaseConfig.getFirebaseAuth().getCurrentUser() != null)
            startActivity(new Intent(this, MainActivity.class));
        else
            startActivity(new Intent(this, LoginActivity.class));
    }
}
