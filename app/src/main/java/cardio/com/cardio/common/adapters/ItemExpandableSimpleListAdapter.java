package cardio.com.cardio.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.holders.ItemExpandableListHolder;
import cardio.com.cardio.common.adapters.holders.ItemExpandableSimpleListHolder;
import cardio.com.cardio.common.model.model.Paciente;

public class ItemExpandableSimpleListAdapter extends RecyclerView.Adapter<ItemExpandableSimpleListHolder>{

    private List<Map.Entry<String, Map<String, String>>> entrysByDateList;

    public ItemExpandableSimpleListAdapter(Map<String, Map<String, String>> entrysByDateMap) {
        if (entrysByDateMap != null){
            this.entrysByDateList = new ArrayList<>(entrysByDateMap.entrySet());
        }
    }

    @NonNull
    @Override
    public ItemExpandableSimpleListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_expandable_simpelist, parent, false);
        return new ItemExpandableSimpleListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemExpandableSimpleListHolder holder, int position) {

        if (entrysByDateList != null){
            holder.bindType(entrysByDateList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (entrysByDateList != null)
            return entrysByDateList.size();
        return 0;
    }
}
