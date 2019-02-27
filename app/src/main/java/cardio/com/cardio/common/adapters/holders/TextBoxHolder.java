package cardio.com.cardio.common.adapters.holders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.R;
import cardio.com.cardio.common.model.view.TextBox;

public class TextBoxHolder extends Holder {

    private TextView mTvLabel;
    private TextView mTvUnidade;
    private EditText mEdtInput;

    public TextBoxHolder(View itemView) {
        super(itemView);

        mTvLabel = (TextView) itemView.findViewById(R.id.tv_label);
        mTvUnidade = (TextView) itemView.findViewById(R.id.tv_label_unidade);
        mEdtInput = (EditText) itemView.findViewById(R.id.edt_input);
    }

    @Override
    public void bindType(Item item) {
        final TextBox textBox = (TextBox) item;

        mTvLabel.setText(textBox.getLabel());
        mTvUnidade.setText(textBox.getUnidade());

        mEdtInput.setInputType(textBox.getInputType());
        mEdtInput.setEnabled(textBox.isEditable());
        mEdtInput.setHint(textBox.getHint());
        if (textBox.getValue() != null)
            mEdtInput.setText(textBox.getValue());

        mEdtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textBox.setValue(mEdtInput.getText().toString());
            }
        });
    }
}
