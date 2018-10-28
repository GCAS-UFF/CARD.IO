package cardio.com.cardio.common.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemExpandableListAdapter;
import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.common.util.Formater;

public class ItemExpandableListHolder extends RecyclerView.ViewHolder {

    private TextView mTvName;
    private TextView mTvCPF;
    private TextView mTvAdress;
    private TextView mTvBirthdate;
    private LinearLayout mLlExpandable;
    private LinearLayout mLlExpandableContainer;
    private Button mBtnDisassociatePatient;
    private Button mBtnEditPatient;
    private ItemExpandableListAdapter.ComunicatorExpandableItem comunicatorExpandableItem;


    public ItemExpandableListHolder(View itemView, ItemExpandableListAdapter.ComunicatorExpandableItem comunicatorExpandableItem) {
        super(itemView);

        mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        mTvCPF = (TextView) itemView.findViewById(R.id.tv_cpf);
        mTvAdress = (TextView) itemView.findViewById(R.id.tv_adress);
        mTvBirthdate = (TextView) itemView.findViewById(R.id.tv_birthdate);
        mLlExpandable = (LinearLayout) itemView.findViewById(R.id.ll_expandable);
        mBtnDisassociatePatient = (Button) itemView.findViewById(R.id.btn_disassociate_patient);
        mBtnEditPatient = (Button) itemView.findViewById(R.id.btn_edit_patient);
        mLlExpandableContainer = (LinearLayout) itemView.findViewById(R.id.ll_expandable_container);
        this.comunicatorExpandableItem = comunicatorExpandableItem;

    }


    public void bindType(final Paciente patient){
        try {
            mTvName.setText(patient.getNome());
            mTvCPF.setText(patient.getCpf());
            mTvAdress.setText(patient.getEndereco());
            mTvBirthdate.setText(Formater.getStringFromDate(new Date(patient.getDataNasc())));
            mLlExpandable.setVisibility(View.GONE);

            mLlExpandableContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLlExpandable.getVisibility() == View.GONE)
                        mLlExpandable.setVisibility(View.VISIBLE);
                    else
                        mLlExpandable.setVisibility(View.GONE);
                }
            });

            mBtnDisassociatePatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comunicatorExpandableItem.disassociatePatient(patient);
                }
            });

            mBtnEditPatient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comunicatorExpandableItem.editPatient(patient);
                }
            });

        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }


}
