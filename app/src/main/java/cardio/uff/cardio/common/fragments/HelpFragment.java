package cardio.uff.cardio.common.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cardio.uff.cardio.R;
import cardio.uff.cardio.professional.ComunicatorFragmentActivity;


public class HelpFragment extends Fragment {

    private ComunicatorFragmentActivity comunicatorFragmentActivity;
    private Button btnTestar;

    public HelpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnTestar = (Button) view.findViewById(R.id.btnTesteFirebase);

        btnTestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
