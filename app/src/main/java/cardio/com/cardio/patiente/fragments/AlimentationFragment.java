package cardio.com.cardio.patiente.fragments;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.view.DropDown;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.model.Alimentacao;
import cardio.com.cardio.common.model.view.TextBox;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class AlimentationFragment extends Fragment {

    private RecyclerView mRecView;
    private ItemRecycleViewAdapter mItemRecycleViewAdapter;
    private Button mBtnSave;
    private TextBox mCaixaDeTextoFood;
    private TextBox mCaixaDeTextoQuantity;
    private DropDown mUnityDropDown;
    private List<Item> mItems;
    private RelativeLayout mRlButtonHistory;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;

    public AlimentationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alimentacao, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view_input);
        mBtnSave = (Button) view.findViewById(R.id.btn_save);
        mItems = new ArrayList<>();

        mCaixaDeTextoFood = new TextBox("Líquido", "", TextBox.INPUT_TEXT);
        mCaixaDeTextoFood.setHint("Água");
        mItems.add(mCaixaDeTextoFood);

        mCaixaDeTextoQuantity = new TextBox("Quantidade", "", TextBox.INPUT_NUMBER);
        mCaixaDeTextoQuantity.setHint("100");
        mItems.add(mCaixaDeTextoQuantity);

        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseHelper.quantidadeBebidasDatabaseReference.addListenerForSingleValueEvent(getBeverageQuantityMetadata);

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

        mRlButtonHistory = (RelativeLayout) view.findViewById(R.id.rl_history);
        mRlButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicatorFragmentActivity.trocaTela(R.id.ll_alimentacao);
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
        alimentacao.setFood(mCaixaDeTextoFood.getValue());
        alimentacao.setQuantidade(
                Formater.getIntegerFromString(mCaixaDeTextoQuantity.getValue()) * Formater.getIntegerFromString(mUnityDropDown.getValue())
        );

        alimentacao.setExecutedDate((new Date()).getTime());
        alimentacao.setPerformed(true);

        saveIntoFirebase(alimentacao);
    }
    private void saveIntoFirebase (Alimentacao alimentacao){

        try{
            DatabaseReference mDatabaseReference = FirebaseHelper.getInstance().getCurrentPatientDatabaseReference().
                    child(alimentacao.getConstantId()).
                    child(alimentacao.getType());

            alimentacao.setId(mDatabaseReference.push().getKey());
            mDatabaseReference.child(alimentacao.getId()).setValue(alimentacao);
            Toast.makeText(getActivity(), getResources().getString(R.string.message_succes_register_general), Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_register_general), Toast.LENGTH_SHORT).show();
        }

    }

    private ValueEventListener getBeverageQuantityMetadata = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Map<String, String> options = new LinkedHashMap<>();

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()){
                options.put(String.valueOf(entrySnapshot.getValue()), entrySnapshot.getKey());
            }

            mUnityDropDown = new DropDown(options, "Medida");
            mItems.add(mUnityDropDown);

            ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
            itemRecycleViewAdapter.setFragmentManager(getFragmentManager());

            mRecView.setAdapter(itemRecycleViewAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}