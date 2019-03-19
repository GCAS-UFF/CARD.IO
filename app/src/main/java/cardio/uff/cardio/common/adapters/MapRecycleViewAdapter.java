package cardio.uff.cardio.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cardio.uff.cardio.R;

public class MapRecycleViewAdapter extends RecyclerView.Adapter<MapRecycleViewAdapter.MapEntryHolder> {

    private List<Map.Entry<String, String>> mapEntrys;

    public MapRecycleViewAdapter(List<Map.Entry<String, String>> mapEntrys) {
        this.mapEntrys = mapEntrys;
    }

    @NonNull
    @Override
    public MapEntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_map, parent, false);
        return new MapEntryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MapEntryHolder holder, int position) {
        if (mapEntrys == null) return;

        holder.bindType(mapEntrys.get(position));
    }

    @Override
    public int getItemCount() {
        if (mapEntrys != null) return mapEntrys.size();
        return 0;
    }

    class MapEntryHolder extends RecyclerView.ViewHolder {

        private TextView mTvLabel;
        private TextView mTvContent;

        MapEntryHolder(View itemView) {
            super(itemView);

            mTvLabel = (TextView) itemView.findViewById(R.id.tv_label);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }

        void bindType (Map.Entry<String, String> entry){
            try {
                mTvLabel.setText(entry.getKey());
                mTvContent.setText(entry.getValue());
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }
}
