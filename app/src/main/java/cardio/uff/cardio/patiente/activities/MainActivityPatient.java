package cardio.uff.cardio.patiente.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import cardio.uff.cardio.appointment.view.AppointmentFragment;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.fragments.AboutFragment;
import cardio.uff.cardio.common.model.model.Paciente;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.R;
import cardio.uff.cardio.common.activities.LoginActivity;
import cardio.uff.cardio.exercise.view.ExerciseFragment;
import cardio.uff.cardio.liquid.view.LiquidFragment;
import cardio.uff.cardio.medicine.view.MedicineFragment;
import cardio.uff.cardio.common.fragments.HomeFragment;
import cardio.uff.cardio.common.fragments.HelpFragment;
import cardio.uff.cardio.common.fragments.OrientationFragment;
import cardio.uff.cardio.monitoringAppointment.AppointmentMonitorating;
import cardio.uff.cardio.patiente.fragments.WeightFragment;
import cardio.uff.cardio.professional.ComunicatorFragmentActivity;
import cardio.uff.cardio.professional.fragments.PrescribeBiometricsFragment;
import cardio.uff.cardio.professional.fragments.PrescribeExercisesFragment;
import cardio.uff.cardio.professional.fragments.PrescribeFoodFragment;

public class MainActivityPatient extends AppCompatActivity implements HomeFragment.ComunicadorHomeActivity, ComunicatorFragmentActivity {

    private FragmentManager fragmentManager;
    private Paciente currentPatient;

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
                PreferencesUtils.IS_CURRENT_USER_PROFESSIONAL, false);

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
            HomeFragment homeFragment = new HomeFragment();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, homeFragment, "homeFragment");
            fragmentTransaction.addToBackStack(getString(R.string.stack));
            fragmentTransaction.commit();
        }
        setPatientSelected(null);

        int telaHomeRes = getIntent().getIntExtra("trocaTelaHome", -1);
        if (telaHomeRes > 0) trocaTelaHome(telaHomeRes);

        int telaRes = getIntent().getIntExtra("trocaTela", -1);
        if (telaRes > 0) trocaTela(telaRes);

        AppointmentMonitorating appointmentMonitorating = new AppointmentMonitorating(this);
        appointmentMonitorating.start();
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
    public void trocaTelaHome(int resId) {

        String id = getIntent().getStringExtra("id");
        long date = getIntent().getLongExtra("date", 0);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (resId){
            case R.id.ll_controle_peso:
                WeightFragment pesagemFragment = new WeightFragment();
                fragmentTransaction.replace(R.id.container,pesagemFragment, "pesagemFragment");
                fragmentTransaction.addToBackStack(getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_alimentacao:
                LiquidFragment liquidFragment = new LiquidFragment();
                fragmentTransaction.replace(R.id.container, liquidFragment, "liquidFragment");
                fragmentTransaction.addToBackStack(getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_exercicios:
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                fragmentTransaction.replace(R.id.container, exerciseFragment, "exerciseFragment");
                fragmentTransaction.addToBackStack(getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_medicacoes:
                MedicineFragment medicineFragment = new MedicineFragment();
                fragmentTransaction.replace(R.id.container, medicineFragment, "medicineFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_consultas:

                AppointmentFragment appointmentFragment;

                if (id != null && date > 0){
                    appointmentFragment = AppointmentFragment.newInstance(id,
                            Formater.getStringFromDate(new Date(date)));
                } else {
                    appointmentFragment= new AppointmentFragment();
                }

                fragmentTransaction.replace(R.id.container, appointmentFragment, "appointmentFragment");
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
    public void trocaTela(int resId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (resId){

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
            case R.id.ll_sobre:
                AboutFragment aboutFragment = new AboutFragment();
                fragmentTransaction.replace(R.id.container, aboutFragment, "aboutFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;


        }
    }

    @Override
    public void setPatientSelected(Paciente patient) {
        FirebaseHelper.getInstance().getCurrentPatientDatabaseReference().addValueEventListener(getPatientEventListener);
    }

    @Override
    public Paciente getPatientSelected() {
        return currentPatient;
    }

    @Override
    public boolean isProfessionalActivity() {
        return false;
    }

    ValueEventListener getPatientEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            currentPatient = dataSnapshot.getValue(Paciente.class);
            currentPatient.setId(dataSnapshot.getKey());
            PreferencesUtils.setString(
                    getBaseContext(),
                    PreferencesUtils.CURRENT_PATIENT_KEY, dataSnapshot.getKey());
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
