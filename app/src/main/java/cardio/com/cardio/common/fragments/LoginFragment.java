package cardio.com.cardio.common.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.common.Firebase.FirebaseConfig;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.R;
import cardio.com.cardio.patiente.activities.MainActivityPatient;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.view.TextBox;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.professional.activities.MainActivityProfessional;


public class LoginFragment extends Fragment {

    private RecyclerView mRecView;
    private Button mBtnLogin;
    private TextView mTvNotRegistered;
    private ComunicadorLoginActivity comunicadorLoginActivity;
    private ItemRecycleViewAdapter itemRecycleViewAdapter;
    private  TextBox userTextBox;
    private TextBox passwordTextBox;
    private FirebaseAuth autentication;

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
        mTvNotRegistered = (TextView) view.findViewById(R.id.tv_not_registered);

        List<Item> itens = new ArrayList<>();

        TextBox caixaDeTextoUsuario = new TextBox("E-mail", "", TextBox.INPUT_TEXT);
        itens.add(caixaDeTextoUsuario);

        TextBox caixaDeTextoSenha = new TextBox("Senha", "", TextBox.INPUT_PASSWORD);
        itens.add(caixaDeTextoSenha);

        itemRecycleViewAdapter = new ItemRecycleViewAdapter(itens);

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnLogin.setClickable(false);
                userTextBox = (TextBox) itemRecycleViewAdapter.getmItems().get(0);
                passwordTextBox = (TextBox) itemRecycleViewAdapter.getmItems().get(1);

                if(userTextBox.getValue() == null || userTextBox.getValue().isEmpty()
                        || passwordTextBox.getValue() == null || passwordTextBox.getValue().isEmpty()){
                    Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                    autentication = FirebaseConfig.getFirebaseAuth();
                    autentication.signInWithEmailAndPassword(userTextBox.getValue(), passwordTextBox.getValue()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();

                                ValueEventListener postListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String tipo = dataSnapshot.getValue(String.class);

                                        PreferencesUtils.setString(getActivity(), FirebaseHelper.USER_KEY, autentication.getUid());
                                        PreferencesUtils.setString(getActivity(), FirebaseHelper.USER_TYPE_KEY, tipo);

                                        if (tipo.equals(FirebaseHelper.PATIENT_KEY)){
                                            startActivity(new Intent(getActivity(), MainActivityPatient.class));
                                        } else {
                                            startActivity(new Intent(getActivity(), MainActivityProfessional.class));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        // Getting Post failed, log a message
                                    }
                                };
                                FirebaseHelper.getInstance().getUserTypeDatabaseReference(autentication.getUid())
                                        .addListenerForSingleValueEvent(postListener);

                                mBtnLogin.setClickable(false);
                            }
                            else{
                                Toast.makeText(getActivity(), "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                                mBtnLogin.setClickable(true);
                            }
                        }
                    });
                }
            }
        });

        mTvNotRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterAlertDialog();
            }
        });
    }

    private void showRegisterAlertDialog (){

        new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert)
                .setMessage(getResources().getString(R.string.message_not_registred))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), MainActivityProfessional.class));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public interface ComunicadorLoginActivity {
        void changeScreen(int resId);
    }
}
