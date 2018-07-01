package cardio.com.cardio.adapters.decodificators;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cardio.com.cardio.R;
import cardio.com.cardio.adapters.holders.CaixaDeTextoDataHolder;
import cardio.com.cardio.adapters.holders.CaixaDeTextoHolder;
import cardio.com.cardio.adapters.holders.Holder;
import cardio.com.cardio.model.LayoutConstantes;

public class LayoutDecodificador {

    public static Holder getViewHolder(ViewGroup viewGroup, int type, FragmentManager fragmentManager) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v;

        switch (type) {
            case LayoutConstantes.LAYOUT_CAIXA_DE_TEXTO:
                v = layoutInflater.inflate(R.layout.item_caixas_texto, viewGroup, false);
                return new CaixaDeTextoHolder(v);
            case LayoutConstantes.LAYOUT_CAIXA_DE_TEXTO_DATA:
                v = layoutInflater.inflate(R.layout.item_caixa_texto_data, viewGroup, false);
                return new CaixaDeTextoDataHolder(v, fragmentManager);

        }

        return null;
    }
}
