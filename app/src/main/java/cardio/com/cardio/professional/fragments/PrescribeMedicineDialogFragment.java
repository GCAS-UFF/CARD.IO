package cardio.com.cardio.professional.fragments;


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

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseConfig;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.model.Medicamento;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.TextBox;
import cardio.com.cardio.common.model.view.DateTextBox;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class PrescribeMedicineDialogFragment extends android.support.v4.app.DialogFragment {

    private RecyclerView mRecView;
    private TextBox mNameTextBox;
    private TextBox mDosageTextBox;
    private TextBox mQuantityTextBox;
    private DateTextBox mStartDateTextBox;
    private DateTextBox mStartHourTextBox;
    private TextBox mFrequencyTextBox;
    private DateTextBox mFinishDateTextBox;
    private TextBox mNoteTextBox;
    private Button mBtnCancelar;
    private Button mBtnOk;
    private List<Item> mItems;

    private ComunicatorFragmentActivity comunicatorFragmentActivity;

    public PrescribeMedicineDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_medicine_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null) {
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

        mNameTextBox = new TextBox(getResources().getString(R.string.medicine_name_label), "", TextBox.INPUT_TEXT);
        mItems.add(mNameTextBox);

        mDosageTextBox = new TextBox(getResources().getString(R.string.medicine_dosage_label), "", TextBox.INPUT_TEXT);
        mItems.add(mDosageTextBox);

        mQuantityTextBox = new TextBox(getResources().getString(R.string.medicine_quantity_label), "", TextBox.INPUT_TEXT);
        mItems.add(mQuantityTextBox);

        mStartDateTextBox = new DateTextBox(getResources().getString(R.string.startDate_label), DateTextBox.INPUT_DATE);
        mItems.add(mStartDateTextBox);

        mStartHourTextBox = new DateTextBox(getResources().getString(R.string.startHour_label), DateTextBox.INPUT_TIME);
        mItems.add(mStartHourTextBox);

        mFrequencyTextBox = new TextBox(getResources().getString(R.string.frequency_label), getResources().getString(R.string.frequency_unit), TextBox.INPUT_NUMBER);
        mItems.add(mFrequencyTextBox);

        mFinishDateTextBox = new DateTextBox(getResources().getString(R.string.finishDate_label), DateTextBox.INPUT_DATE);
        mItems.add(mFinishDateTextBox);

        mNoteTextBox = new TextBox(getResources().getString(R.string.medicine_note_label), "", TextBox.INPUT_TEXT);
        mItems.add(mNoteTextBox);

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

        Medicamento medicamento = new Medicamento();

        medicamento.setName(mNameTextBox.getValue());
        medicamento.setDosagem(mDosageTextBox.getValue());
        medicamento.setQuantidade(mQuantityTextBox.getValue());
        medicamento.setHorario(mStartHourTextBox.getValue());
        medicamento.setNote(mNoteTextBox.getValue());
        medicamento.setProfissionalId(FirebaseConfig.getFirebaseAuth().getUid());

        Recomentation recomentation = new Recomentation();
        recomentation.setAction(medicamento);
        recomentation.setFrequencyByDay(Formater.getIntegerFromString(mFrequencyTextBox.getValue()));
        recomentation.setStartDate(Formater.getDateFromString(mStartDateTextBox.getValue()).getTime());
        recomentation.setFinishDate(Formater.getDateFromString(mFinishDateTextBox.getValue()).getTime());

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
                    .child(FirebaseHelper.MEDICINE_KEY);

            recomentation.setId(mDbRef.push().getKey());
            mDbRef.child(recomentation.getId()).setValue(recomentation);
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.MEDICINE_NAME_KEY).setValue(((Medicamento) recomentation.getAction()).getName());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.MEDICINE_DOSAGE_KEY).setValue(((Medicamento) recomentation.getAction()).getDosagem());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.QUANTITY_KEY).setValue(((Medicamento) recomentation.getAction()).getQuantidade());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.MEDICINE_NOTE_KEY).setValue(((Medicamento) recomentation.getAction()).getNote());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.MEDICINE_START_HOUR_KEY).setValue(((Medicamento) recomentation.getAction()).getHorario());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.MEDICINE_PROFESSIONAL_KEY).setValue(((Medicamento) recomentation.getAction()).getProfissionalId());
            Toast.makeText(getActivity(), getResources().getString(R.string.message_success_recomendation), Toast.LENGTH_SHORT).show();
            dismiss();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_recomendation), Toast.LENGTH_SHORT).show();
        }

    }
}

