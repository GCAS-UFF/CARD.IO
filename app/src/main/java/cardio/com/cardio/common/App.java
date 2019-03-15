package cardio.com.cardio.common;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.util.NotificationUtils;
import cardio.com.cardio.common.util.PreferencesUtils;

public class App extends Application {

    public App (){}

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtils.initialize(getApplicationContext());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        FirebaseHelper.initialize(getApplicationContext());
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseHelper.USER_KEY);

        NotificationUtils.initialize(getApplicationContext());
    }
}
