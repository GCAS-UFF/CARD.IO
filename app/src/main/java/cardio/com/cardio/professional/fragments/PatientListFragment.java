package cardio.com.cardio.professional.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cardio.com.cardio.R;
import cardio.com.cardio.patiente.fragments.DialogFragment;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class PatientListFragment extends Fragment {

    private RecyclerView mRecVwListPatient;
    private RelativeLayout mRlAddPatient;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;

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

        mRecVwListPatient = (RecyclerView) view.findViewById(R.id.recycle_view);
        mRlAddPatient = (RelativeLayout) view.findViewById(R.id.rl_add_medicamento);

        mRlAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicatorFragmentActivity.trocaTela(R.layout.fragment_register_patient);
            }
        });
    }
}
