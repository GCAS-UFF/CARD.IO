package cardio.com.cardio.common.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

import cardio.com.cardio.R;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class AboutFragment extends Fragment {

    private ComunicatorFragmentActivity comunicatorFragmentActivity;
    private TextView mTvContributors;
    private TextView mTvDevelopers;
    private TextView mTvAboutText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvAboutText = (TextView) view.findViewById(R.id.tv_about_text);
//        mTvAboutText.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);

        mTvContributors = (TextView) view.findViewById(R.id.tv_contiburtors);
        mTvContributors.setText(Arrays.toString(getResources().getStringArray(R.array.contributors)).replaceAll("\\[|\\]|\\,", "\n"));

        mTvDevelopers = (TextView) view.findViewById(R.id.tv_developers);
        mTvDevelopers.setText(Arrays.toString(getResources().getStringArray(R.array.developers)).replaceAll("\\[|\\]|\\,", "\n"));
    }
}
