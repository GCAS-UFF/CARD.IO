package cardio.com.cardio.common.adapters.holders;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.model.view.RadioButtonList;

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

        try {
            mTvQuestion.setText(radioButtonList.getQuestion());

            RadioGroup group = new RadioGroup(itemView.getContext());
            group.setOrientation(RadioGroup.VERTICAL);

            for (Map.Entry<String, Boolean> entry : radioButtonList.getOptions().entrySet()){
                RadioButton radioButton = new RadioButton(itemView.getContext());
                radioButton.setText(entry.getKey());
                radioButton.setChecked(entry.getValue());
                group.addView(radioButton);
            }

            mRadioGroup.addView(group);

            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = (RadioButton) group.findViewById(checkedId);

                    clearSelections();
                    radioButtonList.getOptions().put(radioButton.getText().toString(), true);
                }
            });

        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void clearSelections (){
        for (Map.Entry<String, Boolean> entry : radioButtonList.getOptions().entrySet()){
            entry.setValue(false);
        }
    }
}
