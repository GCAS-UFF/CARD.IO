package cardio.uff.cardio.professional.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import cardio.uff.cardio.R;
import cardio.uff.cardio.appointment.view.AppointmentFragment;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.activities.LoginActivity;
import cardio.uff.cardio.common.fragments.AboutFragment;
import cardio.uff.cardio.common.fragments.HelpFragment;
import cardio.uff.cardio.common.fragments.HomeFragment;
import cardio.uff.cardio.common.fragments.OrientationFragment;
import cardio.uff.cardio.common.model.model.Paciente;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.exercise.view.ExerciseFragment;
import cardio.uff.cardio.liquid.view.LiquidFragment;
import cardio.uff.cardio.medicine.view.MedicineFragment;
import cardio.uff.cardio.professional.ComunicatorFragmentActivity;
import cardio.uff.cardio.professional.fragments.PatientListFragment;
import cardio.uff.cardio.professional.fragments.PrescribeBiometricsFragment;
import cardio.uff.cardio.professional.fragments.RegisterPatientFragment;
import cardio.uff.cardio.professional.fragments.RegisterProfessionalFragment;

import static android.provider.ContactsContract.Intents.Insert.ACTION;

public class MainActivityProfessional extends AppCompatActivity implements ComunicatorFragmentActivity, HomeFragment.ComunicadorHomeActivity {

    private FragmentManager fragmentManager;
    private InternalReceiver internalReceiver;

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

        PreferencesUtils.setBollean(this,
                PreferencesUtils.IS_CURRENT_USER_PROFESSIONAL, true);

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

        internalReceiver = new InternalReceiver();
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
                LiquidFragment liquidFragment = new LiquidFragment();
                fragmentTransaction.replace(R.id.container, liquidFragment, "liquidFragment");
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
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                fragmentTransaction.replace(R.id.container, exerciseFragment, "exerciseFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_consultas:

                AppointmentFragment appointmentFragment= new AppointmentFragment();
                fragmentTransaction.replace(R.id.container, appointmentFragment, "appointmentFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_medicacoes:
                MedicineFragment medicineFragment = new MedicineFragment();
                fragmentTransaction.replace(R.id.container, medicineFragment, "medicineFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_sobre:
                AboutFragment aboutFragment = new AboutFragment();
                fragmentTransaction.replace(R.id.container, aboutFragment, "aboutFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_orientation:
                OrientationFragment orientationFragment = new OrientationFragment();
                fragmentTransaction.replace(R.id.container, orientationFragment,"orientationFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_help:
                HelpFragment helpFragment = new HelpFragment();
                fragmentTransaction.replace(R.id.container, helpFragment, "helpFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void trocaTelaHome(int resId) {
        trocaTela(resId);
    }

    @Override
    public void setPatientSelected(Paciente patient) {
        PreferencesUtils.setString(this,
                PreferencesUtils.CURRENT_PATIENT_KEY, patient.getId());
    }

    @Override
    public boolean isProfessionalActivity() {
        return true;
    }

    class InternalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == "notification"){
                ArrayList<String> arrayList = intent.getExtras().getStringArrayList("msg");
                PushNotificationDialogFragment pndf = new PushNotificationDialogFragment();
                pndf.show((MainActivityProfessional.this).getSupportFragmentManager(),"notification");
                Bundle args = new Bundle();
                args.putStringArrayList("msg", arrayList);
                pndf.setArguments(args);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("MAIN LIFECYCLE:", "onResume()");
        IntentFilter intentFilter = new IntentFilter(ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(internalReceiver,intentFilter);
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i("MAIN LIFECYCLE:", "onPause()");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(internalReceiver);
    }
}
