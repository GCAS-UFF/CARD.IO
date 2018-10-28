package cardio.com.cardio.common.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemExpandableSimpleListAdapter;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.model.view.OrientationItem;

public class ItemOrientationHolder extends Holder {

    private TextView mTvTitle;
    private TextView mTvDescription;

    public ItemOrientationHolder(View itemView) {
        super(itemView);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_orientation_title);
        mTvDescription = (TextView) itemView.findViewById(R.id.tv_orientation_description);
    }


    @Override
    public void bindType(Item item) {
        final OrientationItem orientationItem = (OrientationItem) item;

        mTvTitle.setText(orientationItem.getTitle());
        mTvDescription.setText(orientationItem.getDescription());

    }
}
