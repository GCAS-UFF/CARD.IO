package cardio.com.cardio.adapters.decodificators;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cardio.com.cardio.R;
import cardio.com.cardio.adapters.holders.CaixaDeTextoHolder;
import cardio.com.cardio.adapters.holders.Holder;
import cardio.com.cardio.model.LayoutConstantes;

public class LayoutDecodificador {

    public static Holder getViewHolder(ViewGroup viewGroup, int type) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v;

        switch (type) {
            case LayoutConstantes.LAYOUT_CAIXA_DE_TEXTO:
                v = layoutInflater.inflate(R.layout.item_caixas_texto, viewGroup, false);
                return new CaixaDeTextoHolder(v);
        }

        return null;
    }
}
