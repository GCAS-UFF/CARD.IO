package cardio.com.cardio.adapters.holders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cardio.com.cardio.model.Item;
import cardio.com.cardio.R;
import cardio.com.cardio.model.CaixaDeTexto;

public class CaixaDeTextoHolder extends Holder {

    private TextView mTvLabel;
    private TextView mTvUnidade;
    private EditText mEdtInput;

    public CaixaDeTextoHolder(View itemView) {
        super(itemView);

        mTvLabel = (TextView) itemView.findViewById(R.id.tv_label);
        mTvUnidade = (TextView) itemView.findViewById(R.id.tv_label_unidade);
        mEdtInput = (EditText) itemView.findViewById(R.id.edt_input);
    }

    @Override
    public void bindType(Item item) {
        final CaixaDeTexto caixaDeTexto = (CaixaDeTexto) item;

        mTvLabel.setText(caixaDeTexto.getLabel());
        mTvUnidade.setText(caixaDeTexto.getUnidade());

        mEdtInput.setInputType(caixaDeTexto.getInputType());
        mEdtInput.setHint(caixaDeTexto.getHint());

        mEdtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                caixaDeTexto.setValue(mEdtInput.getText().toString());
            }
        });
    }
}
