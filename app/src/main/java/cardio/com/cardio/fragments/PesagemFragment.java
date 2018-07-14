package cardio.com.cardio.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.PreferencesUtils;
import cardio.com.cardio.R;
import cardio.com.cardio.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.model.CaixaDeTexto;
import cardio.com.cardio.model.Item;

public class PesagemFragment extends Fragment {

    private RecyclerView mRecView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    public PesagemFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pesagem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);

        List<Item> items = new ArrayList<>();

        CaixaDeTexto caixaDeTextoPeso = new CaixaDeTexto("Meu peso", "kg", CaixaDeTexto.INPUT_DECIMAL);
        caixaDeTextoPeso.setHint("00.0");
        items.add(caixaDeTextoPeso);

        CaixaDeTexto caixaDeTextoBatimentos = new CaixaDeTexto("Meus batimentos", "bpm", CaixaDeTexto.INPUT_NUMBER);
        caixaDeTextoBatimentos.setHint("000");
        items.add(caixaDeTextoBatimentos);

        CaixaDeTexto caixaDeTextoPressao = new CaixaDeTexto("Minha pressão arterial", "mmHg", CaixaDeTexto.INPUT_TEXT);
        caixaDeTextoPressao.setHint("00x00");
        items.add(caixaDeTextoPressao);

        ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(items);

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initFirebase();
    }

    private void initFirebase (){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        Log.d("debug_kelly", PreferencesUtils.getString(getActivity(), getResources().getString(R.string.patientKey)));
    }
}
