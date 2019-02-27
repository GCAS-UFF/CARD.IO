package cardio.com.cardio.common.fragments;

import android.content.Context;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemOrientationAdapter;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.model.view.OrientationItem;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class OrientationFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;
    private List<Item> orientationList;
    private ItemRecycleViewAdapter mItemRecycleViewAdapter;

    public OrientationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orientation, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycle_view_orientations);

        orientationList = new ArrayList<>();
        populateOrientationList(orientationList);

        mItemRecycleViewAdapter = new ItemRecycleViewAdapter(orientationList);

        mRecyclerView.setAdapter(mItemRecycleViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void populateOrientationList(List<Item> orientationList) {

        List<String> titleList = Arrays.asList(getResources().getStringArray(R.array.orienttion_title));
        String[] titleArray = new String[titleList.size()];
        int i = 0;
        for (String s : titleList){
            titleArray[i] = s.toString();
            i++;
        }

        List<String> descriptionList =  Arrays.asList(getResources().getStringArray(R.array.orientation_description));
        String[] descriptionArray = new String[descriptionList.size()];
        i = 0;
        for(String s : descriptionList){
            descriptionArray[i] = s.toString();
            i++;
        }

        for (i = 0; i < titleList.size(); i++){
            orientationList.add(new OrientationItem(titleArray[i], descriptionArray[i]));
        }
    }
}