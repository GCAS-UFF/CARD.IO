package cardio.uff.cardio.common.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cardio.uff.cardio.R;

public class CheckboxListAdapter extends RecyclerView.Adapter<CheckboxListAdapter.CheckboxHolder>{

    private Map<String, Boolean> options;
    private List<Map.Entry<String, Boolean>> optionsEntrys;

    public CheckboxListAdapter(Map<String, Boolean> options) {
        this.options = options;

        optionsEntrys = new ArrayList<>();
        optionsEntrys.addAll(options.entrySet());
    }

    @NonNull
    @Override
    public CheckboxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.checkbox_item, parent, false);
        return new CheckboxHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckboxHolder holder, int position) {
        if (optionsEntrys == null) return;

        holder.bindType(optionsEntrys.get(position));
    }

    @Override
    public int getItemCount() {
        if (optionsEntrys != null) return optionsEntrys.size();
        return 0;
    }

    class CheckboxHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;

        private CheckboxHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.check_box);
        }

        private void bindType(final Map.Entry<String, Boolean> entry){

            mCheckBox.setText(entry.getKey());
            mCheckBox.setChecked(entry.getValue());

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    entry.setValue(isChecked);
                }
            });
        }
    }
}
