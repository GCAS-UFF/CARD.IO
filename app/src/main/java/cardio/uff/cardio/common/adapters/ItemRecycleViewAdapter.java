package cardio.uff.cardio.common.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

import cardio.uff.cardio.common.adapters.holders.Holder;
import cardio.uff.cardio.common.model.view.Item;
import cardio.uff.cardio.common.adapters.decodificators.LayoutDecodificator;

public class ItemRecycleViewAdapter extends RecyclerView.Adapter<Holder> {

    private List<Item> mItems;
    private FragmentManager fragmentManager;

    public ItemRecycleViewAdapter(List<Item> mItems) {
        this.mItems = mItems;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public List<Item> getmItems() {
        return mItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems == null) return super.getItemViewType(position);
        return mItems.get(position).getTipo();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return LayoutDecodificator.getViewHolder(parent, viewType, fragmentManager);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindType(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        if (mItems == null) return 0;
        return mItems.size();
    }
}