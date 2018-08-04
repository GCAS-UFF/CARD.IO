package cardio.com.cardio.professional.fragments;


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
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.adapters.ItemExpandableListAdapter;
import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class PatientListFragment extends Fragment {

    private RecyclerView mRecVwListPatient;
    private RelativeLayout mRlAddPatient;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;
    private ItemExpandableListAdapter itemExpandableListAdapter;


    public PatientListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patient_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecVwListPatient = (RecyclerView) view.findViewById(R.id.recycle_view_prescribe_food);
        mRecVwListPatient.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemExpandableListAdapter = new ItemExpandableListAdapter(comunicatorExpandableItem);
        mRecVwListPatient.setAdapter(itemExpandableListAdapter);

        mRlAddPatient = (RelativeLayout) view.findViewById(R.id.rl_add_medicamento);

        mRlAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicatorFragmentActivity.trocaTela(R.layout.fragment_register_patient);
            }
        });

        populatePpatientList();
    }

    public void populatePpatientList(){
        FirebaseHelper.getInstance().getCurrentPatientListDatabaseReference().
                addValueEventListener(pacientListEventListener);

    }

    private ValueEventListener pacientListEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            itemExpandableListAdapter.cleanList();

            for (DataSnapshot pacientSnapshot: dataSnapshot.getChildren()){

                String id = pacientSnapshot.getValue(String.class);
                if (id != null){
                    FirebaseHelper.getInstance().
                            getPatientDatabaseReference(id).addValueEventListener(patientEventListener);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener patientEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Paciente paciente = dataSnapshot.getValue(Paciente.class);
            paciente.setId(dataSnapshot.getKey());

            itemExpandableListAdapter.addPacient(paciente);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private ItemExpandableListAdapter.ComunicatorExpandableItem comunicatorExpandableItem =
            new ItemExpandableListAdapter.ComunicatorExpandableItem() {
        @Override
        public void disassociatePatient(final Paciente paciente) {
//            Log.d("DEBUG-JP",FirebaseHelper.getInstance().
//                    getCurrentPatientListDatabaseReference().child(paciente.getId()).gettoString());
            FirebaseHelper.getInstance().
                    getCurrentPatientListDatabaseReference().child(paciente.getId()).removeValue();

        }

        @Override
        public void editPatient(Paciente paciente) {
            comunicatorFragmentActivity.setPatientSelected(paciente);
            comunicatorFragmentActivity.trocaTela(R.layout.fragment_home);
        }
    };


}
