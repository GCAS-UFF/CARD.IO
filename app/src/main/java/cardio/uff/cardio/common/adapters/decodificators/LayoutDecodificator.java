package cardio.uff.cardio.common.adapters.decodificators;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.adapters.holders.DateTextBoxHolder;
import cardio.uff.cardio.common.adapters.holders.DropDownHolder;
import cardio.uff.cardio.common.adapters.holders.CheckBoxListHolder;
import cardio.uff.cardio.common.adapters.holders.ItemOrientationHolder;
import cardio.uff.cardio.common.adapters.holders.RadioButtonListHolder;
import cardio.uff.cardio.common.adapters.holders.TextBoxHolder;
import cardio.uff.cardio.common.adapters.holders.Holder;
import cardio.uff.cardio.common.model.view.LayoutConstantes;

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
            case LayoutConstantes.CHECKBOX_LIST_LAYOUT:
                v = layoutInflater.inflate(R.layout.item_checkbox_list, viewGroup, false);
                return new CheckBoxListHolder(v);
            case LayoutConstantes.RADIO_BUTTON_LIST_LAYOUT:
                v = layoutInflater.inflate(R.layout.item_radio_button_list, viewGroup, false);
                return new RadioButtonListHolder(v);
            case LayoutConstantes.ORIENTATION_ITEM:
                v = layoutInflater.inflate(R.layout.item_orientation, viewGroup, false);
                return new ItemOrientationHolder(v);
        }

        return null;
    }
}
