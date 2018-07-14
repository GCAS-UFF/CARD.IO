package cardio.com.cardio.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import cardio.com.cardio.R;
import cardio.com.cardio.fragments.HomeFragment;
import cardio.com.cardio.fragments.MedicamentosFragment;
import cardio.com.cardio.fragments.PesagemFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.ComunicadorHomeActivity{

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
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
            HomeFragment homeFragment = new HomeFragment();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, homeFragment, "homeFragment");
            fragmentTransaction.addToBackStack(getString(R.string.pilha));
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
                PesagemFragment pesagemFragment = new PesagemFragment();
                fragmentTransaction.replace(R.id.container,pesagemFragment, "pesagemFragment");
                fragmentTransaction.addToBackStack(getString(R.string.pilha));
                fragmentTransaction.commit();
                break;
            case R.id.ll_medicacoes:
                MedicamentosFragment medicamentosFragment = new MedicamentosFragment();
                fragmentTransaction.replace(R.id.container, medicamentosFragment, "medicamentosFragment");
                fragmentTransaction.addToBackStack(getString(R.string.pilha));
                fragmentTransaction.commit();
                break;
        }
    }
}
