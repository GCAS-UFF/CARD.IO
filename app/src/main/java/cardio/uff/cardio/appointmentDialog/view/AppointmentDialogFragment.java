package cardio.uff.cardio.appointmentDialog.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cardio.uff.cardio.R;
import cardio.uff.cardio.appointmentDialog.model.AppointmentDialogModel;
import cardio.uff.cardio.appointmentDialog.model.AppointmentDialogModelImp;
import cardio.uff.cardio.appointmentDialog.presenter.AppointmentDialogPresenter;
import cardio.uff.cardio.appointmentDialog.presenter.AppointmentDialogPresenterImp;
import cardio.uff.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.uff.cardio.common.model.model.Consulta;
import cardio.uff.cardio.common.model.view.DateTextBox;
import cardio.uff.cardio.common.model.view.DropDown;
import cardio.uff.cardio.common.model.view.Item;
import cardio.uff.cardio.common.model.view.RadioButtonList;
import cardio.uff.cardio.common.util.Formater;

public class AppointmentDialogFragment extends android.support.v4.app.DialogFragment
        implements AppointmentDialogView, View.OnClickListener{

    public static final String ARG_PARAM1 = "id";
    public static final String ARG_PARAM2 = "date";

    private String mId;
    private String mDateStr;
    private AppointmentDialogModel mAppointmentDialogModel;
    private AppointmentDialogPresenter mAppointmentDialogPresenter;
    private Consulta mAppointment;

    private RecyclerView mRecView;
    private DateTextBox mDateTextBox;
    private DateTextBox mHourTextBox;
    private DropDown mAdressDropDown;
    private DropDown mSpecialityDropDown;
    private RadioButtonList mRadioButtonList;
    private Button mBtnCancelar;
    private Button mBtnOk;
    private List<Item> mItems;

    private ItemRecycleViewAdapter itemRecycleViewAdapter;

    public AppointmentDialogFragment() {
    }

    public static AppointmentDialogFragment newInstance (String id, String dateStr){
        AppointmentDialogFragment dialog = new AppointmentDialogFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, dateStr);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_appointment_dialog, container, false);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null) {
            mAppointmentDialogPresenter = new AppointmentDialogPresenterImp(this,
                    new AppointmentDialogModelImp(context));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mBtnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        mBtnCancelar.setOnClickListener(this);

        mBtnOk = (Button) view.findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);

        mItems = new ArrayList<>();

        mDateTextBox = new DateTextBox(getString(R.string.appointment_date_label), DateTextBox.INPUT_DATE);
        mDateTextBox.setEditable(false);
        mItems.add(mDateTextBox);

        mHourTextBox = new DateTextBox(getString(R.string.appointment_hour_label), DateTextBox.INPUT_TIME);
        mHourTextBox.setEditable(false);
        mItems.add(mHourTextBox);

        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
        itemRecycleViewAdapter.setFragmentManager(getFragmentManager());

        mRecView.setAdapter(itemRecycleViewAdapter);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.very_round_background_shape);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mAppointmentDialogPresenter.initializeAppointmentInformation(mId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancelar:
                dismiss();
                break;
            case R.id.btn_ok:
                mAppointmentDialogPresenter.onClickButtonOK();
                break;
        }
    }

    @Override
    public void showAppointmentInformation(Consulta appointment){
        this.mAppointment = appointment;
        mDateTextBox.setValue(Formater.getStringFromDate(new Date(appointment.getData())));
        mHourTextBox.setValue(Formater.getTimeStringFromDate(new Date(appointment.getData())));

        itemRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void populateAddressDropDown(Map<String, String> options){
        mAdressDropDown = new DropDown(options, "Endereço");
        mAdressDropDown.setEditable(false);
        mItems.add(mAdressDropDown);
        // Populando quando já existe informação prévia
        if (mAdressDropDown != null && mAdressDropDown.getOptions() != null){
            for(Map.Entry<String, String> entry : mAdressDropDown.getOptions().entrySet()){
                if(entry.getValue().equals(mAppointment.getLocalizacao())){
                    mAdressDropDown.setValue(entry.getKey());
                }
            }
        }
        itemRecycleViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void populateSpecialitiesDropDown(Map<String, String> options){
        mSpecialityDropDown = new DropDown(options, "Especialidade");
        mSpecialityDropDown.setEditable(false);
        mItems.add(mSpecialityDropDown);

        if (mSpecialityDropDown != null && mSpecialityDropDown.getOptions() != null) {
            for (Map.Entry<String, String> entry : mSpecialityDropDown.getOptions().entrySet()) {
                if (entry.getValue().equals(mAppointment.getEspecialideProfissional())) {
                    mSpecialityDropDown.setValue(entry.getKey());
                }
            }
        }
        populateAttendanceOptions();
        itemRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isFormValid() {
        boolean isValid = true;
        for (Item item : mItems){
            if (item.isEmpty()){
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    @Override
    public Consulta getAppointment() {

        if (mRadioButtonList != null && mRadioButtonList.getOptionSelected() != null){
            if (mRadioButtonList.getOptionSelected().equals("Sim"))
                mAppointment.setAttended(true);
            else
                mAppointment.setAttended(false);
        }

        return mAppointment;
    }

    @Override
    public void showMessage(int res) {
        Toast.makeText(getContext(), getResources().getString(res), Toast.LENGTH_SHORT).show();
    }

    private void populateAttendanceOptions(){
        Map<String, Boolean> comparecimento = new HashMap<>();
        comparecimento.put("Sim", false);
        comparecimento.put("Não", false);

        mRadioButtonList = new RadioButtonList(comparecimento, "Compareceu?");
        mItems.add(mRadioButtonList);
    }
}
