package cardio.com.cardio.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.Firebase.FirebaseConfig;
import cardio.com.cardio.R;
import cardio.com.cardio.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.model.CaixaDeTexto;
import cardio.com.cardio.model.Item;

public class LoginActivity extends AppCompatActivity {

    private RecyclerView mRecView;
    private Button mBtnLogin;
    private Button mBtnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRecView = (RecyclerView) findViewById(R.id.rec_view_login);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        List<Item> itens = new ArrayList<>();

        CaixaDeTexto caixaDeTextoUsuario = new CaixaDeTexto("E-mail", "", CaixaDeTexto.INPUT_TEXT);
        itens.add(caixaDeTextoUsuario);

        CaixaDeTexto caixaDeTextoSenha = new CaixaDeTexto("senha", "", CaixaDeTexto.INPUT_PASSWORD);
        itens.add(caixaDeTextoSenha);

        final ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(itens);

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(this));

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaixaDeTexto caixaDeTextoUsuario = (CaixaDeTexto) itemRecycleViewAdapter.getmItens().get(0);
                CaixaDeTexto caixaDeTextoSenha = (CaixaDeTexto) itemRecycleViewAdapter.getmItens().get(1);

                if(caixaDeTextoUsuario.getValue() == null || caixaDeTextoUsuario.getValue().isEmpty()
                        || caixaDeTextoSenha.getValue() == null || caixaDeTextoSenha.getValue().isEmpty()){
                    Toast.makeText(getBaseContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseAuth autentication = FirebaseConfig.getFirebaseAuth();
                    autentication.signInWithEmailAndPassword(caixaDeTextoUsuario.getValue(), caixaDeTextoSenha.getValue()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getBaseContext(), "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(getBaseContext(), "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
