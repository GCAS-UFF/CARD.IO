package cardio.uff.cardio.professional.fragments;


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

import java.util.ArrayList;
import java.util.List;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.Firebase.FirebaseConfig;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.activities.LoginActivity;
import cardio.uff.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.uff.cardio.common.model.model.Profissional;
import cardio.uff.cardio.common.model.model.User;
import cardio.uff.cardio.common.model.view.TextBox;
import cardio.uff.cardio.common.model.view.Item;
import cardio.uff.cardio.professional.ComunicatorFragmentActivity;


public class RegisterProfessionalFragment extends Fragment {
    private RecyclerView mRecView;
    private Button mBtnRegister;
    private List<Item> mItens;
    private FirebaseAuth mFirebaseAuth;

    private TextBox mNameTextBox;
    private TextBox mCPFTextBox;
    private TextBox mRegionalRegisterTextBox;
    private TextBox mSpecialityTextBox;
    private TextBox mEmailTextBox;
    private TextBox mPasswordTextBox;

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

        mFirebaseAuth =  FirebaseConfig.getFirebaseAuth();

        mItens = new ArrayList<>();

        mNameTextBox = new TextBox("Nome", "", TextBox.INPUT_TEXT);
        mNameTextBox.setHint("Nome Completo");
        mItens.add(mNameTextBox);

        mCPFTextBox = new TextBox("CPF", "", TextBox.INPUT_DECIMAL);
        mCPFTextBox.setHint("000.000.000-00");
        mItens.add(mCPFTextBox);

        mRegionalRegisterTextBox = new TextBox("Registro Regional", "", TextBox.INPUT_TEXT);
        mItens.add(mRegionalRegisterTextBox);

        mSpecialityTextBox = new TextBox("Especialidade", "", TextBox.INPUT_TEXT);
        mItens.add(mSpecialityTextBox);

        mEmailTextBox = new TextBox("E-mail", "", TextBox.INPUT_TEXT);
        mEmailTextBox.setHint("email@email.com");
        mItens.add(mEmailTextBox);

        mPasswordTextBox = new TextBox("Senha", "", TextBox.INPUT_PASSWORD);
        mItens.add(mPasswordTextBox);

        ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(mItens);
        itemRecycleViewAdapter.setFragmentManager(getFragmentManager());

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidForm()) {
                    Profissional profissional = new Profissional();
                    profissional.setNome(mNameTextBox.getValue());
                    profissional.setCpf(mCPFTextBox.getValue());
                    profissional.setRegistro(mRegionalRegisterTextBox.getValue());
                    profissional.setEmail(mEmailTextBox.getValue());
                    profissional.setEspecialidade(mSpecialityTextBox.getValue());
                    profissional.setSenha(mPasswordTextBox.getValue());

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

            FirebaseHelper.getInstance().getUserDatabaseReference(profissional.getId()).setValue(user);
            FirebaseHelper.getInstance().getUserTypeDatabaseReference(profissional.getId()).setValue(profissional.getTipo());
            FirebaseHelper.getInstance().getProfessionalDatabaseReference(profissional.getId()).setValue(profissional);

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
