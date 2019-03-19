package cardio.uff.cardio.common.adapters;

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

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.model.model.Medicamento;

public class MedicineRecycleViewAdapter extends RecyclerView.Adapter<MedicineRecycleViewAdapter.MedicineHolder>{

    private Context context;
    private List<Medicamento> medicines;

    public MedicineRecycleViewAdapter(Context context, List<Medicamento> medicines) {
        this.context = context;
        this.medicines = medicines;
    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.medicine_item, parent, false);
        return new MedicineHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineHolder holder, int position) {
        Medicamento medicamento = medicines.get(position);

        holder.tvTitle.setText(medicamento.getName());
        holder.tvDosagem.setText(medicamento.getDosagem());

        Date date = new Date(medicamento.getHorario());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

        holder.tvHorario.setText(simpleDateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        if (medicines != null) return medicines.size();
        return 0;
    }

    class MedicineHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDosagem;
        TextView tvHorario;
        ImageView imgVwDelete;
        ImageView imgVwEdit;

        public MedicineHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_titulo_medicamento);
            tvDosagem = (TextView) itemView.findViewById(R.id.tv_dosagem);
            tvHorario = (TextView) itemView.findViewById(R.id.tv_horario);

            imgVwDelete = (ImageView) itemView.findViewById(R.id.img_vw_delete);
            imgVwEdit = (ImageView) itemView.findViewById(R.id.img_vw_edit);
        }
    }
}
