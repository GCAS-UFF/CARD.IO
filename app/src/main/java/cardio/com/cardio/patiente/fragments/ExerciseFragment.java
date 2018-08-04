package cardio.com.cardio.patiente.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.R;
import cardio.com.cardio.common.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.common.model.view.TextBox;
import cardio.com.cardio.common.model.model.Exercicio;
import cardio.com.cardio.common.model.view.Item;


public class ExerciseFragment extends Fragment {

    private RecyclerView mRecView;
    private ItemRecycleViewAdapter mItemRecycleViewAdapter;
    private Button mBtnSave;
    private List<Item> mItems;
    private TextBox mCaixaDeTextoExercise;
    private TextBox mCaixaDeTextoIntensity;
    private TextBox mCaixaDeTextoDuration;


    public ExerciseFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecView = (RecyclerView) view.findViewById(R.id.rec_view);
        mBtnSave = (Button) view.findViewById(R.id.btn_save);

        mItems = new ArrayList<>();

        mCaixaDeTextoExercise = new TextBox("Atividade física", "", TextBox.INPUT_TEXT);
        mCaixaDeTextoExercise.setHint("Caminhada");
        mItems.add(mCaixaDeTextoExercise);

        mCaixaDeTextoIntensity = new TextBox("Intensidade", "", TextBox.INPUT_TEXT);
        mCaixaDeTextoIntensity.setHint("Leve");
        mItems.add(mCaixaDeTextoIntensity);

        mCaixaDeTextoDuration = new TextBox("Duração", "min", TextBox.INPUT_NUMBER);
        mCaixaDeTextoDuration.setHint("30");
        mItems.add(mCaixaDeTextoDuration);

        mItemRecycleViewAdapter = new ItemRecycleViewAdapter(mItems);
        mRecView.setAdapter(mItemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidForm()){
                    saveObject();
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_error_field_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidForm (){
        boolean isValid = true;
        for (Item item : mItems){
            if (item.isEmpty()){
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private void saveObject(){
        Exercicio exercicio = new Exercicio();
        exercicio.setExercise(mCaixaDeTextoExercise.getValue());
        exercicio.setIntensity(mCaixaDeTextoIntensity.getValue());
        exercicio.setDuration(Formater.getIntegerFromString(mCaixaDeTextoDuration.getValue()));

        exercicio.setExecutedDate((new Date()).getTime());
        exercicio.setPerformed(true);

        saveIntoFirebase(exercicio);
    }

    private void saveIntoFirebase (Exercicio exercicio){

        try{
            DatabaseReference mDatabaseReference = FirebaseHelper.getInstance().getCurrentPatientDatabaseReference().
                    child(exercicio.getConstantId()).
                    child(exercicio.getType());
            exercicio.setId(mDatabaseReference.push().getKey());
            mDatabaseReference.child(exercicio.getId()).setValue(exercicio);
            Toast.makeText(getActivity(), getResources().getString(R.string.message_succes_register_general), Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.message_error_register_general), Toast.LENGTH_SHORT).show();
        }
    }

}
