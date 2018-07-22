package cardio.com.cardio.patiente.fragments;


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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import cardio.com.cardio.common.model.view.CaixaDeTexto;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.model.model.MedicaoDadosFisiologicos;

public class PesagemFragment extends Fragment {

    private RecyclerView mRecView;
    private CaixaDeTexto mCaixaDeTextoPeso;
    private CaixaDeTexto mCaixaDeTextoBatimentos;
    private CaixaDeTexto mCaixaDeTextoPressao;
    private ItemRecycleViewAdapter mItemRecycleViewAdapter;
    private RadioGroup mRGInchaco;
    private RadioGroup mRGFadiga;
    private Button mButtonSave;
    private List<Item> mItems ;

    public PesagemFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pesagem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mRGFadiga = (RadioGroup) view.findViewById(R.id.rg_fadiga);
        mRGInchaco = (RadioGroup) view.findViewById(R.id.rg_inchaco);
        mButtonSave = (Button) view.findViewById(R.id.btn_save);

        mItems = new ArrayList<>();

        mCaixaDeTextoPeso = new CaixaDeTexto("Meu peso", "kg", CaixaDeTexto.INPUT_DECIMAL);
        mCaixaDeTextoPeso.setHint("00.0");
        mItems.add(mCaixaDeTextoPeso);

        mCaixaDeTextoBatimentos = new CaixaDeTexto("Meus batimentos", "bpm", CaixaDeTexto.INPUT_NUMBER);
        mCaixaDeTextoBatimentos.setHint("000");
        mItems.add(mCaixaDeTextoBatimentos);

        mCaixaDeTextoPressao = new CaixaDeTexto("Minha pressão arterial", "mmHg", CaixaDeTexto.INPUT_TEXT);
        mCaixaDeTextoPressao.setHint("00x00");
        mItems.add(mCaixaDeTextoPressao);

        mItemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);

        mRecView.setAdapter(mItemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mButtonSave.setOnClickListener(new View.OnClickListener() {
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

    private void saveObject(){
        MedicaoDadosFisiologicos medicaoDadosFisiologicos = new MedicaoDadosFisiologicos();

        medicaoDadosFisiologicos.setWeigth(Formater.getFloatFromString(mCaixaDeTextoPeso.getValue()));
        medicaoDadosFisiologicos.setBloodPressure(mCaixaDeTextoPressao.getValue());
        medicaoDadosFisiologicos.setBpm(Formater.getIntegerFromString(mCaixaDeTextoBatimentos.getValue()));
        if(mRGInchaco.getCheckedRadioButtonId() != -1) {
            RadioButton radioButton = mRGInchaco.findViewById(mRGInchaco.getCheckedRadioButtonId());
            medicaoDadosFisiologicos.setSwelling(radioButton.getText().toString());
        }
        if (mRGFadiga.getCheckedRadioButtonId() != -1){
            RadioButton radioButton = mRGFadiga.findViewById(mRGFadiga.getCheckedRadioButtonId());
            medicaoDadosFisiologicos.setFatigue(radioButton.getText().toString());
        }

        medicaoDadosFisiologicos.setCreateDate((new Date()).getTime());
        medicaoDadosFisiologicos.setPerformed(true);

        saveIntoFirebase(medicaoDadosFisiologicos);
    }

    private boolean isValidForm (){
        boolean isValid = true;
        int i = 0;
        for (Item item : mItems){
            if (item.isEmpty()){
                Log.d("DEBUG-JP: ", ""+i);
                i++;
                isValid = false;
                break;
            }
        }
        Log.d("DEBUG-JP: ", mRGFadiga.isSelected()+", "+mRGInchaco.isSelected());
        return isValid && mRGFadiga.getCheckedRadioButtonId() != -1 && mRGInchaco.getCheckedRadioButtonId() != -1;
    }

    private void saveIntoFirebase (MedicaoDadosFisiologicos medicaoDadosFisiologicos){

        try{
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference().
                child((new Paciente()).getTipo()).
                child(PreferencesUtils.getString(getActivity(), getResources().getString(R.string.userKey))).
                child(medicaoDadosFisiologicos.getConstantId()).
                child(medicaoDadosFisiologicos.getType());
        Log.d("DEBUG-JP: ", mDatabaseReference.toString());
        medicaoDadosFisiologicos.setId(mDatabaseReference.push().getKey());
        mDatabaseReference.child(medicaoDadosFisiologicos.getId()).setValue(medicaoDadosFisiologicos);
        Toast.makeText(getActivity(), getResources().getString(R.string.message_succes_register_general), Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_register_general), Toast.LENGTH_SHORT).show();
        }

    }
}
