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
import cardio.com.cardio.common.fragments.HomeFragment;
import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;
import cardio.com.cardio.professional.fragments.PatientListFragment;
import cardio.com.cardio.professional.fragments.PrescribeAppointmentFragment;
import cardio.com.cardio.professional.fragments.PrescribeBiometricsFragment;
import cardio.com.cardio.professional.fragments.PrescribeExercisesFragment;
import cardio.com.cardio.professional.fragments.PrescribeFoodFragment;
import cardio.com.cardio.professional.fragments.RegisterPatientFragment;
import cardio.com.cardio.professional.fragments.RegisterProfessionalFragment;

public class MainActivityProfessional extends AppCompatActivity implements ComunicatorFragmentActivity, HomeFragment.ComunicadorHomeActivity {

    private FragmentManager fragmentManager;
    private Paciente currentPatientSelected;

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
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
            } else {
                RegisterProfessionalFragment registerProfessionalFragment = new RegisterProfessionalFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, registerProfessionalFragment, "registerProfessionalFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
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
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;

            case R.layout.fragment_register_patient:
                RegisterPatientFragment registerPatientFragment = new RegisterPatientFragment();
                fragmentTransaction.replace(R.id.container, registerPatientFragment, "registerPatientFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.layout.fragment_home:
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.container, homeFragment, "homeFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_alimentacao:
                PrescribeFoodFragment prescribeFoodFragment = new PrescribeFoodFragment();
                fragmentTransaction.replace(R.id.container, prescribeFoodFragment, "prescribeFoodFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_controle_peso:
                PrescribeBiometricsFragment prescribeBiometricsFragment = new PrescribeBiometricsFragment();
                fragmentTransaction.replace(R.id.container, prescribeBiometricsFragment, "prescribeBiometricsFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_exercicios:
                PrescribeExercisesFragment prescribeExercisesFragment = new PrescribeExercisesFragment();
                fragmentTransaction.replace(R.id.container, prescribeExercisesFragment, "prescribeExercisesFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_consultas:
                PrescribeAppointmentFragment prescribeAppointmentFragment = new PrescribeAppointmentFragment();
                fragmentTransaction.replace(R.id.container, prescribeAppointmentFragment, "prescribeAppointmentFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
        }
    }

    @Override
    public void setPatientSelected(Paciente patient) {
        this.currentPatientSelected = patient;
    }

    @Override
    public Paciente getPatientSelected() {
        return this.currentPatientSelected;
    }
}
