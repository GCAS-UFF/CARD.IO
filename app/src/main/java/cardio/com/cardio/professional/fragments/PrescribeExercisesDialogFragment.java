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
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.model.Exercicio;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.DateTextBox;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.model.view.TextBox;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class PrescribeExercisesDialogFragment extends  android.support.v4.app.DialogFragment {

    private RecyclerView mRecView;
    private TextBox exerciseTextBox;
    private TextBox frequencyTextBox;
    private TextBox intensityTextBox;
    private TextBox durationTextBox;
    private DateTextBox startDateTextBox;
    private DateTextBox finishDateTextBox;
    private Button mBtnCancelar;
    private Button mBtnOk;
    private List<Item> mItems;

    private ComunicatorFragmentActivity comunicatorFragmentActivity;

    public PrescribeExercisesDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_exercises_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null)
            comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mBtnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);


        mItems = new ArrayList<>();

        exerciseTextBox = new TextBox(getString(R.string.exercise_prescription_label), "", TextBox.INPUT_TEXT);
        mItems.add(exerciseTextBox);

        frequencyTextBox = new TextBox(getString(R.string.frequency_label), getString(R.string.frequency_unit), TextBox.INPUT_NUMBER);
        mItems.add(frequencyTextBox);

        intensityTextBox = new TextBox(getString(R.string.exercise_intensity_label), "", TextBox.INPUT_TEXT);
        mItems.add(intensityTextBox);

        durationTextBox = new TextBox(getString(R.string.exercise_duration_label), getString(R.string.exercise_duration_unit), TextBox.INPUT_NUMBER);
        mItems.add(durationTextBox);

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

        Exercicio exercicio = new Exercicio();
        exercicio.setName(exerciseTextBox.getValue());
        exercicio.setIntensity(intensityTextBox.getValue());
        exercicio.setDuration(Formater.getIntegerFromString(durationTextBox.getValue()));

        Recomentation recomentation = new Recomentation();
        recomentation.setAction(exercicio);
        recomentation.setFrequencyByDay(Formater.getIntegerFromString(frequencyTextBox.getValue()));
        recomentation.setStartDate(Formater.getDateFromString(startDateTextBox.getValue()).getTime());
        recomentation.setFinishDate(Formater.getDateFromString(finishDateTextBox.getValue()).getTime());

        saveIntoFirebase(recomentation);
    }

    public boolean isFormValid(){
        boolean isValid = true;

        for(Item item : mItems)
            if (item.isEmpty()) {
                isValid = false;
                break;
            }
        return  isValid;
    }

    private void saveIntoFirebase(Recomentation recomentation){

        try {
            DatabaseReference mDbRef = FirebaseHelper.getInstance()
                    .getPatientDatabaseReference(comunicatorFragmentActivity.getPatientSelected().getId())
                    .child(FirebaseHelper.RECOMMENDED_ACTION_KEY)
                    .child(FirebaseHelper.EXERCICIO_KEY);

            recomentation.setId(mDbRef.push().getKey());
            mDbRef.child(recomentation.getId()).setValue(recomentation);
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.EXERCISE_NAME_KEY).setValue(((Exercicio) recomentation.getAction()).getName());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.EXERCISE_INTENSITY_KEY).setValue(((Exercicio) recomentation.getAction()).getIntensity());
            mDbRef.child(recomentation.getId()).child(FirebaseHelper.EXERCISE_DURATION_KEY).setValue(((Exercicio) recomentation.getAction()).getDuration());
            Toast.makeText(getActivity(), getResources().getString(R.string.message_success_recomendation), Toast.LENGTH_SHORT).show();
            dismiss();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_recomendation), Toast.LENGTH_SHORT).show();
        }

    }
}