package cardio.uff.cardio.patiente.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cardio.uff.cardio.R;

public class MedicalAppontimentsFragment extends Fragment {

    private RecyclerView mRecView;
    private DatabaseReference mFireBase;

    public MedicalAppontimentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view_consultas);
        //List<Consulta> consultasAgendadas = new ArrayList<>();
        mFireBase = FirebaseDatabase.getInstance().getReference().child("Consulta");


    }
}
