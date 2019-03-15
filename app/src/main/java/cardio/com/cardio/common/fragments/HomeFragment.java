package cardio.com.cardio.common.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cardio.com.cardio.R;

public class HomeFragment extends Fragment {

    private LinearLayout mLlPesagem;
    private LinearLayout mLlAlimentacao;
    private LinearLayout mLlMedicamentos;
    private LinearLayout mLlConsultas;
    private LinearLayout mLlExercicios;
    private LinearLayout mLlSobre;
    private LinearLayout mLlOrientation;
    private LinearLayout mLlHelp;
    private ComunicadorHomeActivity comunicadorHomeActivity;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        comunicadorHomeActivity = (ComunicadorHomeActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLlPesagem = (LinearLayout) view.findViewById(R.id.ll_controle_peso);
        mLlPesagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTelaHome(R.id.ll_controle_peso);
            }
        });

        mLlAlimentacao = (LinearLayout) view.findViewById(R.id.ll_alimentacao);
        mLlAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTelaHome(R.id.ll_alimentacao);
            }
        });

        mLlMedicamentos = (LinearLayout) view.findViewById(R.id.ll_medicacoes);
        mLlMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTelaHome(R.id.ll_medicacoes);
            }
        });

        mLlConsultas = (LinearLayout) view.findViewById(R.id.ll_consultas);
        mLlConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTelaHome(R.id.ll_consultas);
            }
        });

        mLlExercicios = (LinearLayout) view.findViewById(R.id.ll_exercicios);
        mLlExercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTelaHome(R.id.ll_exercicios);
            }
        });

        mLlSobre = (LinearLayout) view.findViewById(R.id.ll_sobre);
        mLlSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTelaHome(R.id.ll_sobre);
            }
        });

        mLlOrientation = (LinearLayout) view.findViewById(R.id.ll_orientation);
        mLlOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTelaHome(R.id.ll_orientation);
            }
        });

        mLlHelp = (LinearLayout) view.findViewById(R.id.ll_help);
        mLlHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTelaHome(R.id.ll_help);
            }
        });
    }

    public interface ComunicadorHomeActivity {
        void trocaTelaHome(int resId);
    }
}
