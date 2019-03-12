package cardio.com.cardio.common.adapters.holders;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.model.view.DropDown;
import cardio.com.cardio.common.model.view.Item;

public class DropDownHolder extends Holder{
    private Spinner mSpinner;

    public DropDownHolder(View itemView) {
        super(itemView);
        mSpinner = (Spinner) itemView.findViewById(R.id.spinner);
    }

    @Override
    public void bindType(Item item) {

        try {

            final DropDown dropDown = (DropDown) item;

            final List<String> spinnerList = new ArrayList<>();
            spinnerList.add(dropDown.getHint());

            if (dropDown.getOptions() != null) {
                spinnerList.addAll(dropDown.getOptions().keySet());
            }

            final ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(itemView.getContext(),
                    R.layout.spinner_text, spinnerList.toArray(new String[spinnerList.size()]));
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

            mSpinner.setAdapter(spinnerAdapter);

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position >= 0 && position < spinnerList.size()) {
                        dropDown.setValue(dropDown.getOptions().get(
                                spinnerList.get(position)
                        ));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }
}
