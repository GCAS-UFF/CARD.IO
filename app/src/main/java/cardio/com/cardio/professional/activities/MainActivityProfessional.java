package cardio.com.cardio.professional.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.activities.LoginActivity;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;
import cardio.com.cardio.professional.fragments.PatientListFragment;
import cardio.com.cardio.professional.fragments.RegisterPatientFragment;
import cardio.com.cardio.professional.fragments.RegisterProfessionalFragment;

public class MainActivityProfessional extends AppCompatActivity implements ComunicatorFragmentActivity{

    private FragmentManager fragmentManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_option_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                PreferencesUtils.setString(this, FirebaseHelper.USER_KEY, null);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient);

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
            if (PreferencesUtils.getString(this, FirebaseHelper.USER_KEY) != null){
                PatientListFragment patientListFragment = new PatientListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, patientListFragment, "patientListFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.pile));
                fragmentTransaction.commit();
            } else {
                RegisterProfessionalFragment registerProfessionalFragment = new RegisterProfessionalFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, registerProfessionalFragment, "registerProfessionalFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.pile));
                fragmentTransaction.commit();
            }
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
    public void trocaTela(int resId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (resId){

            case R.layout.fragment_patient_list:
                PatientListFragment patientListFragment = new PatientListFragment();
                fragmentTransaction.replace(R.id.container, patientListFragment, "patientListFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.pile));
                fragmentTransaction.commit();
                break;

            case R.layout.fragment_register_patient:
                RegisterPatientFragment registerPatientFragment = new RegisterPatientFragment();
                fragmentTransaction.replace(R.id.container, registerPatientFragment, "registerPatientFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.pile));
                fragmentTransaction.commit();
                break;
        }
    }
}
