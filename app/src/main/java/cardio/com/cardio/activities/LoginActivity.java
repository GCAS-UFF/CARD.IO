package cardio.com.cardio.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.Firebase.FirebaseConfig;
import cardio.com.cardio.R;
import cardio.com.cardio.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.fragments.HomeFragment;
import cardio.com.cardio.fragments.LoginFragment;
import cardio.com.cardio.fragments.RegisterPatientFragment;
import cardio.com.cardio.model.CaixaDeTexto;
import cardio.com.cardio.model.Item;

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
            case R.layout.fragment_login:
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.replace(R.id.container, loginFragment, "loginFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.pilha));
                fragmentTransaction.commit();
                break;
            case R.layout.fragment_register_patient:
                RegisterPatientFragment registerPatientFragment = new RegisterPatientFragment();
                fragmentTransaction.replace(R.id.container, registerPatientFragment, "registerPatientFragment");
                fragmentTransaction.addToBackStack(getResources().getString(R.string.pilha));
                fragmentTransaction.commit();
                break;
        }

    }
}
