package cardio.com.cardio.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cardio.com.cardio.R;
import cardio.com.cardio.model.ListaMedicamento;

public class HomeFragment extends Fragment {

    private LinearLayout mLlPesagem;
    private LinearLayout mLlMedicamentos;
    private LinearLayout mLlSobre;
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
                comunicadorHomeActivity.trocaTela(R.id.ll_controle_peso);
            }
        });

        mLlMedicamentos = (LinearLayout) view.findViewById(R.id.ll_medicacoes);
        mLlMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTela(R.id.ll_medicacoes);
            }
        });

        mLlSobre = (LinearLayout) view.findViewById(R.id.ll_sobre);
        mLlSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorHomeActivity.trocaTela(R.id.ll_sobre);
            }
        });
    }

    public interface ComunicadorHomeActivity {
        void trocaTela( int resId);
    }
}
