package cardio.uff.cardio.medicine.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
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
import cardio.uff.cardio.medicine.presenter.MedicinePresenter;
import cardio.uff.cardio.medicine.presenter.MedicinePresenterImpl;
import cardio.uff.cardio.medicine.model.MedicineModelImpl;
import cardio.uff.cardio.medicineDialog.view.DialogAddPerformMedicineFragment;
import cardio.uff.cardio.professional.fragments.PrescribeMedicineDialogFragment;

public class MedicineFragment extends Fragment implements MedicineView, View.OnClickListener {

    public static final String ARG_PARAM1 = "id";
    public static final String ARG_PARAM2 = "date";

    private String mId;
    private String mDateStr;

    private RelativeLayout mRlPrescribe;
    private RecyclerView mRVOldMedicines;
    private RecyclerView mRVCurrentMedicines;

    private MedicinePresenter medicinePresenterImpl;

    public static MedicineFragment newInstance (String id, String dateStr){
        MedicineFragment medicineFragment = new MedicineFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, dateStr);
        medicineFragment.setArguments(args);

        return medicineFragment;
    }

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null){
            mId = bundle.getString(ARG_PARAM1);
            mDateStr = bundle.getString(ARG_PARAM2);
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
        mRVOldMedicines.setNestedScrollingEnabled(false);

        mRVCurrentMedicines = (RecyclerView) view.findViewById(R.id.recycle_view_current_medicine);
        mRVCurrentMedicines.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVCurrentMedicines.setNestedScrollingEnabled(false);

        medicinePresenterImpl.initilizeRecomendationList();

        if (mId != null && mDateStr != null){
            DialogAddPerformMedicineFragment dialog = DialogAddPerformMedicineFragment.newInstance(mId, mDateStr);
            dialog.show(getActivity().getSupportFragmentManager(), "dialogFragment");
        }
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