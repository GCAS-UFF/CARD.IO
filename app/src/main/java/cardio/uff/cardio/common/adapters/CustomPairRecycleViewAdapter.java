package cardio.uff.cardio.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.model.view.CustomPair;

public class CustomPairRecycleViewAdapter extends RecyclerView.Adapter<CustomPairRecycleViewAdapter.CustomPairHolder> {

    private List<CustomPair> mCustomPairs;

    public CustomPairRecycleViewAdapter(List<CustomPair> customPairs) {
        this.mCustomPairs = customPairs;
    }

    @NonNull
    @Override
    public CustomPairHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_map, parent, false);
        return new CustomPairHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomPairHolder holder, int position) {
        if (mCustomPairs == null) return;
        holder.bindType(mCustomPairs.get(position));
    }

    @Override
    public int getItemCount() {
        if (mCustomPairs!= null) return mCustomPairs.size();
        return 0;
    }

    class CustomPairHolder extends RecyclerView.ViewHolder {
        private TextView mTvLabel;
        private TextView mTvContent;

        public CustomPairHolder(View itemView) {
            super(itemView);
            mTvLabel = (TextView) itemView.findViewById(R.id.tv_label);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }

        public void bindType (CustomPair customPair){
            try {
                mTvLabel.setText(customPair.getKey());
                mTvContent.setText(customPair.getValue());
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }
}
