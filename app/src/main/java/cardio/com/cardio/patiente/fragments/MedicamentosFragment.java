package cardio.com.cardio.patiente.fragments;


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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ListaMedicamentosRecycleViewAdapter;
import cardio.com.cardio.common.model.model.ListaMedicamento;
import cardio.com.cardio.common.model.model.Medicamento;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicamentosFragment extends Fragment {

    private RecyclerView mRecVwListasMedicamentos;
    private RelativeLayout mRlAddMedicamento;

    public MedicamentosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meidcamentos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecVwListasMedicamentos = (RecyclerView) view.findViewById(R.id.recycle_view);
        mRlAddMedicamento = (RelativeLayout) view.findViewById(R.id.rl_add_medicamento);

        mRlAddMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
            }
        });

        List<Medicamento> medicamentos = new ArrayList<>();
        medicamentos.add(new Medicamento("Levoid", "50mg", (new Date()).getTime()));
        medicamentos.add(new Medicamento("Levoid", "50mg", (new Date()).getTime()));
        medicamentos.add(new Medicamento("Levoid", "50mg", (new Date()).getTime()));

        List<ListaMedicamento> listaMedicamentos = new ArrayList<>();
        listaMedicamentos.add(new ListaMedicamento("Manhã", medicamentos));
        listaMedicamentos.add(new ListaMedicamento("Tarde", medicamentos));
        listaMedicamentos.add(new ListaMedicamento("Noite", medicamentos));

        ListaMedicamentosRecycleViewAdapter listaMedicamentosRecycleViewAdapter =
                new ListaMedicamentosRecycleViewAdapter(listaMedicamentos, getActivity());

        mRecVwListasMedicamentos.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecVwListasMedicamentos.setAdapter(listaMedicamentosRecycleViewAdapter);
        }
}
