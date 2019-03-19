package cardio.uff.cardio.liquid.view;


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
import android.widget.RelativeLayout;

import java.util.List;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.adapters.CustomMapListRecycleViewAdapter;
import cardio.uff.cardio.common.model.view.CustomMapsList;
import cardio.uff.cardio.liquid.model.LiquidModelImp;
import cardio.uff.cardio.liquid.presenter.LiquidPresenter;
import cardio.uff.cardio.liquid.presenter.LiquidPresenterImp;
import cardio.uff.cardio.liquidDialog.view.LiquidDialogFragment;
import cardio.uff.cardio.professional.fragments.PrescribeFoodDialogFragment;

public class LiquidFragment extends Fragment
        implements LiquidView, View.OnClickListener {

    private RelativeLayout mRlPrescribe;
    private RecyclerView  mRvOldLiquidRecomendation;
    private RecyclerView mRvCurrentLiquidRecomendation;

    private LiquidPresenter mLiquidPresenterImp;

    public LiquidFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liquid, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null)
            mLiquidPresenterImp = new LiquidPresenterImp(this, new LiquidModelImp(context));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRlPrescribe = (RelativeLayout) view.findViewById(R.id.rl_prescribe);
        mRlPrescribe.setOnClickListener(this);
        mLiquidPresenterImp.initializeProfileInformation();


        mRvOldLiquidRecomendation = (RecyclerView) view.findViewById(R.id.recycle_view_old_liquid);
        mRvOldLiquidRecomendation.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvOldLiquidRecomendation.setNestedScrollingEnabled(false);

        mRvCurrentLiquidRecomendation = (RecyclerView) view.findViewById(R.id.recycle_view_current_liquid);
        mRvCurrentLiquidRecomendation.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvCurrentLiquidRecomendation.setNestedScrollingEnabled(false);

        mLiquidPresenterImp.initializeRecomendationList();
    }

    @Override
    public void showPrescribeButton() {
        mRlPrescribe.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePrescribeButton() {
        mRlPrescribe.setVisibility(View.GONE);
    }

    @Override
    public void populateCurrentRecommendationsRecycleView(List<CustomMapsList> customMapsLists) {
        CustomMapListRecycleViewAdapter currentCustomMapListRecycleViewAdapter =
                new CustomMapListRecycleViewAdapter(customMapsLists);

        currentCustomMapListRecycleViewAdapter.setComunicatorOnAddClickItem(new CustomMapListRecycleViewAdapter.ComunicatorOnAddClickItem() {
            @Override
            public void onAddClickItemListener(String id, String title) {
                LiquidDialogFragment dialog = LiquidDialogFragment.newInstance(id, title);
                dialog.show(getActivity().getSupportFragmentManager(), "dialogFragment");
            }
        });

        mRvCurrentLiquidRecomendation.setAdapter(currentCustomMapListRecycleViewAdapter);
    }

    @Override
    public void populateOldRecommendationsRecycleView(List<CustomMapsList> customMapsLists) {
        CustomMapListRecycleViewAdapter oldCustomMapListRecycleViewAdapter =
                new CustomMapListRecycleViewAdapter(customMapsLists);
        mRvOldLiquidRecomendation.setAdapter(oldCustomMapListRecycleViewAdapter);
    }

    @Override
    public List<CustomMapsList> getCurrentRecommendationsRecycleView() {
        CustomMapListRecycleViewAdapter customMapRecycleViewAdapter =
                (CustomMapListRecycleViewAdapter) mRvCurrentLiquidRecomendation.getAdapter();
        return customMapRecycleViewAdapter.getmCustomMapsLists();
    }

    @Override
    public List<CustomMapsList> getOldRecommendationsRecycleView() {
        CustomMapListRecycleViewAdapter customMapRecycleViewAdapter =
                (CustomMapListRecycleViewAdapter) mRvOldLiquidRecomendation.getAdapter();
        return customMapRecycleViewAdapter.getmCustomMapsLists();
    }

    @Override
    public void openPrescribeDialog() {
        PrescribeFoodDialogFragment prescribeFoodDialogFragment= new PrescribeFoodDialogFragment();
        prescribeFoodDialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_prescribe:
                mLiquidPresenterImp.onClickPrescribeLiquid();
                break;
        }
    }
}
