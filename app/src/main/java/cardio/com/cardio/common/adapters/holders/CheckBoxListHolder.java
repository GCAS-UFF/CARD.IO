package cardio.com.cardio.common.adapters.holders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.CheckboxListAdapter;
import cardio.com.cardio.common.model.view.Item;
import cardio.com.cardio.common.model.view.CheckboxListItem;

public class CheckBoxListHolder extends  Holder{

    private RecyclerView mRecyclerView;
    private TextView mTvQuestion;

    public CheckBoxListHolder(View itemView) {
        super(itemView);

        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rec_view);
        mTvQuestion = (TextView) itemView.findViewById(R.id.tv_question);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
    }

    @Override
    public void bindType(Item item) {

        try {

            CheckboxListItem checkboxListItem = (CheckboxListItem) item;

            CheckboxListAdapter checkboxListAdapter = new CheckboxListAdapter(checkboxListItem.getOptions());
            mRecyclerView.setAdapter(checkboxListAdapter);
            mTvQuestion.setText(checkboxListItem.getQuestion());

        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
