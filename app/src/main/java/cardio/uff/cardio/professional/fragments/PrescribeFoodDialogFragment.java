package cardio.uff.cardio.professional.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.uff.cardio.common.model.model.Alimentacao;
import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.model.view.DateTextBox;
import cardio.uff.cardio.common.model.view.Item;
import cardio.uff.cardio.common.model.view.TextBox;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.professional.ComunicatorFragmentActivity;

public class PrescribeFoodDialogFragment extends android.support.v4.app.DialogFragment {

    private RecyclerView mRecView;
    private TextBox quantityTextBox;
    private DateTextBox startDateTextBox;
    private DateTextBox finishDateTextBox;
    private Button mBtnCancelar;
    private Button mBtnOk;
    private List<Item> mItems;

    private ComunicatorFragmentActivity comunicatorFragmentActivity;

    public PrescribeFoodDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_food_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context != null){
            comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mBtnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);


        mItems = new ArrayList<>();

        quantityTextBox = new TextBox(getString(R.string.food_prescription_label), getString(R.string.food_prescription_unit), TextBox.INPUT_NUMBER);
        mItems.add(quantityTextBox);

        startDateTextBox = new DateTextBox(getString(R.string.startDate_label), DateTextBox.INPUT_DATE);
        mItems.add(startDateTextBox);

        finishDateTextBox = new DateTextBox(getString(R.string.finishDate_label), DateTextBox.INPUT_DATE);
        mItems.add(finishDateTextBox);

        ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
        itemRecycleViewAdapter.setFragmentManager(getFragmentManager());

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (isFormValid()){
                        saveObject();
                    }
                    else {
                        Toast.makeText(getContext(), getResources().getString(R.string.message_error_field_empty), Toast.LENGTH_SHORT).show();
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
        });

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.very_round_background_shape);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    private void saveObject() throws ParseException {

        Alimentacao alimentacao = new Alimentacao();
        alimentacao.setQuantidade(Formater.getIntegerFromString(quantityTextBox.getValue()));

        Recomentation recomentation = new Recomentation();
        recomentation.setAction(alimentacao);
        recomentation.setFrequencyByDay(1);
        recomentation.setStartDate(Formater.getDateFromString(startDateTextBox.getValue()).getTime());
        recomentation.setFinishDate(Formater.getDateFromString(finishDateTextBox.getValue()).getTime());

        saveIntoFirebase(recomentation);
    }


    private boolean isFormValid(){
        boolean isValid = true;
        for (Item item : mItems){
            if (item.isEmpty()){
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private void saveIntoFirebase(Recomentation recomentation){

        try {
            DatabaseReference mDbRef = FirebaseHelper.getInstance()
                    .getPatientDatabaseReference(comunicatorFragmentActivity.getPatientSelected().getId())
                    .child(FirebaseHelper.RECOMMENDED_ACTION_KEY)
                    .child(FirebaseHelper.ALIMENTACAO_KEY);

            recomentation.setId(mDbRef.push().getKey());
            mDbRef.child(recomentation.getId()).setValue(recomentation);
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.QUANTITY_KEY).setValue(((Alimentacao) recomentation.getAction()).getQuantidade());
            Toast.makeText(getActivity(), getResources().getString(R.string.message_success_recomendation), Toast.LENGTH_SHORT).show();
            dismiss();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_recomendation), Toast.LENGTH_SHORT).show();
        }

    }
}
