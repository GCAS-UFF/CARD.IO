package cardio.uff.cardio.common.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cardio.uff.cardio.common.model.view.Item;

public abstract class Holder extends RecyclerView.ViewHolder{
    public Holder(View itemView) {
        super(itemView);
    }

    public abstract void bindType(Item item);
}
