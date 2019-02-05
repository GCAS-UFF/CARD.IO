package cardio.com.cardio.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.model.view.CustomMapObject;

public class CustomMapRecycleViewAdapter extends RecyclerView.Adapter<CustomMapRecycleViewAdapter.CustomMapHolder> {

    private List<CustomMapObject> mCustomMapObjects;
    private ComunicatorOnAddClick mComunicatorOnAddClick;

    public CustomMapRecycleViewAdapter(List<CustomMapObject> customMapObjects) {
        this.mCustomMapObjects = customMapObjects;
    }

    @NonNull
    @Override
    public CustomMapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_custom_map, parent, false);
        return new CustomMapHolder(v);
    }

    public void setComunicatorOnAddClick(ComunicatorOnAddClick comunicatorOnAddClick) {
        this.mComunicatorOnAddClick = comunicatorOnAddClick;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CustomMapHolder holder, int position) {
        if (mCustomMapObjects == null) return;
        holder.bindType(mCustomMapObjects.get(position));
    }

    @Override
    public int getItemCount() {
        if (mCustomMapObjects != null) return mCustomMapObjects.size();
        return 0;
    }

    class CustomMapHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerView;
        private ImageView mImgVwButtonAdd;

        public CustomMapHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rec_view);
            mImgVwButtonAdd = (ImageView) itemView.findViewById(R.id.img_vw_btn_add);
        }

        public void bindType (final CustomMapObject customMapObject){
            CustomPairRecycleViewAdapter customPairRecycleViewAdapter =
                    new CustomPairRecycleViewAdapter(customMapObject.getCustomPairs());

            mRecyclerView.setAdapter(customPairRecycleViewAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            mRecyclerView.setNestedScrollingEnabled(false);

            if (mComunicatorOnAddClick != null){
                mImgVwButtonAdd.setVisibility(View.VISIBLE);
                mImgVwButtonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mComunicatorOnAddClick.onAddClickListener(customMapObject.getId());
                    }
                });
            } else {
                mImgVwButtonAdd.setVisibility(View.GONE);
            }
        }
    }

    public interface ComunicatorOnAddClick {
        public void onAddClickListener(String id);
    }
}
