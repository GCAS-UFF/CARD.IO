package cardio.uff.cardio.common.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.uff.cardio.common.model.view.Item;
import cardio.uff.cardio.common.model.view.OrientationItem;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.professional.ComunicatorFragmentActivity;


public class HelpFragment extends Fragment {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;
    private List<Item> helpList;
    private ItemRecycleViewAdapter mItemRecycleViewAdapter;


    public HelpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycle_view_help);
        mRecyclerView.setNestedScrollingEnabled(false);

        helpList = new ArrayList<>();
        populateHelpList(helpList);

        mItemRecycleViewAdapter = new ItemRecycleViewAdapter(helpList);

        mRecyclerView.setAdapter(mItemRecycleViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void populateHelpList(List<Item> helpList) {

        List<String> titleList = null;
        List<String> descriptionList = null;
        if (PreferencesUtils.getBollean(mContext, PreferencesUtils.IS_CURRENT_USER_PROFESSIONAL)){
            titleList = Arrays.asList(getResources().getStringArray(R.array.professional_help_titles));
            descriptionList = Arrays.asList(getResources().getStringArray(R.array.professional_help_descriptions));
        } else{
            titleList = Arrays.asList(getResources().getStringArray(R.array.patient_help_titles));
            descriptionList = Arrays.asList(getResources().getStringArray(R.array.patient_help_descriptions));
        }

        String[] titleArray = new String[titleList.size()];
        int i = 0;
        for (String s : titleList){
            titleArray[i] = s.toString();
            i++;
        }

        String[] descriptionArray = new String[descriptionList.size()];
        i = 0;
        for(String s : descriptionList){
            descriptionArray[i] = s.toString();
            i++;
        }

        for (i = 0; i < titleList.size(); i++){
            helpList.add(new OrientationItem(titleArray[i], descriptionArray[i]));
        }
    }
}
