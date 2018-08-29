package cardio.com.cardio.patiente.activities;

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

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.fragments.AboutFragment;
import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.R;
import cardio.com.cardio.common.activities.LoginActivity;
import cardio.com.cardio.patiente.fragments.AlimentationFragment;
import cardio.com.cardio.patiente.fragments.ConsultasFragment;
import cardio.com.cardio.patiente.fragments.ExerciseFragment;
import cardio.com.cardio.common.fragments.HomeFragment;
import cardio.com.cardio.patiente.fragments.MedicinesFragments;
import cardio.com.cardio.patiente.fragments.WeightFragment;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;
import cardio.com.cardio.professional.fragments.PatientListFragment;
import cardio.com.cardio.professional.fragments.PrescribeAppointmentFragment;
import cardio.com.cardio.professional.fragments.PrescribeBiometricsFragment;
import cardio.com.cardio.professional.fragments.PrescribeExercisesFragment;
import cardio.com.cardio.professional.fragments.PrescribeFoodFragment;
import cardio.com.cardio.professional.fragments.PrescribeMedicineFragment;
import cardio.com.cardio.professional.fragments.RegisterPatientFragment;

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

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
            HomeFragment homeFragment = new HomeFragment();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, homeFragment, "homeFragment");
            fragmentTransaction.addToBackStack(getString(R.string.stack));
            fragmentTransaction.commit();
        }

        setPatientSelected(null);
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

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (resId){
            case R.id.ll_controle_peso:
                WeightFragment pesagemFragment = new WeightFragment();
                fragmentTransaction.replace(R.id.container,pesagemFragment, "pesagemFragment");
                fragmentTransaction.addToBackStack(getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_alimentacao:
                AlimentationFragment alimentacaoFragment = new AlimentationFragment();
                fragmentTransaction.replace(R.id.container, alimentacaoFragment, "alimentacaoFragment");
                fragmentTransaction.addToBackStack(getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_exercicios:
                ExerciseFragment exercicioFragment = new ExerciseFragment();
                fragmentTransaction.replace(R.id.container, exercicioFragment, "exercicioFragment");
                fragmentTransaction.addToBackStack(getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_medicacoes:
                PrescribeMedicineFragment prescribeMedicineFragment = new PrescribeMedicineFragment();
                fragmentTransaction.replace(R.id.container, prescribeMedicineFragment, "prescribeMedicineFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.stack));
                fragmentTransaction.commit();
                break;
            case R.id.ll_consultas:
                ConsultasFragment consultasFragment = new ConsultasFragment();
                fragmentTransaction.replace(R.id.container, consultasFragment, "consultasFragment");
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
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
