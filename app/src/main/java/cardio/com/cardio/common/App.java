package cardio.com.cardio.common;

import android.app.Application;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.util.PreferencesUtils;

public class App extends Application {

    public App (){}

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtils.initialize(getApplicationContext());
        FirebaseHelper.initialize(getApplicationContext());
    }
}
