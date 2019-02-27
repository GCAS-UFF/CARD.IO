package cardio.com.cardio.medicineDialog.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import cardio.com.cardio.common.model.view.DateTextBox;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.model.view.TextBox;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.medicineDialog.model.MedicineDialogModelImp;
import cardio.com.cardio.medicineDialog.presenter.MedicineDialogPresenter;
import cardio.com.cardio.medicineDialog.presenter.MedicineDialogPresenterImp;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class DialogAddPerformMedicineFragment extends android.support.v4.app.DialogFragment implements MedicineDialogView {

    public static final String ARG_PARAM1 = "id";
    public static final String ARG_PARAM2 = "date";

    private String mId;
    private String mDateStr;
    private MedicineDialogPresenter mMedicineDialogPresenter;
    private ItemRecycleViewAdapter mItemRecycleViewAdapter;

    private RecyclerView mRecView;
    private TextBox mNameTextBox;
    private TextBox mDosageTextBox;
    private TextBox mQuantityTextBox;
    private TextBox mNoteTextBox;
    private TextBox mDateTextBox;
    private DateTextBox mHourTextBox;
    private Button mBtnCancelar;
    private Button mBtnOk;
    private List<Item> mItems;

    private ComunicatorFragmentActivity comunicatorFragmentActivity;

    public static DialogAddPerformMedicineFragment newInstance (String id, String dateStr){
        DialogAddPerformMedicineFragment dialog = new DialogAddPerformMedicineFragment();

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
        return inflater.inflate(R.layout.fragment_prescribe_medicine_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null) {
            mMedicineDialogPresenter = new MedicineDialogPresenterImp(this, new MedicineDialogModelImp(context));
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
        mNameTextBox.setEditable(false);
        mItems.add(mNameTextBox);

        mDosageTextBox = new TextBox(getResources().getString(R.string.medicine_dosage_label), "", TextBox.INPUT_TEXT);
        mDosageTextBox.setEditable(false);
        mItems.add(mDosageTextBox);

        mQuantityTextBox = new TextBox(getResources().getString(R.string.medicine_quantity_label), "", TextBox.INPUT_TEXT);
        mQuantityTextBox.setEditable(false);
        mItems.add(mQuantityTextBox);

        mDateTextBox = new TextBox(getResources().getString(R.string.medicine_performed_date), "", TextBox.INPUT_TEXT);
        mDateTextBox.setEditable(false);
        mItems.add(mDateTextBox);

        mHourTextBox = new DateTextBox(getResources().getString(R.string.medicine_performed_hour), DateTextBox.INPUT_TIME);
        mItems.add(mHourTextBox);

        mNoteTextBox = new TextBox(getResources().getString(R.string.medicine_note_label), "", TextBox.INPUT_TEXT);
        mNoteTextBox.setEditable(false);
        mItems.add(mNoteTextBox);

        mItemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
        mItemRecycleViewAdapter.setFragmentManager(getFragmentManager());

        mRecView.setAdapter(mItemRecycleViewAdapter);
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

        mMedicineDialogPresenter.initializeMedicineInformation(mId);


    }


    private void saveObject() throws ParseException {

        Medicamento medicamento = new Medicamento();

        medicamento.setName(mNameTextBox.getValue());
        medicamento.setDosagem(mDosageTextBox.getValue());
        medicamento.setQuantidade(mQuantityTextBox.getValue());
        medicamento.setNote(mNoteTextBox.getValue());

        Recomentation recomentation = new Recomentation();
        recomentation.setAction(medicamento);
        recomentation.getAction().setExecutedDate((Formater.getDateFromStringDateAndTime(mDateStr, mHourTextBox.getValue())).getTime());

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
                    .child(FirebaseHelper.PERFORMED_ACTION_KEY)
                    .child(FirebaseHelper.MEDICINE_KEY);

            recomentation.setId(mDbRef.push().getKey());
            mDbRef.child(recomentation.getId()).setValue(recomentation.getAction());
            Toast.makeText(getActivity(), getResources().getString(R.string.message_success_recomendation), Toast.LENGTH_SHORT).show();
            dismiss();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_recomendation), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showMedicineInformation(Recomentation recomentation) {
        Medicamento medicamento = (Medicamento) recomentation.getAction();
        mNameTextBox.setValue(medicamento.getName());
        mDosageTextBox.setValue(medicamento.getDosagem());
        mQuantityTextBox.setValue(medicamento.getQuantidade());
        mNoteTextBox.setValue(medicamento.getNote());
        mDateTextBox.setValue(mDateStr);
        Log.d("DEBUN_JP", medicamento.getName());

        mItemRecycleViewAdapter.notifyDataSetChanged();
    }
}

