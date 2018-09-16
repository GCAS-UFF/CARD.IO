package cardio.com.cardio.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.holders.ItemExpandableSimpleListHolder;

public class ItemExpandableSimpleListAdapter extends RecyclerView.Adapter<ItemExpandableSimpleListHolder>{

    private List<Map.Entry<String, List<Map.Entry<String, String>>>> entrysByDateList;
    private Map<String, List<Map.Entry<String, String>>> entrysByDateMap;
    private ComunicatorItemClick mComunicatorItemClick;

    public ItemExpandableSimpleListAdapter(Map<String, List<Map.Entry<String, String>>> entrysByDateMap) {
        this.entrysByDateMap = entrysByDateMap;

        if (entrysByDateMap != null){
            this.entrysByDateList = new ArrayList<>(entrysByDateMap.entrySet());
        }
    }

    public ItemExpandableSimpleListAdapter(Map<String, List<Map.Entry<String, String>>> entrysByDateMap,
                                           ComunicatorItemClick comunicatorItemClick) {
        this.entrysByDateMap = entrysByDateMap;

        if (entrysByDateMap != null){
            this.entrysByDateList = new ArrayList<>(entrysByDateMap.entrySet());
        }

        this.mComunicatorItemClick = comunicatorItemClick;
    }

    public Map<String, List<Map.Entry<String, String>>> getEntrysByDateMap() {
        return entrysByDateMap;
    }

    @NonNull
    @Override
    public ItemExpandableSimpleListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_expandable_simpelist, parent, false);
        return new ItemExpandableSimpleListHolder(v, mComunicatorItemClick);
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

    public interface ComunicatorItemClick {
        void onClick(String dateStr);
    }
}
