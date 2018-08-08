package cardio.com.cardio.common.adapters.decodificators;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.holders.DateTextBoxHolder;
import cardio.com.cardio.common.adapters.holders.DropDownHolder;
import cardio.com.cardio.common.adapters.holders.TextBoxHolder;
import cardio.com.cardio.common.adapters.holders.Holder;
import cardio.com.cardio.common.model.view.LayoutConstantes;

public class LayoutDecodificator {

    public static Holder getViewHolder(ViewGroup viewGroup, int type, FragmentManager fragmentManager) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v;

        switch (type) {
            case LayoutConstantes.TEXT_BOX_LAYOUT:
                v = layoutInflater.inflate(R.layout.text_box_item, viewGroup, false);
                return new TextBoxHolder(v);
            case LayoutConstantes.DATE_TEXT_BOX_LAYOUT:
                v = layoutInflater.inflate(R.layout.date_text_box_item, viewGroup, false);
                return new DateTextBoxHolder(v, fragmentManager);
            case LayoutConstantes.DROP_DOWN_LAYOUT:
                v = layoutInflater.inflate(R.layout.item_dorpdown, viewGroup, false);
                return new DropDownHolder(v);
        }

        return null;
    }
}
