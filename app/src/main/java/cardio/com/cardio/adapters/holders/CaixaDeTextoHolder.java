package cardio.com.cardio.adapters.holders;

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
        CaixaDeTexto caixaDeTexto = (CaixaDeTexto) item;

        mTvLabel.setText(caixaDeTexto.getLabel());
        mTvUnidade.setText(caixaDeTexto.getUnidade());

        mEdtInput.setInputType(caixaDeTexto.getInputType());
        mEdtInput.setHint(caixaDeTexto.getHint());
    }
}
