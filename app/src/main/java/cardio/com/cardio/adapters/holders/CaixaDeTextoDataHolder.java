package cardio.com.cardio.adapters.holders;

import android.app.TimePickerDialog;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cardio.com.cardio.R;
import cardio.com.cardio.fragments.DataPickerFragment;
import cardio.com.cardio.fragments.TimePickerFragment;
import cardio.com.cardio.model.CaixaDeTexto;
import cardio.com.cardio.model.CaixaDeTextoData;
import cardio.com.cardio.model.Item;

public class CaixaDeTextoDataHolder extends Holder {

    private TextView mTvLabel;
    private TextView mTvInput;
    private FragmentManager mFragmentManager;

    public CaixaDeTextoDataHolder(View itemView, FragmentManager fragmentManager) {
        super(itemView);

        mTvLabel = (TextView) itemView.findViewById(R.id.tv_label);
        mTvInput = (TextView) itemView.findViewById(R.id.tv_input);
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public void bindType(Item item) {
        final CaixaDeTextoData caixaDeTextoData = (CaixaDeTextoData) item;

        mTvLabel.setText(caixaDeTextoData.getLabel());
        mTvInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (caixaDeTextoData.getInputType().equals(CaixaDeTextoData.INPUT_DATE)) {
                    DataPickerFragment dateFragment = new DataPickerFragment(comunicatorDate);
                    dateFragment.show(mFragmentManager, "dataPicker");
                }

                if (caixaDeTextoData.getInputType().equals(CaixaDeTextoData.INPUT_TIME)) {
                    TimePickerFragment timePickerFragment = new TimePickerFragment(comunicatorTime);
                    timePickerFragment.show(mFragmentManager, "timePicker");
                }
            }
        });

        mTvInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                caixaDeTextoData.setValue(mTvInput.getText().toString());
            }
        });
    }

    private DataPickerFragment.ComunicatorDataPicker comunicatorDate = new DataPickerFragment.ComunicatorDataPicker() {
        @Override
        public void setDate(String dataString) {
            mTvInput.setText(dataString);
        }
    };

    private TimePickerFragment.ComunicatorTimePicker comunicatorTime = new TimePickerFragment.ComunicatorTimePicker() {
        @Override
        public void setTime(String timeString) {
            mTvInput.setText(timeString);
        }
    };
}
