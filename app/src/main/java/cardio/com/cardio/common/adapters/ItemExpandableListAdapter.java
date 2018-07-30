package cardio.com.cardio.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.holders.ItemExpandableListHolder;
import cardio.com.cardio.common.model.model.Paciente;

public class ItemExpandableListAdapter extends RecyclerView.Adapter<ItemExpandableListHolder>{

    private List<Paciente> mPatientsList;

    public ItemExpandableListAdapter() {
        this.mPatientsList = new ArrayList<>();
    }

    public List<Paciente> getmPatientsList() {
        return mPatientsList;
    }

    public void addPacient (Paciente paciente){
        mPatientsList.add(paciente);
        sortByName();
        notifyDataSetChanged();
    }

    private void sortByName (){
        if (mPatientsList.size() > 1) {
            Collections.sort(mPatientsList, new Comparator<Paciente>() {
                @Override
                public int compare(Paciente object1, Paciente object2) {
                    return object1.getNome().compareTo(object2.getNome());
                }
            });
        }
    }

    @NonNull
    @Override
    public ItemExpandableListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_expandable_list, parent, false);
        return new ItemExpandableListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemExpandableListHolder holder, int position) {

        Paciente currentPatient = mPatientsList.get(position);

        if (currentPatient != null){
            holder.bindType(currentPatient);
        }
    }

    @Override
    public int getItemCount() {
        if (mPatientsList != null)
            return mPatientsList.size();
        return 0;
    }
}
