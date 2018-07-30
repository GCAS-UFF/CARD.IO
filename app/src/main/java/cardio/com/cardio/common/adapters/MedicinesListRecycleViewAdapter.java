package cardio.com.cardio.common.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.model.model.MedicineList;

public class MedicinesListRecycleViewAdapter extends RecyclerView.Adapter<MedicinesListRecycleViewAdapter.MedicineListHolder> {

    private List<MedicineList> medicineList;
    private Context context;

    public MedicinesListRecycleViewAdapter(List<MedicineList> medicineList, Context context) {
        this.medicineList = medicineList;
        this.context = context;
    }

    @NonNull
    @Override
    public MedicineListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.list_medice_item, parent, false);
        return new MedicineListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineListHolder holder, int position) {

        MedicineList medicineList = this.medicineList.get(position);
        holder.tvTitle.setText(medicineList.getTitulo());
        MedicineRecycleViewAdapter medicamentoRecycleViewAdapter = new MedicineRecycleViewAdapter
                (context, medicineList.getMedicamentos());

        holder.recyclerView.setAdapter(medicamentoRecycleViewAdapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        if (medicineList != null) return medicineList.size();
        return 0;
    }

    class MedicineListHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        RecyclerView recyclerView;

         MedicineListHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rec_view);
        }
    }
}
