package cardio.com.cardio.patiente.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.R;
import cardio.com.cardio.common.activities.LoginActivity;
import cardio.com.cardio.patiente.fragments.AlimentationFragment;
import cardio.com.cardio.patiente.fragments.ExerciseFragment;
import cardio.com.cardio.patiente.fragments.HomeFragment;
import cardio.com.cardio.patiente.fragments.MedicinesFragments;
import cardio.com.cardio.patiente.fragments.WeightFragment;

public class MainActivityPatient extends AppCompatActivity implements HomeFragment.ComunicadorHomeActivity{

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
            HomeFragment homeFragment = new HomeFragment();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, homeFragment, "homeFragment");
            fragmentTransaction.addToBackStack(getString(R.string.pile));
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
    public void trocaTela(int resId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (resId){
            case R.id.ll_controle_peso:
                WeightFragment pesagemFragment = new WeightFragment();
                fragmentTransaction.replace(R.id.container,pesagemFragment, "pesagemFragment");
                fragmentTransaction.addToBackStack(getString(R.string.pile));
                fragmentTransaction.commit();
                break;
            case R.id.ll_medicacoes:
                MedicinesFragments medicamentosFragment = new MedicinesFragments();
                fragmentTransaction.replace(R.id.container, medicamentosFragment, "medicamentosFragment");
                fragmentTransaction.addToBackStack(getString(R.string.pile));
                fragmentTransaction.commit();
                break;
            case R.id.ll_alimentacao:
                AlimentationFragment alimentacaoFragment = new AlimentationFragment();
                fragmentTransaction.replace(R.id.container, alimentacaoFragment, "alimentacaoFragment");
                fragmentTransaction.addToBackStack(getString(R.string.pile));
                fragmentTransaction.commit();
                break;
            case R.id.ll_exercicios:
                ExerciseFragment exercicioFragment = new ExerciseFragment();
                fragmentTransaction.replace(R.id.container, exercicioFragment, "exercicioFragment");
                fragmentTransaction.addToBackStack(getString(R.string.pile));
                fragmentTransaction.commit();


        }
    }
}
