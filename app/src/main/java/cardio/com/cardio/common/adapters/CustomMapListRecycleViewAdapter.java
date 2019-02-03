package cardio.com.cardio.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.model.view.CustomMapsList;

public class CustomMapListRecycleViewAdapter extends RecyclerView.Adapter<CustomMapListRecycleViewAdapter.CustomMapListHolder> {

    private List<CustomMapsList> mCustomMapsLists;

    public CustomMapListRecycleViewAdapter(List<CustomMapsList> customMapsLists) {
        this.mCustomMapsLists = customMapsLists;
    }

    @NonNull
    @Override
    public CustomMapListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_expandable_simpelist, parent, false);
        return new CustomMapListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomMapListHolder holder, int position) {
        if (mCustomMapsLists == null) return;
        holder.bindType (mCustomMapsLists.get(position));
    }

    @Override
    public int getItemCount() {
        if (mCustomMapsLists != null) return mCustomMapsLists.size();
        return 0;
    }

    class CustomMapListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTvTitle;
        private RecyclerView mRecView;
        private LinearLayout mLlExpandable;
        private LinearLayout getmLlExpandableContainer;

        public CustomMapListHolder(View itemView) {
            super(itemView);

            mLlExpandable = (LinearLayout) itemView.findViewById(R.id.ll_expandable);
            getmLlExpandableContainer = (LinearLayout) itemView.findViewById(R.id.ll_expandable_container);

            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);

            mRecView = (RecyclerView) itemView.findViewById(R.id.rec_view);
            mRecView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }

        public void bindType(CustomMapsList customMapsList) {
            try {
                mTvTitle.setText(customMapsList.getTitle());

                CustomMapRecycleViewAdapter customMapRecycleViewAdapter =
                        new CustomMapRecycleViewAdapter(customMapsList.getCustomMapObjectList());

                mRecView.setAdapter(customMapRecycleViewAdapter);

                getmLlExpandableContainer.setOnClickListener(this);

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            if (mLlExpandable.getVisibility() == View.GONE)
                mLlExpandable.setVisibility(View.VISIBLE);
            else
                mLlExpandable.setVisibility(View.GONE);
        }
    }
}
