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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.uff.cardio.common.model.model.Consulta;
import cardio.uff.cardio.common.model.view.DateTextBox;
import cardio.uff.cardio.common.model.view.DropDown;
import cardio.uff.cardio.common.model.view.Item;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.professional.ComunicatorFragmentActivity;

public class PrescribeAppointmentDialogFragment extends android.support.v4.app.DialogFragment {

    private RecyclerView mRecView;
    private DateTextBox mDateTextBox;
    private DateTextBox mHourTextBox;
    private DropDown mAdressDropDown;
    private DropDown mSpecialityDropDown;
    private Button mBtnCancelar;
    private Button mBtnOk;
    private List<Item> mItems;

    private ComunicatorFragmentActivity comunicatorFragmentActivity;

    public PrescribeAppointmentDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_appointment_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null)
            comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mBtnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);

        mItems = new ArrayList<>();

        mDateTextBox = new DateTextBox(getString(R.string.appointment_date_label), DateTextBox.INPUT_DATE);
        mItems.add(mDateTextBox);

        mHourTextBox = new DateTextBox(getString(R.string.appointment_hour_label), DateTextBox.INPUT_TIME);
        mItems.add(mHourTextBox);

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

        FirebaseHelper.adressDatabaseReference.addListenerForSingleValueEvent(getAdressMetadata);
        FirebaseHelper.specialitiesDatabaseReference.addListenerForSingleValueEvent(getSpecialityMetadata);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.very_round_background_shape);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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
    private void saveObject() throws ParseException {

        Consulta consulta = new Consulta();

        consulta.setData(Formater.getDateFromStringDateAndTime(mDateTextBox.getValue(),
                mHourTextBox.getValue()).getTime());
        consulta.setLocalizacao(mAdressDropDown.getValue());
        consulta.setEspecialideProfissional(mSpecialityDropDown.getValue());
        consulta.setPaciente(getCurrentPatientKey());
        consulta.setNotificationId(Formater.createRandomID());
//        consulta.setProfissional(PreferencesUtils.getString(getActivity(), FirebaseHelper.USER_KEY));

        saveIntoFirebase(consulta);
    }
    private void saveIntoFirebase(Consulta consulta){

        try {
            DatabaseReference mDbRef = FirebaseHelper.appointmentDatabaseReference;

            consulta.setId(mDbRef.push().getKey());
            mDbRef.child(consulta.getId()).setValue(consulta);

            Toast.makeText(getActivity(), getResources().getString(R.string.message_success_appointment), Toast.LENGTH_SHORT).show();
            dismiss();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_appointment), Toast.LENGTH_SHORT).show();
        }

    }
    private ValueEventListener getAdressMetadata = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Map<String, String> options = new HashMap<>();

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()){
                options.put(entrySnapshot.getKey(), entrySnapshot.getValue(String.class));
            }

            mAdressDropDown = new DropDown(options, "Endereço");
            mItems.add(mAdressDropDown);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private ValueEventListener getSpecialityMetadata = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Map<String, String> options = new LinkedHashMap<>();

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()){
                options.put(entrySnapshot.getKey(), entrySnapshot.getValue(String.class));
            }

            mSpecialityDropDown = new DropDown(options, "Especialidade");
            mItems.add(mSpecialityDropDown);

            ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
            itemRecycleViewAdapter.setFragmentManager(getFragmentManager());

            mRecView.setAdapter(itemRecycleViewAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(getContext(), PreferencesUtils.CURRENT_PATIENT_KEY);
    }
}