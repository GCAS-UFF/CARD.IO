package cardio.com.cardio.patiente.fragments;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.model.Alimentacao;
import cardio.com.cardio.common.model.view.CaixaDeTexto;
import cardio.com.cardio.common.model.view.Item;

public class AlimentacaoFragment extends Fragment {

    private RecyclerView mRecViewInput;
    private ItemRecycleViewAdapter mItemRecycleViewAdapter;
    private Button mBtnSave;
    private List<Item> mItems;
    private CaixaDeTexto mCaixaDeTextoAliment;
    private CaixaDeTexto mCaixaDeTextoQuantity;

    public AlimentacaoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alimentacao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecViewInput = (RecyclerView) view.findViewById(R.id.rec_view_input);
        mBtnSave = (Button) view.findViewById(R.id.btn_save);
        mItems = new ArrayList<>();

        mCaixaDeTextoAliment = new CaixaDeTexto("Bebida", "", CaixaDeTexto.INPUT_TEXT);
        mCaixaDeTextoAliment.setHint("Água");
        mItems.add(mCaixaDeTextoAliment);

        mCaixaDeTextoQuantity = new CaixaDeTexto("Quantidade", "ml", CaixaDeTexto.INPUT_NUMBER);
        mCaixaDeTextoQuantity.setHint("100");
        mItems.add(mCaixaDeTextoQuantity);

        mItemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
        mRecViewInput.setAdapter(mItemRecycleViewAdapter);
        mRecViewInput.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidForm()) {
                    saveObject();
                }
                else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_error_field_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidForm (){
        boolean isValid = true;
        for (Item item : mItems){
            if (item.isEmpty()){
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private void saveObject(){
        Alimentacao alimentacao = new Alimentacao();
        alimentacao.setAliment(mCaixaDeTextoAliment.getValue());
        alimentacao.setQuantity(Formater.getIntegerFromString(mCaixaDeTextoQuantity.getValue()));

        alimentacao.setCreateDate((new Date()).getTime());
        alimentacao.setPerformed(true);

        saveIntoFirebase(alimentacao);
    }
    private void saveIntoFirebase (Alimentacao alimentacao){

        try{
            FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference().
                    child((new Paciente()).getTipo()).
                    child(PreferencesUtils.getString(getActivity(), getResources().getString(R.string.userKey))).
                    child(alimentacao.getConstantId()).
                    child(alimentacao.getType());
            //Log.d("DEBUG-JP: ", mDatabaseReference.toString());
            alimentacao.setId(mDatabaseReference.push().getKey());
            mDatabaseReference.child(alimentacao.getId()).setValue(alimentacao);
            Toast.makeText(getActivity(), getResources().getString(R.string.message_succes_register_general), Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_register_general), Toast.LENGTH_SHORT).show();
        }

    }
}