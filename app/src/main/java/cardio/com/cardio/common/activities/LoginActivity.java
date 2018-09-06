package cardio.com.cardio.common.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cardio.com.cardio.R;
import cardio.com.cardio.common.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity implements LoginFragment.ComunicadorLoginActivity {

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
            LoginFragment loginFragment = new LoginFragment();

            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, loginFragment, "loginFragment");
//            fragmentTransaction.addToBackStack(getString(R.string.stack));
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() <=1){
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void changeScreen(int resId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (resId){
            case R.layout.fragment_login:
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.replace(R.id.container, loginFragment, "loginFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
        }

    }
}
