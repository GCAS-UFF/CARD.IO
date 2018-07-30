package cardio.com.cardio.common.adapters.holders;

import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import cardio.com.cardio.R;
import cardio.com.cardio.common.fragments.DataPickerFragment;
import cardio.com.cardio.common.fragments.TimePickerFragment;
import cardio.com.cardio.common.model.view.DateTextBox;
import cardio.com.cardio.common.model.view.Item;

public class DateTextBoxHolder extends Holder {

    private TextView mTvLabel;
    private TextView mTvInput;
    private FragmentManager mFragmentManager;

    public DateTextBoxHolder(View itemView, FragmentManager fragmentManager) {
        super(itemView);

        mTvLabel = (TextView) itemView.findViewById(R.id.tv_label);
        mTvInput = (TextView) itemView.findViewById(R.id.tv_input);
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public void bindType(Item item) {
        final DateTextBox dateTextBox = (DateTextBox) item;

        mTvLabel.setText(dateTextBox.getLabel());
        mTvInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateTextBox.getInputType().equals(DateTextBox.INPUT_DATE)) {
                    DataPickerFragment dateFragment = new DataPickerFragment(comunicatorDate);
                    dateFragment.show(mFragmentManager, "dataPicker");
                }

                if (dateTextBox.getInputType().equals(DateTextBox.INPUT_TIME)) {
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
                dateTextBox.setValue(mTvInput.getText().toString());
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
