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
import cardio.com.cardio.common.model.view.CaixaDeTexto;
import cardio.com.cardio.common.model.view.CaixaDeTextoData;
import cardio.com.cardio.common.model.view.Item;

public class DialogFragment extends android.support.v4.app.DialogFragment {

    private RecyclerView mRecView;
    private Button mBtnCancelar;
    private Button mBtnOk;

    public DialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mBtnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);


        List<Item> items = new ArrayList<>();

        CaixaDeTexto caixaDeTextoNome = new CaixaDeTexto("Nome Medicamento", "", CaixaDeTexto.INPUT_TEXT);
        items.add(caixaDeTextoNome);

        CaixaDeTexto caixaDeTextoDosagem = new CaixaDeTexto("Dosagem", "", CaixaDeTexto.INPUT_TEXT);
        items.add(caixaDeTextoDosagem);

        CaixaDeTextoData caixaDeTextoDataInicial = new CaixaDeTextoData("Data Inicial", CaixaDeTextoData.INPUT_DATE);
        items.add(caixaDeTextoDataInicial);

        CaixaDeTextoData caixaDeTextoTempoInicial = new CaixaDeTextoData("Hora Inicial", CaixaDeTextoData.INPUT_TIME);
        items.add(caixaDeTextoTempoInicial);

        CaixaDeTexto caixaDeTextoVezesDia = new CaixaDeTexto("Vezes Ao Dia", "", CaixaDeTexto.INPUT_NUMBER);
        items.add(caixaDeTextoVezesDia);

        CaixaDeTextoData caixaDeTextoDataFim = new CaixaDeTextoData("Data Fim", CaixaDeTextoData.INPUT_DATE);
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

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_muito_redondo_fundo);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
