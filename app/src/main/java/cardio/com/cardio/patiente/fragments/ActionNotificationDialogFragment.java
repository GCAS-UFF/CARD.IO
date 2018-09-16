package cardio.com.cardio.patiente.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.model.Consulta;
import cardio.com.cardio.common.model.view.DropDown;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.model.view.RadioButtonList;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

@SuppressLint("ValidFragment")
public class ActionNotificationDialogFragment extends android.support.v4.app.DialogFragment {

    private List<Consulta> consultaList;
    private RecyclerView mRecView;
    private RadioButtonList mRadioButtonList;
    private DropDown mHourDropDown;
    private Button mBtnCancelar;
    private Button mBtnOk;
    private List<Item> mItems;

    private ComunicatorFragmentActivity comunicatorFragmentActivity;

    @SuppressLint("ValidFragment")
    public ActionNotificationDialogFragment(List<Consulta> consultaList) {
        this.consultaList = consultaList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_notification_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null)
            comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);
        mBtnCancelar = (Button) view.findViewById(R.id.btn_cancelar);

        mItems = new ArrayList<>();

        Map<String, String> options = new LinkedHashMap<>();
        for (Consulta consulta : consultaList){
            options.put(consulta.getId(), Formater.getTimeStringFromDate(new Date(consulta.getData())));
        }
        mHourDropDown = new DropDown(options, "Horário");
        mItems.add(mHourDropDown);

        Map<String, Boolean> comparecimento = new HashMap<>();
        comparecimento.put("Sim", false);
        comparecimento.put("Não", false);

        mRadioButtonList = new RadioButtonList(comparecimento, "Compareceu?");
        mItems.add(mRadioButtonList);

        ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
        itemRecycleViewAdapter.setFragmentManager(getFragmentManager());

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}