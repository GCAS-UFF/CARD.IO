package cardio.com.cardio.common.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import cardio.com.cardio.R;
import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.util.Formater;

public class ItemExpandableListHolder extends RecyclerView.ViewHolder {

    private TextView mTvName;
    private TextView mTvCPF;
    private TextView mTvAdress;
    private TextView mTvBirthdate;
    private LinearLayout mLlExpandable;
    private LinearLayout getmLlExpandableContainer;


    public ItemExpandableListHolder(View itemView) {
        super(itemView);

        mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        mTvCPF = (TextView) itemView.findViewById(R.id.tv_cpf);
        mTvAdress = (TextView) itemView.findViewById(R.id.tv_adress);
        mTvBirthdate = (TextView) itemView.findViewById(R.id.tv_birthdate);
        mLlExpandable = (LinearLayout) itemView.findViewById(R.id.ll_expandable);
        getmLlExpandableContainer = (LinearLayout) itemView.findViewById(R.id.ll_expandable_container);
    }


    public void bindType(Paciente patient){
        try {
            mTvName.setText(patient.getNome());
            mTvCPF.setText(patient.getCpf());
            mTvAdress.setText(patient.getEndereco());
            mTvBirthdate.setText(Formater.getStringFromDate(new Date(patient.getDataNasc())));
            mLlExpandable.setVisibility(View.GONE);

            getmLlExpandableContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLlExpandable.getVisibility() == View.GONE)
                        mLlExpandable.setVisibility(View.VISIBLE);
                    else
                        mLlExpandable.setVisibility(View.GONE);
                }
            });


        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }


}
