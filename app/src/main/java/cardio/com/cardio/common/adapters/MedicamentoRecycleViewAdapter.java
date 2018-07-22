package cardio.com.cardio.common.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.model.model.Medicamento;

public class MedicamentoRecycleViewAdapter extends RecyclerView.Adapter<MedicamentoRecycleViewAdapter.MedicamentoHolder>{

    private Context context;
    private List<Medicamento> medicamentos;

    public MedicamentoRecycleViewAdapter(Context context, List<Medicamento> medicamentos) {
        this.context = context;
        this.medicamentos = medicamentos;
    }

    @NonNull
    @Override
    public MedicamentoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_medicamento, parent, false);
        return new MedicamentoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoHolder holder, int position) {
        Medicamento medicamento = medicamentos.get(position);

        holder.tvTitle.setText(medicamento.getName());
        holder.tvDosagem.setText(medicamento.getDosagem());

        Date date = new Date(medicamento.getHorario());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

        holder.tvHorario.setText(simpleDateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        if (medicamentos != null) return medicamentos.size();
        return 0;
    }

    class MedicamentoHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDosagem;
        TextView tvHorario;
        ImageView imgVwDelete;
        ImageView imgVwEdit;

        public MedicamentoHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_titulo_medicamento);
            tvDosagem = (TextView) itemView.findViewById(R.id.tv_dosagem);
            tvHorario = (TextView) itemView.findViewById(R.id.tv_horario);

            imgVwDelete = (ImageView) itemView.findViewById(R.id.img_vw_delete);
            imgVwEdit = (ImageView) itemView.findViewById(R.id.img_vw_edit);
        }
    }
}
