package cardio.uff.cardio.exercise.view;


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
import cardio.uff.cardio.exercise.model.ExerciseModelImp;
import cardio.uff.cardio.exercise.presenter.ExercisePresenter;
import cardio.uff.cardio.exercise.presenter.ExercisePresenterImp;
import cardio.uff.cardio.exerciseDialog.view.ExerciseDialogFragment;
import cardio.uff.cardio.professional.fragments.PrescribeExercisesDialogFragment;

public class ExerciseFragment extends Fragment
        implements View.OnClickListener, ExerciseView{

    private RelativeLayout mRlPrescribe;
    private RecyclerView mRvOldExerciseRecomendation;
    private RecyclerView mRvCurrentExerciseRecomendation;

    private ExercisePresenter mExercisePresenterImp;

    public ExerciseFragment(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null)
            mExercisePresenterImp = new ExercisePresenterImp(this, new ExerciseModelImp(context));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRlPrescribe = (RelativeLayout) view.findViewById(R.id.rl_prescribe);
        mRlPrescribe.setOnClickListener(this);

        mExercisePresenterImp.initializeProfileInformation();

        mRvCurrentExerciseRecomendation = (RecyclerView) view.findViewById(R.id.recycle_view_current_exercise);
        mRvCurrentExerciseRecomendation.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvCurrentExerciseRecomendation.setNestedScrollingEnabled(false);

        mRvOldExerciseRecomendation = (RecyclerView) view.findViewById(R.id.recycle_view_old_exercise);
        mRvOldExerciseRecomendation.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvOldExerciseRecomendation.setNestedScrollingEnabled(true);

        mExercisePresenterImp.initializeRecomendationList();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_prescribe:
                mExercisePresenterImp.onClickPrescribeLiquid();
                break;
        }
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
    public void populateCurrentRecommendationsRecycleView(List<CustomMapsList> currentRecomendations) {
        CustomMapListRecycleViewAdapter currentCustomMapListRecycleViewAdapter =
                new CustomMapListRecycleViewAdapter(currentRecomendations);

        currentCustomMapListRecycleViewAdapter.setComunicatorOnAddClickItem(new CustomMapListRecycleViewAdapter.ComunicatorOnAddClickItem() {
            @Override
            public void onAddClickItemListener(String id, String title) {
                ExerciseDialogFragment dialog = ExerciseDialogFragment.newInstance(id, title);
                dialog.show(getActivity().getSupportFragmentManager(), "dialogFragment");
            }
        });

        mRvCurrentExerciseRecomendation.setAdapter(currentCustomMapListRecycleViewAdapter);
    }

    @Override
    public void populateOldRecommendationsRecycleView(List<CustomMapsList> oldRecomendations) {
        CustomMapListRecycleViewAdapter oldCustomMapListRecycleViewAdapter =
                new CustomMapListRecycleViewAdapter(oldRecomendations);
        mRvOldExerciseRecomendation.setAdapter(oldCustomMapListRecycleViewAdapter);
    }

    @Override
    public List<CustomMapsList> getCurrentRecommendationsRecycleView() {
        CustomMapListRecycleViewAdapter customMapRecycleViewAdapter =
                (CustomMapListRecycleViewAdapter) mRvCurrentExerciseRecomendation.getAdapter();
        return customMapRecycleViewAdapter.getmCustomMapsLists();
    }

    @Override
    public List<CustomMapsList> getOldRecommendationsRecycleView() {
        CustomMapListRecycleViewAdapter customMapRecycleViewAdapter =
                (CustomMapListRecycleViewAdapter) mRvOldExerciseRecomendation.getAdapter();
        return customMapRecycleViewAdapter.getmCustomMapsLists();
    }

    @Override
    public void openPrescribeDialog() {
        PrescribeExercisesDialogFragment prescribeExercisesDialogFragment = new PrescribeExercisesDialogFragment();
        prescribeExercisesDialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
    }
}
