package cardio.com.cardio.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

import cardio.com.cardio.adapters.holders.Holder;
import cardio.com.cardio.model.Item;
import cardio.com.cardio.adapters.decodificators.LayoutDecodificador;

public class ItemRecycleViewAdapter extends RecyclerView.Adapter<Holder> {

    private List<Item> mItens;
    private FragmentManager fragmentManager;

    public ItemRecycleViewAdapter(List<Item> mItens) {
        this.mItens = mItens;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public List<Item> getmItens() {
        return mItens;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItens == null) return super.getItemViewType(position);
        return mItens.get(position).getTipo();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return LayoutDecodificador.getViewHolder(parent, viewType, fragmentManager);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindType(mItens.get(position));
    }

    @Override
    public int getItemCount() {
        if (mItens == null) return 0;
        return mItens.size();
    }
}
