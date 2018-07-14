package cardio.com.cardio.fragments;


import android.content.Context;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardio.com.cardio.Firebase.FirebaseConfig;
import cardio.com.cardio.Formater;
import cardio.com.cardio.R;
import cardio.com.cardio.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.model.CaixaDeTexto;
import cardio.com.cardio.model.CaixaDeTextoData;
import cardio.com.cardio.model.Item;
import cardio.com.cardio.model.Paciente;


public class RegisterPatientFragment extends Fragment {

    private RecyclerView mRecView;
    private Button mBtnRegister;
    private List<Item> mItens;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDbPatientRef;
    private FirebaseAuth mFirebaseAuth;

    private LoginFragment.ComunicadorLoginActivity comunicadorLoginActivity;

    public RegisterPatientFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register_patient, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        comunicadorLoginActivity = (LoginFragment.ComunicadorLoginActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view_patient);
        mBtnRegister = (Button) view.findViewById(R.id.btn_register);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDbPatientRef = mFirebaseDatabase.getReference().child(getResources().getString(R.string.patientKey));
        mFirebaseAuth =  FirebaseConfig.getFirebaseAuth();

        mItens = new ArrayList<>();

        final CaixaDeTexto caixaDeTextoNome = new CaixaDeTexto("Nome", "", CaixaDeTexto.INPUT_TEXT);
        caixaDeTextoNome.setHint("Nome Completo");
        mItens.add(caixaDeTextoNome);

        final CaixaDeTexto caixaDeTextoCPF = new CaixaDeTexto("CPF", "", CaixaDeTexto.INPUT_DECIMAL);
        caixaDeTextoCPF.setHint("000.000.000-00");
        mItens.add(caixaDeTextoCPF);

        final CaixaDeTexto caixaDeTextoEndereco = new CaixaDeTexto("Endereço","", CaixaDeTexto.INPUT_TEXT);
        caixaDeTextoEndereco.setHint("");
        mItens.add(caixaDeTextoEndereco);

        final CaixaDeTextoData caixaDeTextoData = new CaixaDeTextoData("Data de Nascimento", CaixaDeTextoData.INPUT_DATE);
        mItens.add(caixaDeTextoData);

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
                try {
                    if (isValidForm()) {
                        Paciente paciente = new Paciente();
                        paciente.setNome(caixaDeTextoNome.getValue());
                        paciente.setCpf(caixaDeTextoCPF.getValue());
                        paciente.setEmail(caixaDeTextoEmail.getValue());
                        paciente.setEndereco(caixaDeTextoEndereco.getValue());
                        paciente.setSenha(caixaDeTextoSenha.getValue());

                        Date date = Formater.getDateFromString(caixaDeTextoData.getValue());
                        paciente.setDataNasc(date.getTime());

                        registerPatient(paciente);
                    } else {
                        Toast.makeText(getContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                        e.printStackTrace();
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

    public void registerPatient (final Paciente paciente){
        mFirebaseAuth.createUserWithEmailAndPassword(
                paciente.getEmail(),
                paciente.getSenha()
        ).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    insertPatient(paciente);
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

    public boolean insertPatient(Paciente paciente){

        try {
            paciente.setId(mDbPatientRef.push().getKey());
            mDbPatientRef.child(paciente.getId()).setValue(paciente);
            Toast.makeText(getActivity(), "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            comunicadorLoginActivity.trocaTela(R.layout.fragment_login);
            return true;
        } catch (Exception e){
            Toast.makeText(getActivity(), "Erro ao gravar usuário", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }
}
