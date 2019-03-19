package cardio.uff.cardio.common.adapters.holders;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Map;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.model.view.Item;
import cardio.uff.cardio.common.model.view.RadioButtonList;

public class RadioButtonListHolder extends Holder {

    private TextView mTvQuestion;
    private RadioGroup mRadioGroup;
    private RadioButtonList radioButtonList;

    public RadioButtonListHolder(View itemView) {
        super(itemView);

        mTvQuestion = (TextView) itemView.findViewById(R.id.tv_question);
        mRadioGroup = (RadioGroup) itemView.findViewById(R.id.radio_group);
    }

    @Override
    public void bindType(Item item) {

        this.radioButtonList = (RadioButtonList) item;

            mTvQuestion.setText(radioButtonList.getQuestion());

            mRadioGroup.setOrientation(RadioGroup.VERTICAL);

            for (Map.Entry<String, Boolean> entry : radioButtonList.getOptions().entrySet()){
                RadioButton radioButton = new RadioButton(itemView.getContext());
                radioButton.setText(entry.getKey());
                radioButton.setChecked(entry.getValue());
                mRadioGroup.addView(radioButton);
            }

            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                    if(radioButton.isChecked()) {
                        clearSelections();
                        radioButtonList.getOptions().put(radioButton.getText().toString(), true);
                    }
                }
            });
    }

    private void clearSelections (){
        for (Map.Entry<String, Boolean> entry : radioButtonList.getOptions().entrySet()){
            entry.setValue(false);
        }
    }
}
