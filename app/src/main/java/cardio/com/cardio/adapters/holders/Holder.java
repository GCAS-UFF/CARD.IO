package cardio.com.cardio.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cardio.com.cardio.model.Item;

public abstract class Holder extends RecyclerView.ViewHolder{
    public Holder(View itemView) {
        super(itemView);
    }

    public abstract void bindType(Item item);
}
