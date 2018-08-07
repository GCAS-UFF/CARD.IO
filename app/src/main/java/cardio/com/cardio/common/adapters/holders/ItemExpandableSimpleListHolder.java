package cardio.com.cardio.common.adapters.holders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemExpandableListAdapter;
import cardio.com.cardio.common.adapters.MapRecycleViewAdapter;
import cardio.com.cardio.common.model.model.Paciente;
import cardio.com.cardio.common.util.Formater;

public class ItemExpandableSimpleListHolder extends RecyclerView.ViewHolder {

    private TextView mTvTitle;
    private RecyclerView mRecView;
    private LinearLayout mLlExpandable;
    private LinearLayout getmLlExpandableContainer;

    public ItemExpandableSimpleListHolder(View itemView) {
        super(itemView);

        mLlExpandable = (LinearLayout) itemView.findViewById(R.id.ll_expandable);
        getmLlExpandableContainer = (LinearLayout) itemView.findViewById(R.id.ll_expandable_container);

        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mRecView = (RecyclerView) itemView.findViewById(R.id.rec_view);

        mRecView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
    }


    public void bindType(Map.Entry<String, List<Map.Entry<String, String>>> entry){
        try {
            mTvTitle.setText(entry.getKey());

            MapRecycleViewAdapter mapRecycleViewAdapter = new MapRecycleViewAdapter(entry.getValue());

            mRecView.setAdapter(mapRecycleViewAdapter);

            mLlExpandable.setVisibility(View.VISIBLE);

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
