package cardio.com.cardio.patiente.fragments;


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

import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.view.TextBox;
import cardio.com.cardio.common.model.view.DateTextBox;
import cardio.com.cardio.common.model.view.Item;

public class PrescribeMedicineDialogFragment extends android.support.v4.app.DialogFragment {

    private RecyclerView mRecView;
    private Button mBtnCancelar;
    private Button mBtnOk;

    public PrescribeMedicineDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_medicine_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mBtnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);


        List<Item> items = new ArrayList<>();

        TextBox caixaDeTextoNome = new TextBox("Nome Medicamento", "", TextBox.INPUT_TEXT);
        items.add(caixaDeTextoNome);

        TextBox caixaDeTextoDosagem = new TextBox("Dosagem", "", TextBox.INPUT_TEXT);
        items.add(caixaDeTextoDosagem);

        DateTextBox caixaDeTextoDataInicial = new DateTextBox("Data Inicial", DateTextBox.INPUT_DATE);
        items.add(caixaDeTextoDataInicial);

        DateTextBox caixaDeTextoTempoInicial = new DateTextBox("Hora Inicial", DateTextBox.INPUT_TIME);
        items.add(caixaDeTextoTempoInicial);

        TextBox caixaDeTextoVezesDia = new TextBox("Vezes Ao Dia", "", TextBox.INPUT_NUMBER);
        items.add(caixaDeTextoVezesDia);

        DateTextBox caixaDeTextoDataFim = new DateTextBox("Data Fim", DateTextBox.INPUT_DATE);
        items.add(caixaDeTextoDataFim);

        ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(items);
        itemRecycleViewAdapter.setFragmentManager(getFragmentManager());

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.very_round_background_shape);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
