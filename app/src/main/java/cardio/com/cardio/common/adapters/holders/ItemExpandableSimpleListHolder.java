package cardio.com.cardio.common.adapters.holders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemExpandableSimpleListAdapter;
import cardio.com.cardio.common.adapters.MapRecycleViewAdapter;

public class ItemExpandableSimpleListHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private TextView mTvTitle;
    private RecyclerView mRecView;
    private LinearLayout mLlExpandable;
    private LinearLayout getmLlExpandableContainer;
    private ItemExpandableSimpleListAdapter.ComunicatorItemClick ComunicatorItemClick;
    private Map.Entry<String, List<Map.Entry<String, String>>> mEntry;
    private ImageView imgVwBtnAdd;

    public ItemExpandableSimpleListHolder(View itemView, ItemExpandableSimpleListAdapter.ComunicatorItemClick ComunicatorItemClick) {
        super(itemView);

        mLlExpandable = (LinearLayout) itemView.findViewById(R.id.ll_expandable);
        getmLlExpandableContainer = (LinearLayout) itemView.findViewById(R.id.ll_expandable_container);

        mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mRecView = (RecyclerView) itemView.findViewById(R.id.rec_view);

        mRecView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        imgVwBtnAdd = (ImageView) itemView.findViewById(R.id.img_vw_btn_add);

        this.ComunicatorItemClick = ComunicatorItemClick;
    }


    public void bindType(final Map.Entry<String, List<Map.Entry<String, String>>> entry){
        try {
            this.mEntry = entry;
            mLlExpandable.setVisibility(View.GONE);

            mTvTitle.setText(entry.getKey());

            MapRecycleViewAdapter mapRecycleViewAdapter = new MapRecycleViewAdapter(entry.getValue());

            mRecView.setAdapter(mapRecycleViewAdapter);

            getmLlExpandableContainer.setOnClickListener(this);
            getmLlExpandableContainer.setOnLongClickListener(this);

            imgVwBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ComunicatorItemClick.onClick(entry.getKey());
                }
            });

        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (mLlExpandable.getVisibility() == View.GONE)
            mLlExpandable.setVisibility(View.VISIBLE);
        else
            mLlExpandable.setVisibility(View.GONE);
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(itemView.getContext(), "fui longamente clicado", Toast.LENGTH_SHORT);
        return true;
    }
}
