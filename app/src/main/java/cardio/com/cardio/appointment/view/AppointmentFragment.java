package cardio.com.cardio.appointment.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.appointment.model.AppointmentModelImp;
import cardio.com.cardio.appointment.presenter.AppointmentPresenter;
import cardio.com.cardio.appointment.presenter.AppointmentPresenterImp;
import cardio.com.cardio.appointmentDialog.view.AppointmentDialogFragment;
import cardio.com.cardio.common.adapters.CustomMapListRecycleViewAdapter;
import cardio.com.cardio.common.model.view.CustomMapsList;
import cardio.com.cardio.professional.fragments.PrescribeAppointmentDialogFragment;

public class AppointmentFragment extends Fragment implements AppointmentView, View.OnClickListener{

    private RelativeLayout mRlPrescribe;
    private RecyclerView mRVOldAppointments;
    private RecyclerView mRVFutureAppointments;

    private AppointmentPresenter mAppointmentPresenterImp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null) {
            mAppointmentPresenterImp = new AppointmentPresenterImp(this, new AppointmentModelImp(context));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRlPrescribe = (RelativeLayout) view.findViewById(R.id.rl_prescribe);
        mRlPrescribe.setOnClickListener(this);

        mAppointmentPresenterImp.initializeProfileInformation();

        mRVOldAppointments = (RecyclerView) view.findViewById(R.id.recycle_view_old_appointment);
        mRVOldAppointments.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVOldAppointments.setNestedScrollingEnabled(false);

        mRVFutureAppointments = (RecyclerView) view.findViewById(R.id.recycle_view_future_appointment);
        mRVFutureAppointments.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVFutureAppointments.setNestedScrollingEnabled(false);

        mAppointmentPresenterImp.initilizeRecomendationList();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.rl_prescribe:
               mAppointmentPresenterImp.onClickPrescribeAppointment();
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
    public void populateFutureAppointmentRecycleView(List<CustomMapsList> customMapsLists){
        CustomMapListRecycleViewAdapter currentCustomMapListRecycleViewAdapter =
                new CustomMapListRecycleViewAdapter(customMapsLists);

        currentCustomMapListRecycleViewAdapter.setComunicatorOnAddClickItem(new CustomMapListRecycleViewAdapter.ComunicatorOnAddClickItem() {
            @Override
            public void onAddClickItemListener(String id, String title) {
                AppointmentDialogFragment dialog = AppointmentDialogFragment.newInstance(id, title);
                dialog.show(getActivity().getSupportFragmentManager(), "dialogFragment");
            }
        });


        mRVFutureAppointments.setAdapter(currentCustomMapListRecycleViewAdapter);
    }

    @Override
    public void populateOldAppointmentRecycleView(List<CustomMapsList> customMapsLists){
        CustomMapListRecycleViewAdapter currentCustomMapListRecycleViewAdapter =
                new CustomMapListRecycleViewAdapter(customMapsLists);

        mRVOldAppointments.setAdapter(currentCustomMapListRecycleViewAdapter);
    }

    @Override
    public void openPrescribeDialog() {
        PrescribeAppointmentDialogFragment prescribeAppointmentDialogFragment = new PrescribeAppointmentDialogFragment();
        prescribeAppointmentDialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
    }
}
