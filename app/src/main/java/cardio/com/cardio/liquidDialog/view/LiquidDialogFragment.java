package cardio.com.cardio.liquidDialog.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cardio.com.cardio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiquidDialogFragment extends Fragment {


    public LiquidDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liquid_dialog, container, false);
    }

}
