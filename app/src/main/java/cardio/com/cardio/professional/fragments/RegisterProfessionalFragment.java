package cardio.com.cardio.professional.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseConfig;
import cardio.com.cardio.common.activities.LoginActivity;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.model.Profissional;
import cardio.com.cardio.common.model.model.User;
import cardio.com.cardio.common.model.view.CaixaDeTexto;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;


public class RegisterProfessionalFragment extends Fragment {
    private RecyclerView mRecView;
    private Button mBtnRegister;
    private List<Item> mItens;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDbProfessionalRef;
    private DatabaseReference mDbUserRef;
    private FirebaseAuth mFirebaseAuth;

    private ComunicatorFragmentActivity comunicatorFragmentActivity;

    public RegisterProfessionalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register_professional, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view_patient);
        mBtnRegister = (Button) view.findViewById(R.id.btn_register);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDbProfessionalRef = mFirebaseDatabase.getReference().child(getResources().getString(R.string.professionalKey));
        mDbUserRef = mFirebaseDatabase.getReference().child(getString(R.string.userKey));
        mFirebaseAuth =  FirebaseConfig.getFirebaseAuth();

        mItens = new ArrayList<>();

        final CaixaDeTexto caixaDeTextoNome = new CaixaDeTexto("Nome", "", CaixaDeTexto.INPUT_TEXT);
        caixaDeTextoNome.setHint("Nome Completo");
        mItens.add(caixaDeTextoNome);

        final CaixaDeTexto caixaDeTextoCPF = new CaixaDeTexto("CPF", "", CaixaDeTexto.INPUT_DECIMAL);
        caixaDeTextoCPF.setHint("000.000.000-00");
        mItens.add(caixaDeTextoCPF);

        final CaixaDeTexto caixaDeTextoRegistroRegional = new CaixaDeTexto("Registro Regional", "", CaixaDeTexto.INPUT_TEXT);
        mItens.add(caixaDeTextoRegistroRegional);

        final CaixaDeTexto caixaDeTextoEspecialidade = new CaixaDeTexto("Especialidade", "", CaixaDeTexto.INPUT_TEXT);
        mItens.add(caixaDeTextoEspecialidade);

        final CaixaDeTexto caixaDeTextoEmail = new CaixaDeTexto("E-mail", "", CaixaDeTexto.INPUT_TEXT);
        caixaDeTextoEmail.setHint("email@email.com");
        mItens.add(caixaDeTextoEmail);

        final CaixaDeTexto caixaDeTextoSenha = new CaixaDeTexto("Senha", "", CaixaDeTexto.INPUT_PASSWORD);
        mItens.add(caixaDeTextoSenha);

        ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(mItens);
        itemRecycleViewAdapter.setFragmentManager(getFragmentManager());

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidForm()) {
                    Profissional profissional = new Profissional();
                    profissional.setNome(caixaDeTextoNome.getValue());
                    profissional.setCpf(caixaDeTextoCPF.getValue());
                    profissional.setRegistro(caixaDeTextoRegistroRegional.getValue());
                    profissional.setEmail(caixaDeTextoEmail.getValue());
                    profissional.setEspecialidade(caixaDeTextoEspecialidade.getValue());
                    profissional.setSenha(caixaDeTextoSenha.getValue());

                    registerProfissional(profissional);
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.message_error_field_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidForm (){
        boolean isValid = true;

        for (Item item : mItens){
            if (item.isEmpty()){
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    public void registerProfissional (final Profissional profissional){
        mFirebaseAuth.createUserWithEmailAndPassword(
                profissional.getEmail(),
                profissional.getSenha()
        ).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    insertProfissional(profissional);
                } else{
                    String erroExceao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        erroExceao = "Senha fraca!";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        erroExceao = "Email inválido";
                    } catch (FirebaseAuthUserCollisionException e){
                        erroExceao = "Email já cadasrado!";
                    } catch (Exception e){
                        erroExceao = "Erro ao executar comando!";
                    }

                    Toast.makeText(getActivity(), "Erro: " + erroExceao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean insertProfissional(Profissional profissional){

        try {
            User user = new User(profissional.getTipo());
            user.setEmail(profissional.getEmail());

            profissional.setId(mFirebaseAuth.getUid());
            mDbUserRef.child(profissional.getId()).setValue(user);
            mDbUserRef.child(profissional.getId()).child(getString(R.string.userTypeKey)).setValue(profissional.getTipo());
            mDbProfessionalRef.child(profissional.getId()).setValue(profissional);
            Toast.makeText(getActivity(), getResources().getString(R.string.message_succes_register_user), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return true;
        } catch (Exception e){
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_register_user), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }
}
