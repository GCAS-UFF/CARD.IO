package cardio.com.cardio.exerciseDialog.view;


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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.model.Exercicio;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.CheckboxListItem;
import cardio.com.cardio.common.model.view.DateTextBox;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.model.view.TextBox;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.exerciseDialog.model.ExerciseDialogModelImp;
import cardio.com.cardio.exerciseDialog.presenter.ExerciseDialogPresenter;
import cardio.com.cardio.exerciseDialog.presenter.ExerciseDialogPresenterImp;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class ExerciseDialogFragment extends android.support.v4.app.DialogFragment
        implements ExerciseDialogView, View.OnClickListener {

    public static final String ARG_PARAM1 = "id";
    public static final String ARG_PARAM2 = "date";

    private String mId;
    private String mDateStr;
    private ExerciseDialogPresenter mExerciseDialogPresenterImp;
    private ItemRecycleViewAdapter mItemRecycleViewAdapter;

    private RecyclerView mRecView;
    private Button mBtnOk;
    private Button mBtnCancel;
    private List<Item> mItems;
    private TextBox mTextBoxExercise;
    private TextBox mTextBoxIntensity;
    private TextBox mTextBoxDuration;
    private DateTextBox mHourTextBox;
    private CheckboxListItem mCheckboxListItem;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;

    public ExerciseDialogFragment() {
    }

    public static ExerciseDialogFragment newInstance (String id, String dateStr){
        ExerciseDialogFragment dialog = new ExerciseDialogFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, dateStr);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null){
            mId = bundle.getString(ARG_PARAM1);
            mDateStr = bundle.getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_exercises_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null){
            mExerciseDialogPresenterImp = new ExerciseDialogPresenterImp(new ExerciseDialogModelImp(context),this);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mBtnCancel = (Button) view.findViewById(R.id.btn_cancelar);
        mBtnCancel.setOnClickListener(this);

        mBtnOk = (Button) view.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);

        mItems = new ArrayList<>();

        mTextBoxExercise = new TextBox("Atividade física", "", TextBox.INPUT_TEXT);
        mTextBoxExercise.setEditable(false);
        mItems.add(mTextBoxExercise);

        mTextBoxIntensity = new TextBox("Intensidade", "", TextBox.INPUT_TEXT);
        mTextBoxIntensity.setHint("Leve");
        mItems.add(mTextBoxIntensity);

        mTextBoxDuration = new TextBox("Duração", "min", TextBox.INPUT_NUMBER);
        mTextBoxDuration.setHint("30");
        mItems.add(mTextBoxDuration);

        mHourTextBox = new DateTextBox(getResources().getString(R.string.medicine_performed_hour), DateTextBox.INPUT_TIME);
        mItems.add(mHourTextBox);

        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mItemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
        mItemRecycleViewAdapter.setFragmentManager(getFragmentManager());

        mRecView.setAdapter(mItemRecycleViewAdapter);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.very_round_background_shape);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mExerciseDialogPresenterImp.initializeExerciseInformation(mId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancelar:
                dismiss();
                break;
            case R.id.btn_ok:
                mExerciseDialogPresenterImp.onClickButtonOK();
                break;
        }
    }

    @Override
    public void populateSymtomsCheckList(Map<String, String> options) {
        Map<String, Boolean> symptoms = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : options.entrySet()){
            symptoms.put(entry.getKey(), false);
        }
        mCheckboxListItem = new CheckboxListItem(symptoms, "Sintomas");
        mItems.add(mCheckboxListItem);

        mItemRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showExerciseInformation(Recomentation recomentation) {
        Exercicio exercicio = (Exercicio) recomentation.getAction();

        mTextBoxExercise.setValue(exercicio.getName());

        mTextBoxIntensity.setValue(exercicio.getIntensity());
        mTextBoxIntensity.setEditable(true);

        mTextBoxDuration.setValue(String.valueOf(exercicio.getDuration()));

        mItemRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isFormValid() {
        boolean isValid = true;
        for (Item item : mItems){
            if (item.isEmpty()){
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    @Override
    public Recomentation getRecomendation() throws ParseException {
        Exercicio exercicio = new Exercicio();

        exercicio.setName(mTextBoxExercise.getValue());
        exercicio.setIntensity(mTextBoxIntensity.getValue());
        exercicio.setDuration(Integer.valueOf(mTextBoxDuration.getValue()));
        exercicio.setSymptons(mCheckboxListItem.getOptions());

        Recomentation recomentation = new Recomentation();
        recomentation.setAction(exercicio);
        recomentation.getAction().setExecutedDate((Formater.getDateFromStringDateAndTime(mDateStr, mHourTextBox.getValue())).getTime());

        return recomentation;
    }

    @Override
    public void showMessage(int res) {
        Toast.makeText(getContext(), getResources().getString(res), Toast.LENGTH_SHORT).show();
    }
}
