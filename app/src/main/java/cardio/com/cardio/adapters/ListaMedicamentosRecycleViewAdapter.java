package cardio.com.cardio.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.model.ListaMedicamento;

public class ListaMedicamentosRecycleViewAdapter extends RecyclerView.Adapter<ListaMedicamentosRecycleViewAdapter.ListaMedicamentoHolder> {

    private List<ListaMedicamento> listasMedicamentos;
    private Context context;

    public ListaMedicamentosRecycleViewAdapter(List<ListaMedicamento> listasMedicamentos, Context context) {
        this.listasMedicamentos = listasMedicamentos;
        this.context = context;
    }

    @NonNull
    @Override
    public ListaMedicamentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_lista_medicamento, parent, false);
        return new ListaMedicamentoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaMedicamentoHolder holder, int position) {

        ListaMedicamento listaMedicamento = listasMedicamentos.get(position);
        holder.tvTitle.setText(listaMedicamento.getTitulo());
        MedicamentoRecycleViewAdapter medicamentoRecycleViewAdapter = new MedicamentoRecycleViewAdapter
                (context, listaMedicamento.getMedicamentos());

        holder.recyclerView.setAdapter(medicamentoRecycleViewAdapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        if (listasMedicamentos != null) return listasMedicamentos.size();
        return 0;
    }

    class ListaMedicamentoHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        RecyclerView recyclerView;

         ListaMedicamentoHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rec_view);
        }
    }
}
