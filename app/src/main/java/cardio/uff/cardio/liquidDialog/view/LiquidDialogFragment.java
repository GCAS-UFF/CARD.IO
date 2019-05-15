package cardio.uff.cardio.liquidDialog.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.uff.cardio.common.model.model.Alimentacao;
import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.model.view.DateTextBox;
import cardio.uff.cardio.common.model.view.DropDown;
import cardio.uff.cardio.common.model.view.Item;
import cardio.uff.cardio.common.model.view.TextBox;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.liquidDialog.model.LiquidDialogModelImp;
import cardio.uff.cardio.liquidDialog.presenter.LiquidDialogPresenter;
import cardio.uff.cardio.liquidDialog.presenter.LiquidDialogPresenterImp;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiquidDialogFragment extends DialogFragment
        implements LiquidDialogView, View.OnClickListener {

    public static final String ARG_PARAM1 = "id";
    public static final String ARG_PARAM2 = "date";

    private String mId;
    private String mDateStr;
    private LiquidDialogPresenter mLiquidDialogPresenter;
    private ItemRecycleViewAdapter mItemRecycleViewAdapter;

    private RecyclerView mRecView;

    private TextBox mFoodTextBox;
    private TextBox mQuantityTextBox;
    private DropDown mQuantityDropDown;
    private DateTextBox mHourTextBox;


    private Button mBtnCancelar;
    private Button mBtnOk;
    private List<Item> mItems;

    public LiquidDialogFragment() {
    }

    public static LiquidDialogFragment newInstance (String id, String dateStr){
        LiquidDialogFragment dialog = new LiquidDialogFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, dateStr);
        dialog.setArguments(args);

        return dialog;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liquid_dialog, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null) {
            mLiquidDialogPresenter = new LiquidDialogPresenterImp(this, new LiquidDialogModelImp(context));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mRecView.setNestedScrollingEnabled(false);
        mBtnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);

        mItems = new ArrayList<>();

        mFoodTextBox = new TextBox(view.getResources().getString(R.string.alimentation_screen_title), "", TextBox.INPUT_TEXT);
        mFoodTextBox.setEditable(true);
        mItems.add(mFoodTextBox);

        mQuantityTextBox = new TextBox(view.getResources().getString(R.string.quantidade), "", TextBox.INPUT_DECIMAL);
        mQuantityTextBox.setEditable(true);
        mItems.add(mQuantityTextBox);

        mItemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
        mItemRecycleViewAdapter.setFragmentManager(getFragmentManager());

        mRecView.setAdapter(mItemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnCancelar.setOnClickListener(this);

        mBtnOk.setOnClickListener(this);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.very_round_background_shape);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mLiquidDialogPresenter.initializeLiquidInformation(mId);
    }

    @Override
    public Recomentation getRecomendation() throws ParseException {

        Alimentacao alimentation = new Alimentacao();
        alimentation.setFood(mFoodTextBox.getValue());

        if (mQuantityDropDown != null)
            alimentation.setQuantidade(mLiquidDialogPresenter.
                calculateLiquid(mQuantityTextBox.getValue(), mQuantityDropDown.getValue()));

        Recomentation recomentation = new Recomentation();
        recomentation.setAction(alimentation);

        if(mHourTextBox != null)
            recomentation.getAction().setExecutedDate((Formater.getDateFromStringDateAndTime(mDateStr, mHourTextBox.getValue())).getTime());

        return recomentation;
    }

    @Override
    public boolean isFormValid(){
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
    public void showMessage(int res) {
        Toast.makeText(getContext(), getResources().getString(res), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateQuantityReferenceDropDown(Map<String, String> options){
        mQuantityDropDown = new DropDown(options, "Referência");
        mQuantityDropDown.setEditable(true);
        mItems.add(mQuantityDropDown);

        mHourTextBox = new DateTextBox(getResources().getString(R.string.medicine_performed_hour), DateTextBox.INPUT_TIME);
        mItems.add(mHourTextBox);

        mItemRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancelar:
                dismiss();
                break;
            case R.id.btn_ok:
                mLiquidDialogPresenter.onClickButtonOK();
                break;
        }
    }
}
