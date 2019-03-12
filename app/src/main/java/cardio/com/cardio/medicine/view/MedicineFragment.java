package cardio.com.cardio.medicine.view;

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

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.CustomMapListRecycleViewAdapter;
import cardio.com.cardio.common.adapters.CustomMapRecycleViewAdapter;
import cardio.com.cardio.common.model.view.CustomMapsList;
import cardio.com.cardio.medicine.presenter.MedicinePresenter;
import cardio.com.cardio.medicine.presenter.MedicinePresenterImpl;
import cardio.com.cardio.medicine.model.MedicineModelImpl;
import cardio.com.cardio.medicineDialog.view.DialogAddPerformMedicineFragment;
import cardio.com.cardio.professional.fragments.PrescribeMedicineDialogFragment;

public class MedicineFragment extends Fragment implements MedicineView, View.OnClickListener {

    private RelativeLayout mRlPrescribe;
    private RecyclerView mRVOldMedicines;
    private RecyclerView mRVCurrentMedicines;

    private MedicinePresenter medicinePresenterImpl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_medicine, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null) {
            medicinePresenterImpl = new MedicinePresenterImpl(this, new MedicineModelImpl(context));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRlPrescribe = (RelativeLayout) view.findViewById(R.id.rl_prescribe_food);
        mRlPrescribe.setOnClickListener(this);

        medicinePresenterImpl.initializeProfileInformation();

        mRVOldMedicines = (RecyclerView) view.findViewById(R.id.recycle_view_old_medicine);
        mRVOldMedicines.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRVCurrentMedicines = (RecyclerView) view.findViewById(R.id.recycle_view_current_medicine);
        mRVCurrentMedicines.setLayoutManager(new LinearLayoutManager(getActivity()));

        medicinePresenterImpl.initilizeRecomendationList();
    }

    @Override
    public void openPrescribeDialog() {
        PrescribeMedicineDialogFragment prescribeMedicineDialogFragment = new PrescribeMedicineDialogFragment();
        prescribeMedicineDialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
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
    public void populateOldRecommendationsRecycleView(List<CustomMapsList> customMapsLists) {
        CustomMapListRecycleViewAdapter oldCustomMapListRecycleViewAdapter =
                new CustomMapListRecycleViewAdapter(customMapsLists);

        mRVOldMedicines.setAdapter(oldCustomMapListRecycleViewAdapter);
    }

    @Override
    public void populateCurrentRecommendationsRecycleView(List<CustomMapsList> customMapsLists) {
        CustomMapListRecycleViewAdapter currentCustomMapListRecycleViewAdapter =
                new CustomMapListRecycleViewAdapter(customMapsLists);

            currentCustomMapListRecycleViewAdapter.setComunicatorOnAddClickItem(new CustomMapListRecycleViewAdapter.ComunicatorOnAddClickItem() {
                @Override
                public void onAddClickItemListener(String id, String title) {
                    DialogAddPerformMedicineFragment dialog = DialogAddPerformMedicineFragment.newInstance(id, title);
                    dialog.show(getActivity().getSupportFragmentManager(), "dialogFragment");
                }
            });


        mRVCurrentMedicines.setAdapter(currentCustomMapListRecycleViewAdapter);
    }

    @Override
    public List<CustomMapsList> getCurrentRecommendationsRecycleView() {
        CustomMapListRecycleViewAdapter customMapRecycleViewAdapter =
                (CustomMapListRecycleViewAdapter) mRVCurrentMedicines.getAdapter();
        return customMapRecycleViewAdapter.getmCustomMapsLists();
    }

    @Override
    public List<CustomMapsList> getOldRecommendationsRecycleView() {
        CustomMapListRecycleViewAdapter customMapRecycleViewAdapter =
                (CustomMapListRecycleViewAdapter) mRVOldMedicines.getAdapter();
        return customMapRecycleViewAdapter.getmCustomMapsLists();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_prescribe_food:
                medicinePresenterImpl.onClickPrescribeMedicine();
                break;
        }
    }
}