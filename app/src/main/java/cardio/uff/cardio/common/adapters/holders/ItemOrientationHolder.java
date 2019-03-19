package cardio.uff.cardio.common.adapters.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.model.view.Item;
import cardio.uff.cardio.common.model.view.OrientationItem;

public class ItemOrientationHolder extends Holder {

    private TextView mTvTitle;
    private TextView mTvDescription;
    private LinearLayout mLlContainer;
    private LinearLayout mLlOrientationDescription;

    public ItemOrientationHolder(View itemView) {
        super(itemView);
        mTvTitle = (TextView) itemView.findViewById(R.id.tv_orientation_title);
        mTvDescription = (TextView) itemView.findViewById(R.id.tv_orientation_description);
        mLlContainer = (LinearLayout) itemView.findViewById(R.id.ll_expandable_container);
        mLlOrientationDescription = (LinearLayout) itemView.findViewById(R.id.ll_orientation_description);
        mLlOrientationDescription.setVisibility(View.GONE);
    }


    @Override
    public void bindType(Item item) {
        try {
            final OrientationItem orientationItem = (OrientationItem) item;

            mTvTitle.setText(orientationItem.getTitle());
            mTvDescription.setText(orientationItem.getDescription());

            mLlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLlOrientationDescription.getVisibility() == View.GONE)
                        mLlOrientationDescription.setVisibility(View.VISIBLE);
                    else
                        mLlOrientationDescription.setVisibility(View.GONE);
                }
            });
        } catch (NullPointerException e){
            e.printStackTrace();
        }


    }
}
