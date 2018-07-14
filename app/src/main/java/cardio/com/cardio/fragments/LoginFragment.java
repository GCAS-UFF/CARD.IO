package cardio.com.cardio.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.Firebase.FirebaseConfig;
import cardio.com.cardio.PreferencesUtils;
import cardio.com.cardio.R;
import cardio.com.cardio.activities.MainActivity;
import cardio.com.cardio.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.model.CaixaDeTexto;
import cardio.com.cardio.model.Item;


public class LoginFragment extends Fragment {

    private RecyclerView mRecView;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private ComunicadorLoginActivity comunicadorLoginActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comunicadorLoginActivity = (ComunicadorLoginActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view_login);
        mBtnLogin = (Button) view.findViewById(R.id.btn_login);
        mBtnRegister = (Button) view.findViewById(R.id.btn_register);

        List<Item> itens = new ArrayList<>();

        CaixaDeTexto caixaDeTextoUsuario = new CaixaDeTexto("E-mail", "", CaixaDeTexto.INPUT_TEXT);
        itens.add(caixaDeTextoUsuario);

        CaixaDeTexto caixaDeTextoSenha = new CaixaDeTexto("Senha", "", CaixaDeTexto.INPUT_PASSWORD);
        itens.add(caixaDeTextoSenha);

        final ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(itens);

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CaixaDeTexto caixaDeTextoUsuario = (CaixaDeTexto) itemRecycleViewAdapter.getmItens().get(0);
                CaixaDeTexto caixaDeTextoSenha = (CaixaDeTexto) itemRecycleViewAdapter.getmItens().get(1);

                if(caixaDeTextoUsuario.getValue() == null || caixaDeTextoUsuario.getValue().isEmpty()
                        || caixaDeTextoSenha.getValue() == null || caixaDeTextoSenha.getValue().isEmpty()){
                    Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(getString(R.string.patientKey));
                    FirebaseAuth autentication = FirebaseConfig.getFirebaseAuth();
                    autentication.signInWithEmailAndPassword(caixaDeTextoUsuario.getValue(), caixaDeTextoSenha.getValue()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                databaseReference.orderByChild("email").equalTo(caixaDeTextoUsuario.getValue()).
                                        addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                                            String patientId = adSnapshot.getKey();
                                            PreferencesUtils.setString(getActivity(), getString(R.string.patientKey), patientId);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(), "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorLoginActivity.trocaTela(R.layout.fragment_register_patient);
            }
        });
    }

    public interface ComunicadorLoginActivity {
        void trocaTela( int resId);
    }
}
